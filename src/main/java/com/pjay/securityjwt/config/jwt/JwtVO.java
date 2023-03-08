package com.pjay.securityjwt.config.jwt;

// SECRET은 노출되면 안된다. (클라우드 AWS - 환경변수, 파일에 있는 것을 읽던가)
// 리프레시 토큰도 필요함
public interface JwtVO {
    public static final String SECRET = "pjay"; // HS256 (대칭키)
    public static final int EXPIRATION_TIME = 1000 * 60 * 60 * 24 * 7;
    public static final String TOKEN_PREFIX = "Bearer ";
    public static final String HEADER = "Authorization";
}
