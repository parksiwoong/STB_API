package dev.a.a01.auth;


import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;

@Slf4j
@Controller
@RequestMapping("/auth")
public class KakaoAuthLogin {


    /**
     * 카카오 로그인 콜백
     *
     * */
    @GetMapping("/kakao/callback")
    public @ResponseBody String kakaoCallback(String code) {

        log.info("로그인페이지에서 카카오 리다이렉트한 인가코드 요청 : {}", code);
        //post방식으로 key=value 데이터를 요청 ( 카카오 쪽으로)
        //http로 전달하는 방식 : HttpsURLConnection , Restorfit2 (안드로이드에서 많이씀), OkHttp ,RestTemplate
        RestTemplate rt = new RestTemplate();

        // HttpHeader 오브젝트 생성
        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-type", "application/x-www-form-urlencoded;charset=utf-8");

        //httpBody 오브젝트 생성
        MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
        params.add("grant_type", "authorization_code");
        params.add("client_id", "e00c43844fd59c675337d27c0d3947a7");
        params.add("redirect_uri", "http://localhost:82/auth/kakao/callback");
        params.add("code", code);

        // Httpheader 와 HttpBody 하나의 오브젝트에 담기 ,밑에 코드는 exchange는 httpEntity를 받기때문에 만듬
        HttpEntity<MultiValueMap<String, String>> kakaoToKenRequest = new HttpEntity<>(params, headers);

        //엑세스토큰 발급받아오기
        ResponseEntity<String> responseAccessToken = rt.exchange(
                "https://kauth.kakao.com/oauth/token"
                , HttpMethod.POST
                , kakaoToKenRequest
                , String.class);

        //TODO: 정리 필요
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


        /******************************************** */
        // 사용자 정보 요청하기
        RestTemplate rt2 = new RestTemplate();

        // HttpHeaders 오브젝트 생성
        HttpHeaders headersProFile = new HttpHeaders();
        headersProFile.add("Authorization", "Bearer "+oauthToken.getAccess_token());
        headersProFile.add("Content-type", "application/x-www-form-urlencoded;charset=utf-8");

        // Httpheader 와 HttpBody 하나의 오브젝트에 담기 ,밑에 코드는 exchange는 httpEntity를 받기때문에 만듬
        // HttpHeaders만 오브젝트에 담기
        HttpEntity<MultiValueMap<String, String>> kakaoProfileRequest2 =
                new HttpEntity<>(headersProFile);

        // Http 요청하기 - POST방식으로 그리고 response 변수의 응답 받음
        ResponseEntity<String> responseProFile = rt2.exchange(
                "https://kapi.kakao.com/v2/user/me", //토큰 발급 요청 주소
                HttpMethod.POST, //요청 메서드 post
                kakaoProfileRequest2,
                String.class // 응답받을 타입
        );

        log.info("프로파일 정보 : {}",responseProFile.getBody());

        /******************************************** */
        //TODO: 정리 필요
        // reponse 로 요청 받고 난 데이터로
        // Gson, Json Simpe, ObjectMapper 같은 라이브러리로 다시 담음
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

        log.info("카카오 아이디(번호) : {}",kakaoProfile.getId());
        log.info("카카오 이메일 : {}",kakaoProfile.getKakao_account().getEmail());

        return responseProFile.getBody();
    }
}
