package edu.hcmut.datn.identity_service.dto.request;

import java.time.LocalDate;

import edu.hcmut.datn.identity_service.common.enums.Gender;
import lombok.Getter;
import lombok.Setter;

public class UserRegistrationRequest {

    @Getter
    @Setter
    private String email;

    @Getter
    @Setter
    private String password;

    @Getter
    @Setter
    private String fName;

    @Getter
    @Setter
    private String lName;

    @Getter
    @Setter
    private LocalDate dob;

    @Getter
    @Setter
    private String pNum;

    @Getter
    @Setter
    private Gender gender;

    public UserRequest toUserRequest() {
        return new UserRequest(email, password);
    }
}
