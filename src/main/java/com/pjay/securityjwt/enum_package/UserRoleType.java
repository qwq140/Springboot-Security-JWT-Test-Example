package com.pjay.securityjwt.enum_package;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public enum UserRoleType {
    ADMIN("관리자"),
    CUSTOMER("고객");

    private String value;
}
