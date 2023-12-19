package dev.member.auth;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Slf4j
@Service("kakaoAuthLoginService")
public class KakaoAuthServiceImpl implements KakaoAuthService {

    /**
     * 카카오프로파일
     * @param responseAccessToken
     * @apiNote 카카오 프로파일 정보를 가지고오는 통신
     *
     * @since 2023. 12. 19
     * */
    public OAuthToken kakaoAccessTokens(ResponseEntity<String> responseAccessToken){
        // Gson, Json Simpe, ObjectMapper 같은 라이브러리로 다시 담음
        ObjectMapper objectMapper = new ObjectMapper();
        OAuthToken oauthToken = null;
        try {
            // 엑세스 토큰 담기
            oauthToken = objectMapper.readValue(responseAccessToken.getBody(), OAuthToken.class);
        } catch (JsonMappingException e) {
            e.printStackTrace();
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        log.info("카카오 엑세스 토큰 access_token  : {}", oauthToken.getAccess_token());


        return oauthToken;
    }
    /**
    * 카카오프로파일
    * @param responseProFile
    * @apiNote 카카오 프로파일 정보를 가지고오는 통신

    * @since 2023. 12. 19
    * */
    public KakaoProfile kakaoProFile(ResponseEntity<String> responseProFile) {
        ObjectMapper objectMapper2 = new ObjectMapper();
        KakaoProfile kakaoProfile = null;
        try {
            /* 사용자 프로파일 정보 담기 */
            kakaoProfile = objectMapper2.readValue(responseProFile.getBody(), KakaoProfile.class);
        } catch (JsonMappingException e) {
            e.printStackTrace();
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        return kakaoProfile;
    }
}
