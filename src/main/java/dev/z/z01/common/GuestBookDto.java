package dev.z.z01.common;

import lombok.Data;

import java.util.List;

@Data
public class GuestBookDto {

    private int articleno;
    private String userid;
    private String subject;
    private String content;
    private String regtime;
    private List<FileInfoDto> fileInfos;

}
