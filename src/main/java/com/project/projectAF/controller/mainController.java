package com.project.projectAF.controller;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

//test
//test2
@Controller
public class mainController {
    @GetMapping("/mainView")
    public String mainView(){

        return "mainView";
    }
}
