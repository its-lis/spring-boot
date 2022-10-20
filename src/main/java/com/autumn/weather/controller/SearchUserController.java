package com.autumn.weather.controller;

import com.autumn.weather.entity.User;
import com.autumn.weather.service.SearchUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class SearchUserController {

    @Autowired
    private SearchUserService service;


    @ResponseBody
    @GetMapping("/searchUser")
    public User searchUser() {
        int i = service.truncateTestUser();
        System.out.println(i);
        return service.searchUser();
    }
}
