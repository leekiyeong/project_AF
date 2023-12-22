package com.project.projectAF.controller;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class mainController {
    @GetMapping("/index")
    public String mainView(){

        return "index";
    }

    @GetMapping("/login")
    public String loginForm(){

        return "login";
    }

    @GetMapping("/naverLogin")
    public String naverLogin(){

        return "naverLogin";
    }

    @GetMapping("/kakaoLogin")
    public String kakaoLogin(){

        return "kakaoLogin";
    }

    @GetMapping("/kakaoLoginTest")
    public String kakaoLoginTest(){

        return "kakaoLoginTest";
    }
}
