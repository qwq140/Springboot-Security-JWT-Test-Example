package com.pjay.securityjwt.modules.user.dto.request;

import com.pjay.securityjwt.enum_package.UserRoleType;
import com.pjay.securityjwt.modules.user.domain.User;
import lombok.Getter;
import lombok.Setter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

/**
 * username
 * 영문, 숫자, 길이 최소 2~20자 이내
 * password
 * 길이 4~20
 * email
 * 이메일 형식
 * fullname
 * 영문, 한글, 길이 1~20
 */
@Getter
@Setter
public class JoinReqDto{

    @NotEmpty // null 이거나 공백일 수 없다.
    @Pattern(regexp = "^[a-zA-Z0-9]{2,20}$", message = "영문/숫자 2~20자 이내로 작성해주세요")
    private String username;
    @NotEmpty
    @Size(min = 4, max = 20)
    private String password;
    @NotEmpty
    @Pattern(regexp = "[a-zA-Z0-9]{2,10}@[a-zA-Z0-9]{2,6}\\.[a-zA-Z]{2,3}", message = "이메일 형식으로 작성해주세요")
    private String email;
    @NotEmpty
    @Pattern(regexp = "^[a-zA-Z가-힣]{1,20}$", message = "한글/영문 1~20자 이내로 작성해주세요")
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