package dev.b.b01.board.impl;

import dev.b.b01.board.BoardService;
import dev.b.b01.board.mappers.BoardDao;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

@Service(value = "bardService")
public class BoardServiceImpl implements BoardService {

    private BoardDao dao;

    BoardServiceImpl(BoardDao dao){
        this.dao = dao;
    }
    public void anonymousAddBoard()throws Exception{
        dao.anonymousAddBoard();
    }
}
