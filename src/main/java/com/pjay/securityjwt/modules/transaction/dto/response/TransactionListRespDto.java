package com.pjay.securityjwt.modules.transaction.dto.response;

import com.pjay.securityjwt.modules.account.domain.Account;
import com.pjay.securityjwt.modules.transaction.domain.Transaction;
import com.pjay.securityjwt.utils.CustomDateUtil;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Getter
@Setter
public class TransactionListRespDto {

    private List<TransactionDto> transactions = new ArrayList<>();

    public TransactionListRespDto(List<Transaction> transactions, Account account) {
        this.transactions = transactions.stream().map(transaction -> new TransactionDto(transaction, account.getNumber())).collect(Collectors.toList());
    }

    @Getter
    @Setter
    public class TransactionDto {
        private Long id;
        private String gubun;
        private Long amount;
        private String sender;
        private String receiver;
        private String tel;
        private String createdAt;
        private Long balance;

        public TransactionDto(Transaction transaction, Long accountNumber) {
            this.id = transaction.getId();
            this.gubun = transaction.getGubun().getValue();
            this.amount = transaction.getAmount();
            this.sender = transaction.getSender();
            this.receiver = transaction.getReceiver();
            this.tel = transaction.getTel() == null ? "없음" : transaction.getTel();
            this.createdAt = CustomDateUtil.toStringFormat(transaction.getCreatedAt());

            if(transaction.getDepositAccount() == null) {
                this.balance = transaction.getWithdrawAccountBalance();
            } else if(transaction.getWithdrawAccount() == null){
                this.balance = transaction.getDepositAccountBalance();
            } else {
                if(transaction.getDepositAccount().getNumber().longValue() == accountNumber.longValue()){
                    this.balance = transaction.getDepositAccountBalance();
                } else {
                    this.balance = transaction.getWithdrawAccountBalance();
                }
            }
        }
    }
}
