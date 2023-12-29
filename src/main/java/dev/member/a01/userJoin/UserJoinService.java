package dev.member.a01.userJoin;

import java.sql.SQLException;

public interface UserJoinService {
    /* 아이디 체킹 */
    int checkdUplicationUserId(UserJoinVo vo)throws SQLException;
    /* 유저 순번 조회 */
    UserJoinVo findUserSn(UserJoinVo vo)throws SQLException;
    /**/
    String userInsert(UserJoinVo vo) throws SQLException;
    public UserJoinVo encryptionPassWord(String vo);
}
