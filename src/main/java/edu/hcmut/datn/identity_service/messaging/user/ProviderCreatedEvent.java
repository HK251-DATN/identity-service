package edu.hcmut.datn.identity_service.messaging.user;

import java.time.LocalDate;

import edu.hcmut.datn.identity_service.common.enums.Bank;
import edu.hcmut.datn.identity_service.common.enums.Gender;

public record ProviderCreatedEvent(
    Long userId,
    String email,
    String fName,
    String lName,
    String avtUrl,
    LocalDate dob,
    String pNum,
    Gender gender,
    Bank bankId,
    String bankNum,
    boolean isFreshAccount
) {}
