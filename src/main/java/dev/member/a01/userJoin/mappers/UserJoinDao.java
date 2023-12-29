package dev.member.a01.userJoin.mappers;

import dev.member.a01.userJoin.UserJoinVo;
import org.apache.ibatis.annotations.Mapper;

import java.sql.SQLException;
import java.util.HashMap;

@Mapper
public interface UserJoinDao {
    int checkdUplicationUserId(UserJoinVo vo)throws SQLException;
    //유저 중복확인
    UserJoinVo findUserSn(UserJoinVo vo)throws SQLException;

    //유저 순번 생성
    UserJoinVo userSelect(UserJoinVo vo)throws SQLException;
    /* 사용자 등록 */
    int userInsert(UserJoinVo vo);
}