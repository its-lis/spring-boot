package com.autumn.weather.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.util.ResourceUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.net.URLEncoder;

@Slf4j
@Controller
public class AvatarDownloadController {

    @ResponseBody
    @RequestMapping("/avatarDownload")
    public void avatarDownload(HttpServletResponse response) {
        File avatar = null;
        FileInputStream in = null;
        OutputStream out = null;
        try {
            avatar = ResourceUtils.getFile("src/main/resources/static/avatar/avatar.jpg");
            response.setHeader("content-disposition","attachment;filename=" + URLEncoder.encode("avatar.jpg", "UTF-8"));
            in = new FileInputStream(avatar);
            out = response.getOutputStream();
            byte buffer[] = new byte[1024];
            int len = 0;
            log.info("开始读取！！！");
            while ((len = in.read(buffer)) > 0) {
                out.write(buffer, 0, len);
            }
            log.info("读取结束！！！");
        } catch (IOException e) {
            log.error("读取出错啦！！！");
            e.printStackTrace();
            log.error("读取出错啦！！！");
        } finally {
            try {
                in.close();
                out.close();
            } catch (IOException e) {
                log.error("流关闭出错啦！！！");
                e.printStackTrace();
                log.error("流关闭出错啦！！！");
            }

        }
    }
}
