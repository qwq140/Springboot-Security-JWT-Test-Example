package com.pjay.securityjwt.modules.user.dto.response;

import com.pjay.securityjwt.modules.user.domain.User;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@ToString
@Getter
@Setter
public class JoinRespDto {
    private Long id;
    private String username;
    private String fullname;

    public JoinRespDto(User user) {
        this.id = user.getId();
        this.username = user.getUsername();
        this.fullname = user.getPassword();
    }
}