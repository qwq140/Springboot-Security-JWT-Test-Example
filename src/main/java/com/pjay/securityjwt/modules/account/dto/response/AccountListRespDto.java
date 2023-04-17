package com.pjay.securityjwt.modules.account.dto.response;

import com.pjay.securityjwt.modules.account.domain.Account;
import com.pjay.securityjwt.modules.user.domain.User;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Getter
@Setter
public class AccountListRespDto {
    private String fullname;
    private List<AccountDto> accounts = new ArrayList<>();

    public AccountListRespDto(User user, List<Account> accounts) {
        this.fullname = user.getFullname();
        this.accounts = accounts.stream().map(AccountDto::new).collect(Collectors.toList());
    }
}
