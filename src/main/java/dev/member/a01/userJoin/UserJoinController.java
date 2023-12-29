package dev.member.a01.userJoin;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.sql.SQLException;
import java.util.HashMap;

/**
 * 회원 가입/탈퇴 관련
 *
 * */
@Slf4j
@Controller
@RequestMapping("/userJoin")
public class UserJoinController {

    @Resource(name = "userJoinService")
    UserJoinService service;

    @RequestMapping("/memberGetInsert")
    public String _userGetJoin(){
        return "/dev/a/a01/userJoin";
    }

    @RequestMapping("/memberGetInsert_2")
    public String _userGetJoin_2(){
        return "/dev/a/a01/userJoin_2";
    }


    /**
     * 사용자 아이디 중복체크
     * @param vo
     * @param map
     * @since 23.05.28
     * */
    @ResponseBody
    @RequestMapping("/checkdUplication")
    public HashMap<String,Object> _checkdUplication(UserJoinVo vo, ModelMap map)throws SQLException {
        HashMap<String, Object> hm = new HashMap<String, Object>();
        int result = service.checkdUplicationUserId(vo);
         map.addAttribute("result",result);
        hm.put("result",result);
        return hm;
    }

    /**
     * 사용자 회원가입
     * @param request
     * @param redirct
     * @param vo
     * @param session
     * @since 23.05.28
     * */
    @RequestMapping("/memberSave")
    public ModelAndView _userJoinSave(HttpServletRequest request, RedirectAttributes redirct, UserJoinVo vo, HttpSession session) throws Exception {
        int resultCnt = 1;
        ModelAndView mav = new ModelAndView();

        log.info("jade - vo: " + vo);
        log.info("jade - request: " + request);
        //로그인 저장
        String instUserSn = service.userInsert(vo);
     //   mav.setViewName("/dev/a/a01/memberInsert");
        mav.setViewName("/layout/index");

        session.setAttribute("alUserSn",instUserSn);
        mav.addObject(session);
      //  redirct.addAttribute("result", resultCnt);
        return mav;
    }
}

