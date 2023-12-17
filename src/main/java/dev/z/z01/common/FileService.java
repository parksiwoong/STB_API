package dev.z.z01.common;


import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

public interface FileService {

    /* 업로드 파일 */
    public void uploadFile(GuestBookDto guestBookDto, @RequestParam("upfile") MultipartFile[] files) throws IllegalStateException, IOException;
}

