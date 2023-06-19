package com.pjay.securityjwt.modules.account.dto.response;

import com.pjay.securityjwt.modules.account.domain.Account;
import com.pjay.securityjwt.modules.transaction.domain.Transaction;
import com.pjay.securityjwt.utils.CustomDateUtil;
import lombok.Getter;
import lombok.Setter;

import java.util.List;
import java.util.stream.Collectors;

@Getter
@Setter
public class AccountDetailRespDto {

    private Long id; // 계좌 ID
    private Long number; // 계좌 번호
    private Long balance; // 계좌 최종 잔액
    private List<TransactionDto> transactions;

    public AccountDetailRespDto(Account account, List<Transaction> transactions) {
        this.id = account.getId();
        this.number = account.getNumber();
        this.balance = account.getBalance();
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
