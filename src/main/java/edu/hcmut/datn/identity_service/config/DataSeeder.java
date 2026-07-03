package edu.hcmut.datn.identity_service.config;

import java.io.InputStream;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;
import tools.jackson.databind.ObjectMapper;
import edu.hcmut.datn.identity_service.dao.Group;
import edu.hcmut.datn.identity_service.dao.GroupPermission;
import edu.hcmut.datn.identity_service.dao.Permission;
import edu.hcmut.datn.identity_service.dao.User;
import edu.hcmut.datn.identity_service.dao.UserGroup;
import edu.hcmut.datn.identity_service.dto.request.EmployeeRegistrationRequest;
import edu.hcmut.datn.identity_service.dto.request.ProviderRegistrationRequest;
import edu.hcmut.datn.identity_service.dto.request.UserRegistrationRequest;
import edu.hcmut.datn.identity_service.repository.GroupPermissionRepository;
import edu.hcmut.datn.identity_service.repository.GroupRepository;
import edu.hcmut.datn.identity_service.repository.PermissionRepository;
import edu.hcmut.datn.identity_service.repository.UserGroupRepository;
import edu.hcmut.datn.identity_service.repository.UserRepository;
import edu.hcmut.datn.identity_service.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Configuration
@RequiredArgsConstructor
@Slf4j
public class DataSeeder {

    private static final String INIT_DATA_FILE = "init_data.json";

    private final UserRepository userRepository;
    private final GroupRepository groupRepository;
    private final PermissionRepository permissionRepository;
    private final UserGroupRepository userGroupRepository;
    private final GroupPermissionRepository groupPermissionRepository;
    private final UserService userService;
    private final ObjectMapper objectMapper;

    @Bean
    public CommandLineRunner seedData() {
        return args -> {
            // Only seed if database is empty
            if (userRepository.count() > 0) {
                log.info("Database already contains data. Skipping seeding.");
                return;
            }

            log.info("Starting database seeding from {}...", INIT_DATA_FILE);

            InitData initData;
            try (InputStream is = new ClassPathResource(INIT_DATA_FILE).getInputStream()) {
                initData = objectMapper.readValue(is, InitData.class);
            }

            // Group name (BUYER/PROVIDER/EMPLOYEE) doubles as which registration flow
            // a UserSeed goes through - derived from the UserGroups linkage instead of
            // a redundant per-user "type" field.
            Map<Integer, String> registrationTypeByUserJsonId =
                    deriveRegistrationTypes(initData.getUserGroups(), initData.getGroups());

            Map<Integer, Long> groupIdByJsonId = seedGroups(initData.getGroups());
            Map<Integer, Long> permissionIdByJsonId = seedPermissions(initData.getPermissions());
            Map<Integer, Long> userIdByJsonId = seedUsers(initData.getUsers(), registrationTypeByUserJsonId);
            seedUserGroups(initData.getUserGroups(), userIdByJsonId, groupIdByJsonId);
            seedGroupPermissions(initData.getGroupPermissions(), groupIdByJsonId, permissionIdByJsonId);

            log.info("Database seeding completed successfully!");
        };
    }

    private Map<Integer, String> deriveRegistrationTypes(List<InitData.UserGroupSeed> userGroups,
                                                          List<InitData.GroupSeed> groups) {
        Map<Integer, String> groupNameByJsonId = new HashMap<>();
        for (InitData.GroupSeed group : groups) {
            groupNameByJsonId.put(group.getId(), group.getGroupName());
        }

        Map<Integer, String> registrationTypeByUserJsonId = new HashMap<>();
        for (InitData.UserGroupSeed userGroup : userGroups) {
            registrationTypeByUserJsonId.put(userGroup.getUserId(), groupNameByJsonId.get(userGroup.getGroupId()));
        }

        return registrationTypeByUserJsonId;
    }

    private Map<Integer, Long> seedGroups(List<InitData.GroupSeed> groups) {
        Map<Integer, Long> groupIdByJsonId = new HashMap<>();

        for (InitData.GroupSeed seed : groups) {
            Group group = new Group();
            group.setGroupName(seed.getGroupName());
            group.setDescription(seed.getDescription());
            group.setActive(seed.isActive());

            Group saved = groupRepository.save(group);
            groupIdByJsonId.put(seed.getId(), saved.getGroupId());
        }

        log.info("Seeded {} groups", groupRepository.count());
        return groupIdByJsonId;
    }

    private Map<Integer, Long> seedPermissions(List<InitData.PermissionSeed> permissions) {
        Map<Integer, Long> permissionIdByJsonId = new HashMap<>();

        for (InitData.PermissionSeed seed : permissions) {
            Permission permission = new Permission();
            permission.setPerCode(seed.getPerCode());
            permission.setPerName(seed.getPerName());
            permission.setPerDescription(seed.getPerDescription());
            permission.setActive(seed.isActive());

            Permission saved = permissionRepository.save(permission);
            permissionIdByJsonId.put(seed.getId(), saved.getPerId());
        }

        log.info("Seeded {} permissions", permissionRepository.count());
        return permissionIdByJsonId;
    }

    private Map<Integer, Long> seedUsers(List<InitData.UserSeed> users,
                                          Map<Integer, String> registrationTypeByUserJsonId) {
        Map<Integer, Long> userIdByJsonId = new HashMap<>();

        for (InitData.UserSeed seed : users) {
            String type = registrationTypeByUserJsonId.get(seed.getId());
            if (type == null) {
                log.warn("User '{}' isn't linked to any group in UserGroups, skipping (can't determine registration flow)",
                        seed.getEmail());
                continue;
            }

            // Routed through UserService so seeding persists the user AND publishes
            // the matching Kafka event, same as the real registration endpoints do.
            User created = switch (type) {
                case "BUYER" -> userService.create(toUserRegistrationRequest(seed));
                case "EMPLOYEE" -> userService.create(toEmployeeRegistrationRequest(seed));
                case "PROVIDER" -> userService.createProvider(toProviderRegistrationRequest(seed));
                default -> {
                    log.warn("User '{}' is linked to unknown group '{}', skipping", seed.getEmail(), type);
                    yield null;
                }
            };

            if (created == null) {
                log.warn("Failed to seed user '{}' (duplicate email or invalid data), skipping", seed.getEmail());
                continue;
            }

            userIdByJsonId.put(seed.getId(), created.getUserId());
        }

        log.info("Seeded {} users", userRepository.count());
        return userIdByJsonId;
    }

    private UserRegistrationRequest toUserRegistrationRequest(InitData.UserSeed seed) {
        UserRegistrationRequest request = new UserRegistrationRequest();
        request.setEmail(seed.getEmail());
        request.setPassword(seed.getPassword());
        request.setFName(seed.getFName());
        request.setLName(seed.getLName());
        request.setDob(seed.getDob());
        request.setPNum(seed.getPNum());
        request.setGender(seed.getGender());
        return request;
    }

    private EmployeeRegistrationRequest toEmployeeRegistrationRequest(InitData.UserSeed seed) {
        EmployeeRegistrationRequest request = new EmployeeRegistrationRequest();
        request.setEmail(seed.getEmail());
        request.setFName(seed.getFName());
        request.setLName(seed.getLName());
        request.setDob(seed.getDob());
        request.setPNum(seed.getPNum());
        request.setGender(seed.getGender());
        return request;
    }

    private ProviderRegistrationRequest toProviderRegistrationRequest(InitData.UserSeed seed) {
        ProviderRegistrationRequest request = new ProviderRegistrationRequest();
        request.setEmail(seed.getEmail());
        request.setPassword(seed.getPassword());
        request.setFName(seed.getFName());
        request.setLName(seed.getLName());
        request.setDob(seed.getDob());
        request.setPNum(seed.getPNum());
        request.setGender(seed.getGender());
        request.setBankId(seed.getBankId());
        request.setBankNum(seed.getBankNum());
        return request;
    }

    private void seedUserGroups(List<InitData.UserGroupSeed> userGroups,
                                 Map<Integer, Long> userIdByJsonId,
                                 Map<Integer, Long> groupIdByJsonId) {
        for (InitData.UserGroupSeed seed : userGroups) {
            Long userId = userIdByJsonId.get(seed.getUserId());
            Long groupId = groupIdByJsonId.get(seed.getGroupId());

            if (userId == null || groupId == null) {
                log.warn("Skipping UserGroup entry referencing unknown userId={} or groupId={}",
                        seed.getUserId(), seed.getGroupId());
                continue;
            }

            UserGroup userGroup = new UserGroup();
            userGroup.setUserId(userId);
            userGroup.setGroupId(groupId);
            userGroup.setActive(seed.isActive());
            userGroupRepository.save(userGroup);
        }

        log.info("Assigned users to groups");
    }

    private void seedGroupPermissions(List<InitData.GroupPermissionSeed> groupPermissions,
                                       Map<Integer, Long> groupIdByJsonId,
                                       Map<Integer, Long> permissionIdByJsonId) {
        for (InitData.GroupPermissionSeed seed : groupPermissions) {
            Long groupId = groupIdByJsonId.get(seed.getGroupId());
            Long perId = permissionIdByJsonId.get(seed.getPerId());

            if (groupId == null || perId == null) {
                log.warn("Skipping GroupPermission entry referencing unknown groupId={} or perId={}",
                        seed.getGroupId(), seed.getPerId());
                continue;
            }

            GroupPermission groupPermission = new GroupPermission();
            groupPermission.setGroupId(groupId);
            groupPermission.setPerId(perId);
            groupPermission.setActive(seed.isActive());
            groupPermission.setValidUntil(LocalDateTime.now().plusYears(10));
            groupPermissionRepository.save(groupPermission);
        }

        log.info("Assigned permissions to groups");
    }
}
