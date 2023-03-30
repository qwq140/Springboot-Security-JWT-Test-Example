package com.pjay.securityjwt.modules.account.dto.request;

import com.pjay.securityjwt.modules.account.domain.Account;
import com.pjay.securityjwt.modules.user.domain.User;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.Digits;
import javax.validation.constraints.NotNull;

@Getter
@Setter
public class AccountSaveReqDto {
    @NotNull
    @Digits(integer = 4, fraction = 4)
    private Long number;
    @NotNull
    @Digits(integer = 4, fraction = 4)
    private Long password;

    public Account toEntity(User user){
        return Account.builder()
                .number(number)
                .password(password)
                .balance(1000L)
                .user(user)
                .build();
    }
}
