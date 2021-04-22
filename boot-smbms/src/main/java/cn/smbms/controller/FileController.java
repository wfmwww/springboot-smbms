package cn.smbms.controller;

import org.aspectj.util.FileUtil;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;
import java.net.URLEncoder;

@Controller
@RequestMapping("/sys/file")
public class FileController {

    @RequestMapping("/download.html")
    public void down(HttpServletRequest request, HttpServletResponse response) {
        ServletOutputStream out=null;
        //需要被下文件的位置
        File file = new File("F:\\图片\\云海.jpg");
        try {
             out = response.getOutputStream();
            //将文件读为字节数组
            byte[] bytes = FileUtil.readAsByteArray(file);
            //设置下载文件相应的媒体类型
            response.setContentType("image/jpeg");
            //设置请求头为附件下载
            String name = URLEncoder.encode("云海1.jpg", "UTF-8");
            response.setHeader("content-disposition","attachement;fileName="+name);
            out.write(bytes);
        } catch (IOException e) {
            e.printStackTrace();
        }finally {
            try {
                out.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

    }
}
