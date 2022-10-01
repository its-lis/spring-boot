package com.autumn.weather.controller;

import org.springframework.stereotype.Controller;
import org.springframework.util.ResourceUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.net.URLEncoder;

@Controller
public class AvatarDownloadController {

    @ResponseBody
    @RequestMapping("/avatarDownload")
    public void avatarDownload(HttpServletResponse response) {
        File avatar = null;
        try {
            avatar = ResourceUtils.getFile("src/main/resources/static/avatar/avatar.jpg");
            response.setHeader("content-disposition","attachment;filename=" + URLEncoder.encode("avatar.jpg", "UTF-8"));
            FileInputStream in = new FileInputStream(avatar);
            OutputStream out = response.getOutputStream();
            byte buffer[] = new byte[1024];
            int len = 0;
            while ((len = in.read(buffer)) > 0) {
                out.write(buffer, 0, len);
            }
            in.close();
            out.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
