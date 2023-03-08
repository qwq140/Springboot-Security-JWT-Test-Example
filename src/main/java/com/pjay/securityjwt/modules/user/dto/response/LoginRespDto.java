package com.pjay.securityjwt.modules.user.dto.response;

import com.pjay.securityjwt.modules.user.domain.User;
import com.pjay.securityjwt.utils.CustomDateUtil;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class LoginRespDto {
    private Long id;
    private String username;
    private String createdAt;

    public LoginRespDto(User user) {
        this.id = user.getId();
        this.username = user.getUsername();
        this.createdAt = CustomDateUtil.toStringFormat(user.getCreatedAt());
    }
}
