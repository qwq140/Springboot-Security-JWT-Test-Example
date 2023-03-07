package com.pjay.securityjwt.modules.user.dto.request;

import com.pjay.securityjwt.enum_package.UserRoleType;
import com.pjay.securityjwt.modules.user.domain.User;
import lombok.Getter;
import lombok.Setter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@Getter
@Setter
public class JoinReqDto{
    private String username;
    private String password;
    private String email;
    private String fullname;

    public User toEntity(BCryptPasswordEncoder passwordEncoder){
        return User.builder()
                .username(username)
                .password(passwordEncoder.encode(password))
                .email(email)
                .fullname(fullname)
                .role(UserRoleType.CUSTOMER)
                .build();
    }
}