package com.pjay.securityjwt.modules.account.dto.response;

import com.pjay.securityjwt.modules.account.domain.Account;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AccountDto {
    private Long id;
    private Long number;
    private Long balance;

    public AccountDto(Account account) {
        this.id = account.getId();
        this.number = account.getNumber();
        this.balance = account.getBalance();
    }
}
