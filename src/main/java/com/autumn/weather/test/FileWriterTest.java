package com.autumn.weather.test;

import lombok.SneakyThrows;
import org.junit.jupiter.api.Test;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.thymeleaf.util.StringUtils;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.net.URLEncoder;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

@Controller
public class FileWriterTest {

    @SneakyThrows
    @Test
    void test1() {
        String filePath = "src/main/resources/tempFile/";
        File dir = new File(filePath);
        if (!dir.exists()) {
            dir.mkdir();
        }
        String fileName = String.valueOf(System.currentTimeMillis());
        File file = new File(filePath + fileName);
        if (!file.exists()) {
            file.createNewFile();
        }
        try {
            FileWriter fw = new FileWriter(filePath+fileName, true);
            BufferedWriter bw = new BufferedWriter(fw);
            bw.close();
            fw.close();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }


    @RequestMapping("/test")
    public static void main(HttpServletRequest request,HttpServletResponse response) {
        // 设置文件夹时间戳和路径位置
        String date = String.valueOf(System.currentTimeMillis());
        // 文件夹的路径，写入服务器的 /tmp 文件夹下
        String filePath = "src/main/resources/tempFile/";
        //zip路径
        String zipPath = "src/main/resources/tempZip/";
        // 导出的zip路径
        String zipFilePath = "src/main/resources/tempZip/" + date + ".zip";
        // 导出的zip名字
        String zipName = date + ".zip";

        File file1 = new File(filePath);
        if (!file1.exists()) {
            file1.mkdirs();
        }
        File file2 = new File(zipPath);
        if (!file2.exists()) {
            file2.mkdirs();
        }

        FileOutputStream fileOutputStream = null;
        try {
            fileOutputStream = new FileOutputStream(new File(zipFilePath));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        // 调用工具类压缩文件夹
        FileUtil.toZip(filePath, fileOutputStream, true);

        // 调用工具类设置响应格式
        try {
            FileUtil.downLoadFile(request, response, zipName, zipFilePath);
        } catch (UnsupportedEncodingException e) {
            throw new RuntimeException("导出失败~");
        }


    }



}
class FileUtil {

    /**
     * 压缩文件夹
     *
     * @param srcDir           要压缩的文件夹路径
     * @param out
     * @param KeepDirStructure true
     * @throws RuntimeException
     */
    public static void toZip(String srcDir, OutputStream out, boolean KeepDirStructure) throws RuntimeException {

        ZipOutputStream zos = null;
        File sourceFile = null;
        try {

            zos = new ZipOutputStream(out);
            sourceFile = new File(srcDir);
            // 压缩文件夹
            compress(sourceFile, zos, sourceFile.getName(), KeepDirStructure);
            long end = System.currentTimeMillis();
        } catch (Exception e) {
            throw new RuntimeException("zip error from ZipUtils", e);
        } finally {
            if (zos != null) {
                try {
                    zos.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            // 删除压缩之前的文件夹
            if (sourceFile != null) {

            }
        }
    }

    private static void compress(File sourceFile, ZipOutputStream zos, String name,
                                 boolean KeepDirStructure) throws Exception {
        byte[] buf = new byte[1024];
        if (sourceFile.isFile()) {
            // 向zip输出流中添加一个zip实体，构造器中name为zip实体的文件的名字
            zos.putNextEntry(new ZipEntry(name));
            // copy文件到zip输出流中
            int len;
            FileInputStream in = new FileInputStream(sourceFile);
            while ((len = in.read(buf)) != -1) {
                zos.write(buf, 0, len);
            }
            // Complete the entry
            zos.closeEntry();
            in.close();
        } else {
            File[] listFiles = sourceFile.listFiles();
            if (listFiles == null || listFiles.length == 0) {
                // 需要保留原来的文件结构时,需要对空文件夹进行处理
                if (KeepDirStructure) {
                    // 空文件夹的处理
                    zos.putNextEntry(new ZipEntry(name + "/"));
                    // 没有文件，不需要文件的copy
                    zos.closeEntry();
                }

            } else {
                for (File file : listFiles) {
                    // 判断是否需要保留原来的文件结构
                    if (KeepDirStructure) {
                        // 注意：file.getName()前面需要带上父文件夹的名字加一斜杠,
                        // 不然最后压缩包中就不能保留原来的文件结构,即：所有文件都跑到压缩包根目录下了
                        compress(file, zos, name + "/" + file.getName(), KeepDirStructure);
                    } else {
                        compress(file, zos, file.getName(), KeepDirStructure);
                    }

                }
            }
        }
    }

    /**
     * 设置导出zip的响应格式
     *
     * @param request
     * @param response
     * @param fileZip  zip的名字
     * @param filePath zip的路径
     * @throws UnsupportedEncodingException
     */
    public static void downLoadFile(HttpServletRequest request, HttpServletResponse response, String fileZip, String filePath) throws UnsupportedEncodingException {

        //进行浏览器下载
        final String userAgent = request.getHeader("USER-AGENT");
        //判断浏览器代理并分别设置响应给浏览器的编码格式
        String finalFileName = null;
        if (StringUtils.contains(userAgent, "MSIE") || StringUtils.contains(userAgent, "Trident")) {
            // IE浏览器
            finalFileName = URLEncoder.encode(fileZip, "UTF8");
            System.out.println("IE浏览器");
        } else if (StringUtils.contains(userAgent, "Mozilla")) {
            // google,火狐浏览器
            finalFileName = new String(fileZip.getBytes(), "ISO8859-1");
        } else {
            // 其他浏览器
            finalFileName = URLEncoder.encode(fileZip, "UTF8");
        }
        // 告知浏览器下载文件，而不是直接打开，浏览器默认为打开
        response.setContentType("application/x-download");
        // 下载文件的名称
        response.setHeader("Content-Disposition", "attachment;filename=\"" + finalFileName + "\"");

        ServletOutputStream servletOutputStream = null;
        try {
            servletOutputStream = response.getOutputStream();
        } catch (IOException e) {
            e.printStackTrace();
        }
        DataOutputStream temps = new DataOutputStream(
                servletOutputStream);
        // 浏览器下载文件的路径
        DataInputStream in = null;
        try {
            in = new DataInputStream(
                    new FileInputStream(filePath));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        byte[] b = new byte[2048];
        // 之后用来删除临时压缩文件
        File reportZip = new File(filePath);
        try {
            while ((in.read(b)) != -1) {
                temps.write(b);
            }
            temps.flush();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (temps != null) {
                try {
                    temps.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if (in != null) {
                try {
                    in.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if (reportZip != null) {
                // 删除服务器本地产生的临时压缩文件!
                reportZip.delete();
            }
            try {
                servletOutputStream.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

    }


}
