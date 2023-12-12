package com.project.projectAF.controller;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class mainController {
    @GetMapping("/index")
    public String mainView(){

        return "index";
    }
}
