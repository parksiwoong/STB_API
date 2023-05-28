package dev.b.b01.board.mappers;

import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface BoardDao {
    void insertBoardDao()throws Exception;

    void anonymousAddBoard()throws Exception;
}
