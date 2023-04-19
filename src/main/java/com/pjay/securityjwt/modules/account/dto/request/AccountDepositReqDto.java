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
    @Digits(integer = 4, fraction = 4, message = "숫자 값이 한계를 초과합니다")
    private Long number;
    @NotNull
    private Long amount;
    @NotEmpty
    @Pattern(regexp = "^(DEPOSIT)$", message = "\"DEPOSIT\"와 일치해야 합니다")
    private String gubun; // DEPOSIT
    @NotEmpty
    @Pattern(regexp = "^[0-9]{11}", message = "전화번호 11자리 입력해주세요")
    private String tel;
}
