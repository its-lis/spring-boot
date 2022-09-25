package com.autumn.weacher.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class HelloLinuxController {

    @RequestMapping("helloLinux")
    public String helloLinux() {
        return "helloLinux";
    }


}
