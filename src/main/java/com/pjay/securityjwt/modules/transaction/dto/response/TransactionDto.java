package com.pjay.securityjwt.modules.transaction.dto.response;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.pjay.securityjwt.modules.transaction.domain.Transaction;
import com.pjay.securityjwt.utils.CustomDateUtil;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class TransactionDto {
    private Long id;
    private String gubun;
    private String sender;
    private String receiver;
    private Long amount;
    @JsonIgnore
    private Long depositAccountBalance; // 클라이언트 전달 x -> 서비스단에서 테스트 용, 보내는 사람이 받는 사람의 잔액을 볼 수 있으면 안된다.
    private String tel;
    private String createAt;

    public TransactionDto(Transaction transaction) {
        this.id = transaction.getId();
        this.gubun = transaction.getGubun().getValue();
        this.sender = transaction.getSender();
        this.receiver = transaction.getReceiver();
        this.amount = transaction.getAmount();
        this.depositAccountBalance = transaction.getDepositAccountBalance();
        this.tel = transaction.getTel();
        this.createAt = CustomDateUtil.toStringFormat(transaction.getCreatedAt());
    }
}
