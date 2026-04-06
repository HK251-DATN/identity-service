package edu.hcmut.datn.identity_service.config;

import edu.hcmut.datn.identity_service.dao.Group;
import edu.hcmut.datn.identity_service.dao.GroupPermission;
import edu.hcmut.datn.identity_service.dao.Permission;
import edu.hcmut.datn.identity_service.dao.User;
import edu.hcmut.datn.identity_service.dao.UserGroup;
import edu.hcmut.datn.identity_service.repository.GroupPermissionRepository;
import edu.hcmut.datn.identity_service.repository.GroupRepository;
import edu.hcmut.datn.identity_service.repository.PermissionRepository;
import edu.hcmut.datn.identity_service.repository.UserGroupRepository;
import edu.hcmut.datn.identity_service.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.time.LocalDateTime;

@Configuration
@RequiredArgsConstructor
@Slf4j
public class DataSeeder {

    private final UserRepository userRepository;
    private final GroupRepository groupRepository;
    private final PermissionRepository permissionRepository;
    private final UserGroupRepository userGroupRepository;
    private final GroupPermissionRepository groupPermissionRepository;

    @Bean
    public CommandLineRunner seedData() {
        return args -> {
            // Only seed if database is empty
            if (userRepository.count() > 0) {
                log.info("Database already contains data. Skipping seeding.");
                return;
            }

            log.info("Starting database seeding...");

            // Seed Permissions
            Permission userView = createPermission("USER_VIEW", "View users");
            Permission userUpdate = createPermission("USER_UPDATE", "Update users");
            Permission userDelete = createPermission("USER_DELETE", "Delete users");
            Permission groupManage = createPermission("GROUP_MANAGE", "Manage groups");
            Permission permissionManage = createPermission("PERMISSION_MANAGE", "Manage permissions");
            Permission permissionView = createPermission("PERMISSION_VIEW", "View permissions");

            log.info("Seeded {} permissions", permissionRepository.count());

            // Seed Groups
            Group adminGroup = createGroup("ADMIN", "Administrator group");
            Group buyerGroup = createGroup("BUYER", "Customer/Buyer group");

            log.info("Seeded {} groups", groupRepository.count());

            // Assign permissions to groups
            assignPermissionToGroup(adminGroup, userView);
            assignPermissionToGroup(adminGroup, userUpdate);
            assignPermissionToGroup(adminGroup, userDelete);
            assignPermissionToGroup(adminGroup, groupManage);
            assignPermissionToGroup(adminGroup, permissionManage);
            assignPermissionToGroup(adminGroup, permissionView);

            assignPermissionToGroup(buyerGroup, userView); // Buyers can view their own profile

            log.info("Assigned permissions to groups");

            // Seed Users
            BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();

            User admin = createUser(
                "admin@gmail.com",
                encoder.encode("admin")
            );

            User buyer = createUser(
                "buyer@gmail.com",
                encoder.encode("buyer")
            );

            log.info("Seeded {} users", userRepository.count());

            // Assign users to groups
            assignUserToGroup(admin, adminGroup);
            assignUserToGroup(buyer, buyerGroup);

            log.info("Assigned users to groups");
            log.info("Database seeding completed successfully!");
        };
    }

    private Permission createPermission(String code, String description) {
        Permission permission = new Permission();
        permission.setPerCode(code);
        permission.setPerName(code); // Use code as name by default
        permission.setPerDescription(description);
        permission.setActive(true);
        return permissionRepository.save(permission);
    }

    private Group createGroup(String name, String description) {
        Group group = new Group();
        group.setGroupName(name);
        group.setDescription(description);
        group.setActive(true);
        return groupRepository.save(group);
    }

    private User createUser(String email, String hashedPassword) {
        User user = new User();
        user.setUserEmail(email);
        user.setHashedPwd(hashedPassword);
        return userRepository.save(user);
    }

    private void assignPermissionToGroup(Group group, Permission permission) {
        GroupPermission groupPermission = new GroupPermission();
        groupPermission.setGroupId(group.getGroupId());
        groupPermission.setPerId(permission.getPerId());
        groupPermission.setActive(true);
        groupPermission.setValidUntil(LocalDateTime.now().plusYears(10)); // Valid for 10 years
        groupPermissionRepository.save(groupPermission);
    }

    private void assignUserToGroup(User user, Group group) {
        UserGroup userGroup = new UserGroup();
        userGroup.setUserId(user.getUserId());
        userGroup.setGroupId(group.getGroupId());
        userGroup.setActive(true);
        userGroupRepository.save(userGroup);
    }
}
