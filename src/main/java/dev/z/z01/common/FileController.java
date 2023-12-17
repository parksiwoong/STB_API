package dev.z.z01.common;

import dev.b.b01.board.FileDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import javax.annotation.Resource;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpSession;
import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

@RequestMapping("/file")
@Controller
public class FileController {


    @Resource(name = "fileService")
    FileService fileService;
    @RequestMapping("/uploadView")
    public ModelAndView fileUpload(){
        ModelAndView vm = new ModelAndView();
        vm.setViewName("/dev/b/b01/fileUp");
        return vm;
    }

/*
* 와서 할일 , 업로드 파일 주석처리하고 write 에 들어간 것들 upload 로 옮기기 , 그리고 다운로드 다시 만들기
* */
 //   @RequestMapping("/upload")
// 업로드하는 파일들을 MultipartFile 형태의 파라미터로 전달된다.
  /*  public String upload(@RequestParam MultipartFile[] uploadfile, Model model)
            throws IllegalStateException, IOException {
        List<FileDto> list = new ArrayList<>();
        for (MultipartFile file : uploadfile) {
            if (!file.isEmpty()) {
                // UUID를 이용해 unique한 파일 이름을 만들어준다.
                FileDto dto = new FileDto(UUID.randomUUID().toString(),
                        file.getOriginalFilename(),
                        file.getContentType());
                list.add(dto);

                File newFileName = new File(dto.getUuid() + "_" + dto.getFileName());
                // 전달된 내용을 실제 물리적인 파일로 저장해준다.
                file.transferTo(newFileName);
            }
        }
        model.addAttribute("files", list);
        return "/dev/b/b01/result";
    }*/

 //   @RequestMapping("/upload")
 /*   public void uploadFile(GuestBookDto guestBookDto, @RequestParam("upfile") MultipartFile[] files, Model model, HttpSession session) throws IllegalStateException, IOException{
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
*/
    @RequestMapping(value = "/write", method = RequestMethod.POST)
    public void write(GuestBookDto guestBookDto, @RequestParam("upfile") MultipartFile[] files, Model model, HttpSession session) throws IllegalStateException, IOException {

        //파일업로드
        fileService.uploadFile(guestBookDto,files);
         //   guestBookDto.setUserid(memberDto.getUserid());
         //   try {
         //       guestBookService.writeArticle(guestBookDto);
         //       return "guestbook/writesuccess";
         //   } catch (Exception e) {
         //       e.printStackTrace();
         //       model.addAttribute("msg", "글작성중 문제가 발생했습니다.");
         //       return "error/error";
         //   }
      //  } else {
      //      model.addAttribute("msg", "로그인 후 사용 가능한 페이지입니다.");
      //      return "error/error";
      //  }
      //  return "guestbook/writesuccess";

        System.out.println("write : 성공");;
    }
}
