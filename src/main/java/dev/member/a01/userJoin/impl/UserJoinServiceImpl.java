package dev.member.a01.userJoin.impl;

import dev.member.a01.userJoin.UserJoinService;
import dev.member.a01.userJoin.UserJoinVo;
import dev.member.a01.userJoin.mappers.UserJoinDao;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
//import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.sql.SQLException;
import java.util.HashMap;

@Slf4j
@Service(value = "userJoinService")
public class UserJoinServiceImpl implements UserJoinService {
    private final UserJoinDao dao;

    @Autowired
    public UserJoinServiceImpl(UserJoinDao dao){
        this.dao = dao;
    }
//    public List<UserJoinVo> userJoinService(UserJoinVo vo) {
//
//        return dao.userJoinDao();
//    }

    @Override
    public int checkdUplicationUserId(UserJoinVo vo) throws SQLException {
        return dao.checkdUplicationUserId(vo);
    }
    /* 사용자 순번 찾기 */
    public UserJoinVo findUserSn(UserJoinVo vo)throws SQLException{
        vo = dao.findUserSn(vo);
        return vo;
    }

    public String userInsert(UserJoinVo vo) throws SQLException{

        String resultSn  = "";

        log.info("jade - vo2: " + vo);
        //스퀸스 번호 호출 모듈
        vo.setGroupNm("SN");


        log.info("jade - dao: " + dao.userSelect(vo).getUserSn());
        // _todo null 로 들어가서 에러 뜸 고쳐야함 (그리고 활성화해야 함)
   //      vo.setUserSn(dao.userSelect(vo).getUserSn());

        //중복 가입 확인 ci값으로 확인
        /*
        HashMap duplmap = dao.findUserSn(vo);

        if(duplmap != null && !duplmap.equals("")){
            return String.valueOf(duplmap.get("userSn"));
        }
        */

        //중복 가입 확인 END

        encryptionPassWord(vo.getUserPwd());

        log.info( vo.getUserPwd() + "<-@@@@@@@@@ out vo.getUserPwd() ");

        log.info(vo + "#########");
        //사용자 정보 저장
       dao.userInsert(vo);
      //  String userSn = vo.getUserSn();
        resultSn = vo.getUserSn();
        return resultSn;
    }


    /* 비밀번호 암호화 BCryptPasswordEncoder */
    public UserJoinVo encryptionPassWord(String passWord){
        UserJoinVo vo = new UserJoinVo();
        vo.setUserPwd(passWord);
        //비밀번호 암호화
        if(!StringUtils.isEmpty(vo.getUserPwd())){
            BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
            log.info( vo.getUserPwd() + "<-@@@@@@@@@ in vo.getUserPwd() ");
            vo.setUserPwd(encoder.encode(vo.getUserPwd()));
        }
        return vo;
    }

}
