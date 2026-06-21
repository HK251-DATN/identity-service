package edu.hcmut.datn.identity_service.dto.request;

import java.time.LocalDate;

import edu.hcmut.datn.identity_service.common.enums.Gender;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class EmployeeRegistrationRequest {
    private String email;
    private String fName;
    private String lName;
    private LocalDate dob;
    private String pNum;
    private Gender gender;
}
