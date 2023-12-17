package dev.b.b01.board;

import lombok.Data;
import org.apache.tomcat.jni.File;

@Data
public class FileVo {
    private String UUID;        // unique 한 파일 이름을 만들기 위한 속성
    private String uuid;        // unique 한 파일 이름을 만들기 위한 속성
    private String fileName;    //실제파일이름
    private String contentType;


    public FileVo(String uuid, String fileName, String contentType){
        this.uuid = uuid;
        this.fileName = fileName;
        this.contentType = contentType;
        System.out.println(contentType);
    }
}
