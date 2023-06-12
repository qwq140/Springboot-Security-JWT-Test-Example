package com.pjay.securityjwt.modules.account.dto.response;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.pjay.securityjwt.modules.account.domain.Account;
import com.pjay.securityjwt.modules.transaction.domain.Transaction;
import com.pjay.securityjwt.utils.CustomDateUtil;
import lombok.Getter;
import lombok.Setter;

// DTO가 똑같아도 재사용하지 않기 (나중에 만ㅇ약에 출금할때 조금 DTO 달라져야 하면 DTO를 공유하면 수정잘못하면 망함 - 독립적으로 만들기)
@Getter
@Setter
public class AccountWithdrawRespDto {
    private Long id;
    private Long number;
    private Long balance;
    private TransactionDto transaction;

    public AccountWithdrawRespDto(Account account, Transaction transaction) {
        this.id = account.getId();
        this.number = account.getNumber();
        this.balance = account.getBalance();
        this.transaction = new TransactionDto(transaction);
    }

    @Getter
    @Setter
    public class TransactionDto {
        private Long id;
        private String gubun; // 입금
        private String sender; // ATM
        private String receiver;
        private Long amount;
        private String createdAt;

        public TransactionDto(Transaction transaction) {
            this.id = transaction.getId();
            this.gubun = transaction.getGubun().getValue();
            this.sender = transaction.getSender();
            this.receiver = transaction.getReceiver();
            this.amount = transaction.getAmount();
            this.createdAt = CustomDateUtil.toStringFormat(transaction.getCreatedAt());
        }
    }
}
