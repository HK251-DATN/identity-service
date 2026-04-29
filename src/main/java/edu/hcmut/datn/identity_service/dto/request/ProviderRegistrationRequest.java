package edu.hcmut.datn.identity_service.dto.request;

import java.time.LocalDate;

import edu.hcmut.datn.identity_service.common.enums.Bank;
import edu.hcmut.datn.identity_service.common.enums.Gender;
import lombok.Getter;

@Getter
public class ProviderRegistrationRequest {
    private String email;
    private String password;
    private String fName;
    private String lName;
    private LocalDate dob;
    private String pNum;
    private Gender gender;
    private Bank bankId;
    private String bankNum;

    public UserRequest toUserRequest() {
        return new UserRequest(email, password);
    }
}
