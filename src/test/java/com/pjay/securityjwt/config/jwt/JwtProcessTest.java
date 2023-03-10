package com.pjay.securityjwt.config.jwt;

import com.pjay.securityjwt.config.auth.LoginUser;
import com.pjay.securityjwt.enum_package.UserRoleType;
import com.pjay.securityjwt.modules.user.domain.User;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.*;


class JwtProcessTest {

    @Test
    void create_test() {
        // given
        User user = User.builder().id(1L).role(UserRoleType.CUSTOMER).build();
        LoginUser loginUser = new LoginUser(user);

        // when
        String jwtToken = JwtProcess.create(loginUser);
        System.out.println("테스트 : "+jwtToken);

        // then
        assertTrue(jwtToken.startsWith(JwtVO.TOKEN_PREFIX));
    }

    @Test
    void verify_test() {
        // given
        String token = "eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJiYW5rIiwicm9sZSI6IkNVU1RPTUVSIiwiaWQiOjEsImV4cCI6MTY3OTAxNjUwM30.-cV9to2VkPJE7yK5BJ9hsxqE6TH0kXsI4_MAIbOJ6DA7x7qEGal7D2NJKWop0ap-fYbDhhguq4FZnsLkt-7CsQ";

        // when
        LoginUser loginUser = JwtProcess.verify(token);
        System.out.println("테스트 : "+loginUser.getUser().getId());

        // then
        assertThat(loginUser.getUser().getId()).isEqualTo(1L);
    }
}