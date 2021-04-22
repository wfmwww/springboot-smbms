package cn.smbms.controller;

import cn.smbms.entity.User;
import cn.smbms.service.user.UserService;
import cn.smbms.util.ExportExcelUtil;
import cn.smbms.util.ImportExcelUtil;
import org.apache.commons.io.FileUtils;
import org.apache.poi.ss.usermodel.Workbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;

@Controller
@RequestMapping("/sys/poi")
public class PoiController {

    @Autowired
    private UserService userService;

    //下载所有用户信息
    @RequestMapping("/downloadUserAll.html")
    public void downloadUserAll(HttpServletResponse response) {
        //偷个懒，没写查询所有的方法
        List<User> userList = userService.getUserList(null, null, 1, 1000);
        //工作簿的名称
        String sheetName = "j93";
        ServletOutputStream outputStream = null;
        try {
            //工具类的方法
            Workbook workbook = ExportExcelUtil.writeNewExcel(sheetName, userList);
            //写出
            outputStream = response.getOutputStream();
            //将内容写出到流中   excel文档格式
            //contentType
            //下载   content-disposition
            response.setContentType("application/vnd.ms-excel;charset=utf-8");
            response.setHeader("content-disposition", "attachement;fileName=abc.xlsx");

            workbook.write(outputStream);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                outputStream.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    //下载模板
    @RequestMapping("/downloadUserModel")
    public void downloadUserModel(HttpServletResponse response) {
        ServletOutputStream outputStream = null;
        //下载
        //工作薄名称
        try {
            //获取到File对象
            File file = ExportExcelUtil.getExcelDemoFile("demo.xlsx");
            //获取文件字节数组
            byte[] bytes = FileUtils.readFileToByteArray(file);
            outputStream = response.getOutputStream();
            //将内容写出到流中   excel文档格式
            //contentType
            //下载   content-disposition
            response.setContentType("application/vnd.ms-excel;charset=utf-8");
            response.setHeader("content-disposition", "attachement;fileName=demo.xlsx");
            outputStream.write(bytes);

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                outputStream.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    //上传
    @RequestMapping(value = "/uploadUserExcel.html", method = RequestMethod.POST)
    public String uploadUserExcel(MultipartFile myFile) {
        String originalFilename = myFile.getOriginalFilename();
        InputStream inputStream = null;
        //调用方法进行处理
        //System.out.println("1111----------------------------------------");
        try {
            inputStream = myFile.getInputStream();
            //System.out.println("222----------------------------------------");
            List<List<Object>> bankListByExcel = ImportExcelUtil.getBankListByExcel(inputStream, originalFilename);
            for(int i = 1 ; i < bankListByExcel.size(); i++) {
                //遍历  循环进行新增
                List<Object> list = bankListByExcel.get(i);
                //用户编号
                Object userCode0 = list.get(0);
                String userCode = (String)userCode0;
                //用户名
                Object userName0 = list.get(1);
                String userName = (String)userName0;
                //用户编号
                Object address0 = list.get(2);
                String address = (String)address0;
                User user = new User();
                user.setUserCode(userCode);
                user.setUserName(userName);
                user.setAddress(address);

                //新增
                userService.addUser(user);
            }

            } catch (IOException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }finally {
            try {
                inputStream.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }


            return "redirect:/sys/user/userList.html";
    }



}
