package dev.member.auth;

import org.springframework.http.ResponseEntity;

public interface KakaoAuthService {
    public OAuthToken kakaoAccessTokens(ResponseEntity<String> responseAccessToken);
    public KakaoProfile kakaoProFile(ResponseEntity<String> responseProFile) ;
}
