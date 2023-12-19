package dev.member.auth;


import dev.member.a01.userJoin.UserJoinService;
import dev.member.a01.userJoin.UserJoinVo;
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


import javax.annotation.Resource;

import java.sql.SQLException;
import java.util.UUID;

@Slf4j
@Controller
@RequestMapping("/auth")
public class KakaoAuth {

    @Resource(name = "userJoinService")
    UserJoinService userJoinService;

    @Resource(name = "kakaoAuthLoginService")
    KakaoAuthService kakaoAuthService;


    /**
     * 카카오 로그인 콜백
     * 1 인가코드 , 2 엑세스코드 , 3 프로파일정보
     * @param code
     * @throws SQLException
     * @since 2023.12.19
     * */
    @GetMapping("/kakao/callback")
    public @ResponseBody String kakaoCallback(String code) throws SQLException {

        /** 1. 인가코드 code ****************************************** */
        log.info("로그인페이지에서 카카오 리다이렉트한 인가코드 요청 : {}", code);

        /** 2. 엑세스코드 ****************************************** */
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

        OAuthToken oauthToken =  kakaoAuthService.kakaoAccessTokens(responseAccessToken);


        /** 3. 프로파일정보 ****************************************** */
        // 사용자 정보 요청하기
        RestTemplate rt2 = new RestTemplate();

        // HttpHeaders 오브젝트 생성
        HttpHeaders headersProFile = new HttpHeaders();
        headersProFile.add("Authorization", "Bearer "+oauthToken.getAccess_token());
        headersProFile.add("Content-type", "application/x-www-form-urlencoded;charset=utf-8");

        // Httpheader 와 HttpBody 하나의 오브젝트에 담기 ,밑에 코드는 exchange는 httpEntity를 받기때문에 만듬
        // HttpHeaders만 오브젝트에 담기
        HttpEntity<MultiValueMap<String, String>> kakaoProfileRequest2 = new HttpEntity<>(headersProFile);

        // Http 요청하기 - POST방식으로 그리고 response 변수의 응답 받음
        ResponseEntity<String> responseProFile = rt2.exchange(
                "https://kapi.kakao.com/v2/user/me", //토큰 발급 요청 주소
                HttpMethod.POST, //요청 메서드 post
                kakaoProfileRequest2,
                String.class // 응답받을 타입
        );

        log.info("프로파일 정보 : {}",responseProFile.getBody());

        KakaoProfile kakaoProfile = kakaoAuthService.kakaoProFile(responseProFile);

        // User 오브젝트 : userNm , userPw, email
        log.info("카카오 아이디(번호) : {}",kakaoProfile.getId());
        log.info("카카오 이메일 : {}",kakaoProfile.getKakao_account().getEmail());
        log.info("카카오 유저네임: {},{},{}", kakaoProfile.getKakao_account().getEmail() , "_", kakaoProfile.getId());

        // 임시 일회용 garbage 패스워드
        UUID garbagePw = UUID.randomUUID();
        log.info("카카오 비밀번호: {}", garbagePw);

        UserJoinVo userVo = new UserJoinVo();
        userVo.setId(kakaoProfile.getId());
        userVo.setUserEmail(kakaoProfile.getKakao_account().getEmail());



        String tmp = String.valueOf(userJoinService.encryptionPassWord(String.valueOf(garbagePw)));
        userVo.setUserPwd(tmp);
        log.info("user 정보 id : {}",userVo.toString());

        /*회원찾기 이메일과 이름*/

        /*회원이 없으면 가입 */
       /* service.userInsert(userVo);*/

        return responseProFile.getBody();
    }


}
