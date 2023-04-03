package com.pjay.securityjwt.config.dummy;

import com.pjay.securityjwt.enum_package.UserRoleType;
import com.pjay.securityjwt.modules.account.domain.Account;
import com.pjay.securityjwt.modules.user.domain.User;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.time.LocalDateTime;

public class DummyObject {
    protected User newUser(String username, String fullname){
        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        String encPassword = passwordEncoder.encode("1234");
        return User.builder()
                .username(username)
                .password(encPassword)
                .email(username + "@gmail.com")
                .fullname(fullname)
                .role(UserRoleType.CUSTOMER)
                .build();
    }

    protected User newMockUser(Long id, String username, String fullname){
        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        String encPassword = passwordEncoder.encode("1234");
        return User.builder()
                .id(id)
                .username(username)
                .password(encPassword)
                .email(username + "@gmail.com")
                .fullname(fullname)
                .role(UserRoleType.CUSTOMER)
                .createdAt(LocalDateTime.now())
                .updatedAt(LocalDateTime.now())
                .build();
    }

    protected Account newAccount(Long number, User user){
        return Account.builder()
                .number(number)
                .password(1234L)
                .balance(1000L)
                .user(user)
                .build();
    }

    protected Account newMockAccount(Long id, Long number, User user, Long balance){
        return Account.builder()
                .id(id)
                .number(number)
                .password(1234L)
                .balance(balance)
                .user(user)
                .build();
    }
}
