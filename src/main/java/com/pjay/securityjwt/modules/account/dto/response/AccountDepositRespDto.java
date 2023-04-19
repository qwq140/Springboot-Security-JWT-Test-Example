package com.pjay.securityjwt.modules.account.dto.response;

import com.pjay.securityjwt.modules.account.domain.Account;
import com.pjay.securityjwt.modules.transaction.domain.Transaction;
import com.pjay.securityjwt.modules.transaction.dto.response.TransactionDto;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AccountDepositRespDto {
    private Long id;
    private Long number;
    private TransactionDto transaction;

    public AccountDepositRespDto(Account account, Transaction transaction) {
        this.id = account.getId();
        this.number = account.getNumber();
        this.transaction = new TransactionDto(transaction);
    }
}
