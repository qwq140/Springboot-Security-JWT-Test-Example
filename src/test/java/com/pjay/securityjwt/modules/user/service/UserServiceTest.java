package com.pjay.securityjwt.modules.user.service;

import com.pjay.securityjwt.enum_package.UserRoleType;
import com.pjay.securityjwt.modules.user.domain.User;
import com.pjay.securityjwt.modules.user.domain.UserRepository;
import com.pjay.securityjwt.modules.user.dto.request.JoinReqDto;
import com.pjay.securityjwt.modules.user.dto.response.JoinRespDto;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.time.LocalDateTime;
import java.util.Optional;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

// Spring 관련 Bean들이 하나도 없는 환경
@ExtendWith(MockitoExtension.class)
public class UserServiceTest {

    @InjectMocks
    private UserService userService;

    @Mock // 가짜
    private UserRepository userRepository;

    @Spy // 진짜
    private BCryptPasswordEncoder passwordEncoder;

    @Test
    public void save_test() throws Exception {
        // given
        JoinReqDto joinReqDto = new JoinReqDto();
        joinReqDto.setUsername("pjay");
        joinReqDto.setPassword("1234");
        joinReqDto.setEmail("pjay@gmail.com");
        joinReqDto.setFullname("피제이");

        // stub 1
        when(userRepository.findByUsername(any())).thenReturn(Optional.empty());
//        when(userRepository.findByUsername(any())).thenReturn(Optional.of(new User()));

        // stub 2
        User user = User.builder()
                .id(1L)
                .username("pjay")
                .password("1234")
                .email("pjay@gmail.com")
                .fullname("피제이")
                .role(UserRoleType.CUSTOMER)
                .createdAt(LocalDateTime.now())
                .updatedAt(LocalDateTime.now())
                .build();
        when(userRepository.save(any())).thenReturn(user);

        // when
        JoinRespDto joinRespDto = userService.save(joinReqDto);
        System.out.println(joinRespDto.toString());

        // then
        assertThat(joinRespDto.getId()).isEqualTo(1L);
        assertThat(joinRespDto.getUsername()).isEqualTo("pjay");
    }
}
