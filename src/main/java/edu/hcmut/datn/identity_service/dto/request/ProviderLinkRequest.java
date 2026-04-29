package edu.hcmut.datn.identity_service.dto.request;

import edu.hcmut.datn.identity_service.common.enums.Bank;
import lombok.Getter;

@Getter
public class ProviderLinkRequest {
    private Bank bankId;
    private String bankNum;
}
