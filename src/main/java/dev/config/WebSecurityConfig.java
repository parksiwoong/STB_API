package dev.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

import javax.sql.DataSource;

@Configuration
@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    private DataSource dataSource;

    @Override
    //파라메타로 HttpSecurity 전달을 받아서 어떤 보안설정을 할것인지 설정해주면 됨
    public void configure(HttpSecurity http) throws Exception {
        http
            .authorizeRequests()
                .antMatchers("/*").permitAll()  //url정의 permitAll 로그인없이도 접근가능
                .anyRequest()
                .authenticated()       //("/", "/home") 그밖에 어떤 요청이 와도 .authenticated() 반드시 로그인을 해야만 볼 수 있음
                .and()
            .formLogin()
                .loginPage("/member/userLogin")                //("/", "/home")외에 들어왔을때 이 페이지로 자동으로 페이지 이동함
                .permitAll()                        //로그인 되지 않은 사용자도 ("/login") 에 접근 할 수 있게permitAll() 해줌
                .and()
            .logout()                               //permitAll() 누구나 로그아웃 할 수 있도록
                .permitAll();
    }

    public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception{
        auth.jdbcAuthentication()
                .dataSource(dataSource)
                .usersByUsernameQuery("select * from tb_user_info where user_id = ? and user_pwd = ?")
                .authoritiesByUsernameQuery("select * from tb_user_info where user_id = ? and user_pwd = ?");
    }
    //css ㅊ추가해줘야함
    @Bean
    public WebSecurityCustomizer webSecurityCustomizer() {
        return (web) -> web.ignoring().antMatchers("/static/js/**", "/static/img/**", "/static/css/**", "/static/scss/**","/mappers/**").anyRequest();
    }
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}

