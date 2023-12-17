package dev.z.z01.common;

import lombok.extern.slf4j.Slf4j;
import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import java.io.IOException;

@WebFilter(urlPatterns = {"/*"})
@Slf4j
public class FilterController implements Filter { //filter : import javax.servlet.*;


    @Override
    public void doFilter( ServletRequest request
                        , ServletResponse response
                        , FilterChain chain) throws IOException, ServletException {
        System.out.println("filter controller");
        chain.doFilter(request,response); // 필터 역할
    }
}
