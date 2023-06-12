package com.pjay.securityjwt.modules.account.dto.request;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.Digits;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

@Getter
@Setter
public class AccountTransferReqDto {
    @NotNull
    @Digits(integer = 4, fraction = 4, message = "숫자 값이 한계를 초과합니다")
    private Long withdrawNumber;
    @NotNull
    @Digits(integer = 4, fraction = 4, message = "숫자 값이 한계를 초과합니다")
    private Long depositNumber;
    @NotNull
    @Digits(integer = 4, fraction = 4)
    private Long withdrawPassword;
    @NotNull
    private Long amount;
    @NotEmpty
    @Pattern(regexp = "^(TRANSFER)$", message = "\"TRANSFER\"와 일치해야 합니다")
    private String gubun;
}
