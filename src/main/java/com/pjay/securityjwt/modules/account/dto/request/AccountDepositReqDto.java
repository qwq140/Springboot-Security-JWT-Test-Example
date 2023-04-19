package com.pjay.securityjwt.modules.account.dto.request;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.Digits;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

@Getter
@Setter
public class AccountDepositReqDto {
    @NotNull
    @Digits(integer = 4, fraction = 4)
    private Long number;
    @NotNull
    private Long amount;
    @NotEmpty
    @Pattern(regexp = "^(DEPOSIT)$")
    private String gubun; // DEPOSIT
    @NotEmpty
    @Pattern(regexp = "^[0-9]{11}")
    private String tel;
}
