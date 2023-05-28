package dev.a.a01.userMember;

import dev.a.a01.userMember.impl.MemberServiceImpl;

import lombok.extern.slf4j.Slf4j;
import org.apache.juli.logging.LogFactory;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;


/**
* 로그인 관련
* */
@Slf4j
@Controller
@RequestMapping("/member")
public class MemberController {
    @Resource(name = "memberService")
    MemberServiceImpl service;

    /**
     * new로그인
     * */
    @RequestMapping("/userLogin")
    public String _memberLogin(){
        log.info("/userLogin ::: in");
        return "/dev/a/a01/login";
    }

    /**
     * 로그인
     * @param map
     * @param vo
     * */
    @RequestMapping("/user")
    public ModelAndView _members(Map map, MemberVo vo) throws Exception {
        ModelAndView mv = new ModelAndView();
        log.info("=====> @ Controller");
        mv.addObject(service.test(vo));
        mv.setViewName("/dev/a/a01/login");
        return mv;
    }

    /**
     * 로그인 아이디체크비교 */
    public ModelAndView _loginChackCompare(MemberVo vo, HttpServletRequest req, ModelMap mv)throws IOException {
        HashMap result = (HashMap)service.loginCheckCompare(vo);

        return new ModelAndView("/");
    }
}
