package com.pjay.securityjwt.config.jwt;

import com.pjay.securityjwt.config.auth.LoginUser;
import com.pjay.securityjwt.enum_package.UserRoleType;
import com.pjay.securityjwt.modules.user.domain.User;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.*;


class JwtProcessTest {

    private String createToken() {
        // given
        User user = User.builder().id(1L).role(UserRoleType.ADMIN).build();
        LoginUser loginUser = new LoginUser(user);

        // when
        String jwtToken = JwtProcess.create(loginUser);
        return jwtToken;
    }

    @Test
    void create_test() {
        // given

        // when
        String jwtToken = createToken();
        System.out.println("테스트 : "+jwtToken);

        // then
        assertTrue(jwtToken.startsWith(JwtVO.TOKEN_PREFIX));
    }

    @Test
    void verify_test() {
        // given
        String token = createToken();
        String jwtToken = token.replace(JwtVO.TOKEN_PREFIX, "");

        // when
        LoginUser loginUser = JwtProcess.verify(jwtToken);
        System.out.println("테스트 : "+loginUser.getUser().getId());
        System.out.println("테스트 : "+loginUser.getUser().getRole().name());

        // then
        assertThat(loginUser.getUser().getId()).isEqualTo(1L);
        assertThat(loginUser.getUser().getRole()).isEqualTo(UserRoleType.ADMIN);
    }
}