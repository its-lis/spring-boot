package com.autumn.weather.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URLEncoder;

@Slf4j
@Controller
public class AvatarDownloadController {

    @ResponseBody
    @RequestMapping("/avatarDownload")
    public void avatarDownload(HttpServletResponse response) {
        File avatar = null;
        InputStream in = null;
        OutputStream out = null;
        try {
            response.setHeader("content-disposition","attachment;filename=" + URLEncoder.encode("avatar.jpg", "UTF-8"));
            ClassPathResource classPathResource = new ClassPathResource("static//avatar/avatar.jpg");
            in = classPathResource.getInputStream();
            out = response.getOutputStream();
            byte buffer[] = new byte[1024];
            int len = 0;
            log.info("读取开始啦！！！");
            while ((len = in.read(buffer)) > 0) {
                out.write(buffer, 0, len);
            }
            log.info("读取结束啦！！！");
        } catch (IOException e) {
            log.error("读取出错啦！！！");
            e.printStackTrace();
        } finally {
            try {
                in.close();
                out.close();
            } catch (IOException e) {
                log.error("流关闭出错啦！！！");
                e.printStackTrace();
            }

        }
    }
}
