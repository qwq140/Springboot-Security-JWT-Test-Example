package com.pjay.securityjwt.modules.user.service;

import com.pjay.securityjwt.handler.ex.CustomApiException;
import com.pjay.securityjwt.modules.user.domain.User;
import com.pjay.securityjwt.modules.user.domain.UserRepository;
import com.pjay.securityjwt.modules.user.dto.request.JoinReqDto;
import com.pjay.securityjwt.modules.user.dto.response.JoinRespDto;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@RequiredArgsConstructor
@Service
public class UserService {
    private final Logger log = LoggerFactory.getLogger(getClass());
    private final UserRepository userRepository;
    private final BCryptPasswordEncoder passwordEncoder;

    // 서비스는 dto로 받고 응답을 한다.
    @Transactional
    public JoinRespDto save(JoinReqDto joinReqDto){
        // 1. 동일 유저네임 존재 검사
        Optional<User> userOP = userRepository.findByUsername(joinReqDto.getUsername());
        if(userOP.isPresent()){
            // 유저네임 중복됨
            throw new CustomApiException("동일한 유저네임이 존재합니다");
        }
        // 2. 패스워드 인코딩 + 회원가입
        User userPS = userRepository.save(joinReqDto.toEntity(passwordEncoder));

        // 3. dto 응답
        return new JoinRespDto(userPS);
    }
}
