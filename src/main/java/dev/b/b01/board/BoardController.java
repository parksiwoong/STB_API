package dev.b.b01.board;

import org.springframework.stereotype.Controller;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@RequestMapping("/b")
@Controller
public class BoardController {

    @Resource(name = "bardService")
    BoardService service;

    @RequestMapping("/fileUpload")
    public String _fileCommit(){

        return "/dev/b/b01/fileUp";
    }
}
