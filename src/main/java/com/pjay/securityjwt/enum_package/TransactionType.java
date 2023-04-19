package com.pjay.securityjwt.enum_package;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum TransactionType {
    WITHDRAW("출금"),
    DEPOSIT("출금"),
    TRANSFER("이체"),
    ALL("입출금내역");

    private String value;
}
