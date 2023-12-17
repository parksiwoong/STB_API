package dev.z.z01.common.impl;


import dev.z.z01.common.FileInfoDto;
import dev.z.z01.common.FileService;
import dev.z.z01.common.GuestBookDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.ServletContext;
import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

@Service(value = "fileService")
public class FileServiceImpl implements FileService {

    @Autowired
    ServletContext servletContext;

    @Override
    public void uploadFile(GuestBookDto guestBookDto, @RequestParam("upfile") MultipartFile[] files) throws IllegalStateException, IOException{
        //    MemberDto memberDto = (MemberDto) session.getAttribute("userinfo");
        //     if (memberDto != null) {
          String realPath = servletContext.getRealPath("/resources/upload");
            String today = new SimpleDateFormat("yyMMdd").format(new Date());
            String saveFolder = realPath + File.separator + today;
            System.out.println(saveFolder);
            File folder = new File(saveFolder);
            if(!folder.exists())
                folder.mkdirs();
            List<FileInfoDto> fileInfos = new ArrayList<FileInfoDto>();
            for (MultipartFile mfile : files) {
                FileInfoDto fileInfoDto = new FileInfoDto();
                String originalFileName = mfile.getOriginalFilename();
                if (!originalFileName.isEmpty()) {
                    String saveFileName = UUID.randomUUID().toString() + originalFileName.substring(originalFileName.lastIndexOf('.'));
                    fileInfoDto.setSaveFolder(today);
                    fileInfoDto.setOriginFile(originalFileName);
                    fileInfoDto.setSaveFile(saveFileName);
                    System.out.println(mfile.getOriginalFilename() + "   " + saveFileName);
                    mfile.transferTo(new File(folder, saveFileName));
                }
                fileInfos.add(fileInfoDto);
            }
            guestBookDto.setFileInfos(fileInfos);
            System.out.println("성공");
    }

 /*       @RequestMapping(value="/download", method= RequestMethod.GET)
        public ModelAndView downloadFile(@RequestParam("sfolder") String sfolder, @RequestParam("ofile") String ofile,
                                         @RequestParam("sfile") String sfile, HttpSession session) {
            //     MemberDto memberDto = (MemberDto) session.getAttribute("userinfo");
            //  if(memberDto != null) {
            //      Map<String, Object> fileInfo = new HashMap<String, Object>();
            fileInfo.put("sfolder", sfolder);
            fileInfo.put("ofile", ofile);
            fileInfo.put("sfile", sfile);
            System.out.println(sfolder + " " + ofile + " " + sfile);
            return new ModelAndView("fileDownLoadView", "downloadFile", fileInfo);
        } else {
            return new ModelAndView("redirect:/");
        }*/
    }
