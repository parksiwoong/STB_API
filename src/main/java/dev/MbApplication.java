package dev;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.ServletComponentScan;

@ServletComponentScan //필터 어노테이션추가
@SpringBootApplication
//@MapperScan(basePackages = "dev.yhp.study.mb.mappers")
@MapperScan(basePackages = "dev.**.**.**.mappers , dev.**.**.**.**.mappers") //java쪽 주소 맵핑 (dao)
public class MbApplication {
    public static void main(String[] args) {
       // System.setProperty("server.servlet.context-path", "/");
        SpringApplication.run(MbApplication.class, args);
    }
}