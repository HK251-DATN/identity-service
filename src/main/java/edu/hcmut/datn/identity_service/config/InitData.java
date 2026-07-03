package edu.hcmut.datn.identity_service.config;

import java.time.LocalDate;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;
import edu.hcmut.datn.identity_service.common.enums.Bank;
import edu.hcmut.datn.identity_service.common.enums.Gender;
import lombok.Getter;
import lombok.Setter;

/**
 * Binds {@code src/main/resources/init_data.json} for {@link DataSeeder}.
 *
 * There is no explicit "registration type" per user; which of
 * {@code UserRegistrationRequest}/{@code EmployeeRegistrationRequest}/{@code ProviderRegistrationRequest}
 * a {@code UserSeed} maps to is derived from the {@code Group} it's linked to via
 * {@code UserGroups} (group names BUYER/PROVIDER/EMPLOYEE double as the registration flow selector).
 */
@Getter
@Setter
public class InitData {

    @JsonProperty("Groups")
    private List<GroupSeed> groups;

    @JsonProperty("Users")
    private List<UserSeed> users;

    @JsonProperty("Permissions")
    private List<PermissionSeed> permissions;

    @JsonProperty("UserGroups")
    private List<UserGroupSeed> userGroups;

    @JsonProperty("GroupPermissions")
    private List<GroupPermissionSeed> groupPermissions;

    @Getter
    @Setter
    public static class GroupSeed {
        private int id;
        private String groupName;
        private String description;
        @JsonProperty("isActive")
        private boolean active;
    }

    @Getter
    @Setter
    public static class UserSeed {
        private int id;
        private String email;
        private String password;
        private String fName;
        private String lName;
        private String avtUrl;
        private LocalDate dob;
        private String pNum;
        private Gender gender;
        private Bank bankId;
        private String bankNum;
        @JsonProperty("isFreshAccount")
        private boolean freshAccount;
    }

    @Getter
    @Setter
    public static class PermissionSeed {
        private int id;
        private String perCode;
        private String perName;
        private String perDescription;
        @JsonProperty("isActive")
        private boolean active;
    }

    @Getter
    @Setter
    public static class UserGroupSeed {
        private int userId;
        private int groupId;
        @JsonProperty("isActive")
        private boolean active;
    }

    @Getter
    @Setter
    public static class GroupPermissionSeed {
        private int groupId;
        private int perId;
        @JsonProperty("isActive")
        private boolean active;
    }
}
