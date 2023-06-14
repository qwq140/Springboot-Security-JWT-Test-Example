package com.pjay.securityjwt.modules.account.dto.response;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.pjay.securityjwt.modules.account.domain.Account;
import com.pjay.securityjwt.modules.transaction.domain.Transaction;
import com.pjay.securityjwt.utils.CustomDateUtil;
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

    @Getter
    @Setter
    public class TransactionDto {
        private Long id;
        private String gubun; // 입금
        private String sender; // ATM
        private String receiver;
        private Long amount;
        @JsonIgnore
        private Long depositAccountBalance; // 클라이언트에게 전달x -> 서비스단에서 테스트 용도
        private String tel;
        private String createdAt;

        public TransactionDto(Transaction transaction) {
            this.id = transaction.getId();
            this.gubun = transaction.getGubun().getValue();
            this.sender = transaction.getSender();
            this.receiver = transaction.getReceiver();
            this.amount = transaction.getAmount();
            this.depositAccountBalance = transaction.getDepositAccountBalance();
            this.tel = transaction.getTel();
            this.createdAt = CustomDateUtil.toStringFormat(transaction.getCreatedAt());
        }
    }
}
