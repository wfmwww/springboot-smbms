package cn.smbms.controller;

import cn.smbms.entity.Role;
import cn.smbms.entity.User;
import cn.smbms.service.role.RoleService;
import cn.smbms.service.user.UserService;
import cn.smbms.util.Constants;
import cn.smbms.util.PageSupport;
import com.alibaba.fastjson.JSONArray;
import com.mysql.cj.util.StringUtils;
import io.swagger.annotations.Api;
import org.apache.commons.io.FilenameUtils;
import org.apache.commons.lang.math.RandomUtils;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.io.File;
import java.io.IOException;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

@Controller
@RequestMapping("/sys/user")
@Api("UserController用于对用户进行操作")
public class UserController {

    @Resource
    private UserService userService;
    @Resource
    private RoleService roleService;

    private static final Logger logger = Logger.getLogger(UserController.class);

    //用户列表的展示
    @RequestMapping("/userList.html")
    public String getUserList(Model model,
                              @RequestParam(value = "queryUserName", required = false) String queryUserName,
                              @RequestParam(value = "queryUserRole", required = false) String queryUserRole,
                              @RequestParam(value = "pageIndex", required = false) String pageIndex) {
        logger.info("getUserList---->queryUserName--" + queryUserName);
        logger.info("getUserList---->queryUserRole--" + queryUserRole);
        logger.info("getUserList---->pageIndex--" + pageIndex);
        //用户列表查询
        int _queryUserRole = 0;
        List<User> userList = null;
        //设置页面容量
        int pageSize = Constants.pageSize;
        //当前页码
        int currenPageNo = 1;
        if (queryUserName == null)
            queryUserName = "";
        if (queryUserRole != null && !queryUserRole.equals(""))
            _queryUserRole = Integer.parseInt(queryUserRole);
        if (pageIndex != null) {
            try {
                currenPageNo = Integer.parseInt(pageIndex);
            } catch (NumberFormatException e) {
                return "syserror";
            }
        }
        //总数量
        int totalCount = userService.getUserCount(queryUserName, _queryUserRole);
        //总页数,调用工具类
        PageSupport pages = new PageSupport();
        pages.setCurrentPageNo(currenPageNo);//传入当前页码
        pages.setPageSize(pageSize);//传入页面容量
        pages.setTotalCount(totalCount);//传入总数量
        int totalPageCount = pages.getTotalPageCount();//得到总页数


        //控制首尾
        if (currenPageNo < 1)
            currenPageNo = 1;
        if (currenPageNo > totalPageCount)
            currenPageNo = totalPageCount;
        userList = userService.getUserList(queryUserName, _queryUserRole, currenPageNo, pageSize);
        model.addAttribute("userList", userList);
        //动态显示角色下拉列表
        List<Role> roleList = null;
        roleList = roleService.getRoleList();
        model.addAttribute("roleList", roleList);
        model.addAttribute("queryUserName", queryUserName);
        model.addAttribute("queryUserRole", queryUserRole);
        model.addAttribute("totalPageCount", totalPageCount);
        model.addAttribute("totalCount", totalCount);
        model.addAttribute("currentPageNo", currenPageNo);
        return "userlist";
    }

    //增加用户
    @RequestMapping(value = "/userAdd.html", method = RequestMethod.GET)
    public String userAdd() {
        return "useradd";
    }

    @RequestMapping(value = "/userAddSave.html", method = RequestMethod.POST)
    public String userAddSave(User user, HttpSession session,
                              HttpServletRequest request,
                              @RequestParam(value = "attachs", required = false) MultipartFile[] attachs) {
        String idPicPath = null;
        String wordPicPath = null;
        String errorInfo = null;
        boolean flag = true;
        //定义上传路径
        String path = request.getSession().getServletContext().
                getRealPath("statics" + File.separator);

        for (int i = 0; i < attachs.length; i++) {
            MultipartFile attach = attachs[i];
            //判断文件是否为空
            if (!attach.isEmpty()) {
                if (i == 0) {
                    errorInfo = "uploadFileError";
                } else {
                    errorInfo = "uploadFileError_wp";
                }
                //使用自适应的分隔符，避免windows和linux分隔符不一样
                //获取原文件名
                String oldFileName = attach.getOriginalFilename();
                //获取原文件的后缀
                String prefix = FilenameUtils.getExtension(oldFileName);
                //定义上传的大小
                int fileSize = 500000;
                if (attach.getSize() > fileSize) {
                    request.setAttribute(errorInfo, "* 上传大小不得超过500k");
                    flag = false;
                } else if (prefix.equalsIgnoreCase("jpg")
                        || prefix.equalsIgnoreCase("jpeg")
                        || prefix.equalsIgnoreCase("png")
                        || prefix.equalsIgnoreCase("pneg")) {
                    //当前系统时间+随机数+”_Personal.jpg进行重命名“
                    String fileName = System.currentTimeMillis() +
                            RandomUtils.nextInt(1000000) + "_Personal.jpg";
                    //File(String parent, String child) 根据 parent 路径名字符串和 child 路径名字符串创建一个新 File 实例
                    File targetFile = new File(path, fileName);
                    if (!targetFile.exists()) {
                        targetFile.mkdirs();
                    }
                    try {
                        //把上传的文件放到目标文件里
                        attach.transferTo(targetFile);
                    } catch (IOException e) {
                        e.printStackTrace();
                        request.setAttribute(errorInfo, "* 上传失败");
                        flag = false;
                    }
                    if (i == 0) {
                        idPicPath = path + File.separator + fileName;
                    } else if (i == 1) {
                        wordPicPath = path + File.separator + fileName;
                    }

                } else {
                    request.setAttribute(errorInfo, "* 上传格式不正确");
                    flag = false;
                }
            }
        }
        if (flag) {
            user.setCreatedBy(((User) session.getAttribute(Constants.USER_SESSION)).getId());
            user.setCreationDate(new Date());
            user.setIdPicPath(idPicPath);
            user.setWorkPicPath(wordPicPath);
            if (userService.addUser(user) > 0) {
                return "redirect:/sys/user/userList.html";
            }
        }
        return "useradd";
    }

    //根据传来的userCode进行Ajax验证是否有此用户
    @RequestMapping("/userCodeExist.html")
    @ResponseBody
    public String userCodeExist(@RequestParam String userCode) {
        logger.info("userCodeExist====" + userCode);
        HashMap<String, String> resultMap = new HashMap<String, String>();
        if (userCode==null || userCode=="") {
            resultMap.put("result", "isEmpt");
        } else {
            int i = userService.userCodeExist(userCode);
            if (i > 0) {
                resultMap.put("result", "exist");
                System.out.println("存在");
            } else {
                resultMap.put("result", "noexist");
                System.out.println("不存在");
            }
        }

        return JSONArray.toJSONString(resultMap);

    }

    //用户展示ajax
    @ResponseBody
    @RequestMapping(value = "/userView.html", method = RequestMethod.GET)
    public User view(@RequestParam String id) {
        User user = new User();
        user = userService.getUserById(id);
        return user;
    }

    //修改用户
    @RequestMapping(value = "/upUser.html", method = RequestMethod.GET)
    public String upUser(@RequestParam String uid, Model model) {
        logger.info("upUser----------------->");
        User user = userService.getUserById(uid);
        model.addAttribute(user);
        return "usermodify";
    }

    @RequestMapping(value = "/upUserSave.html", method = RequestMethod.POST)
    public String upUserSave(HttpSession session, User user,
                             HttpServletRequest request,
                             @RequestParam(value = "attachs", required = false) MultipartFile[] attachs) {
        String idPicPath = null;
        String wordPicPath = null;
        String errorInfo = null;
        boolean flag = true;
        //定义上传路径
        String path = request.getSession().getServletContext().
                getRealPath("statics" + File.separator);

        for (int i = 0; i < attachs.length; i++) {
            MultipartFile attach = attachs[i];
            //判断文件是否为空
            if (!attach.isEmpty()) {
                if (i == 0) {
                    errorInfo = "uploadFileError";
                } else {
                    errorInfo = "uploadFileError_wp";
                }
                //使用自适应的分隔符，避免windows和linux分隔符不一样
                //获取原文件名
                String oldFileName = attach.getOriginalFilename();
                //获取原文件的后缀
                String prefix = FilenameUtils.getExtension(oldFileName);
                //定义上传的大小
                int fileSize = 500000;
                if (attach.getSize() > fileSize) {
                    request.setAttribute(errorInfo, "* 上传大小不得超过500k");
                    flag = false;
                } else if (prefix.equalsIgnoreCase("jpg")
                        || prefix.equalsIgnoreCase("jpeg")
                        || prefix.equalsIgnoreCase("png")
                        || prefix.equalsIgnoreCase("pneg")) {
                    //当前系统时间+随机数+”_Personal.jpg进行重命名“
                    String fileName = System.currentTimeMillis() +
                            RandomUtils.nextInt(1000000) + "_Personal.jpg";
                    //File(String parent, String child) 根据 parent 路径名字符串和 child 路径名字符串创建一个新 File 实例
                    File targetFile = new File(path, fileName);
                    if (!targetFile.exists()) {
                        targetFile.mkdirs();
                    }
                    try {
                        //把上传的文件放到目标文件里
                        attach.transferTo(targetFile);
                    } catch (IOException e) {
                        e.printStackTrace();
                        request.setAttribute(errorInfo, "* 上传失败");
                        flag = false;
                    }
                    if (i == 0) {
                        idPicPath = path + File.separator + fileName;
                        user.setIdPicPath(idPicPath);
                    } else if (i == 1) {
                        wordPicPath = path + File.separator + fileName;
                        user.setWorkPicPath(wordPicPath);
                    }

                } else {
                    request.setAttribute(errorInfo, "* 上传格式不正确");
                    flag = false;
                }
            } else {
                User user2 = userService.getUserById(user.getId().toString());
                if(i == 0)
                    user.setIdPicPath(user2.getIdPicPath());
                if (i == 1) {
                    user.setWorkPicPath(user2.getWorkPicPath());
                }
            }

        }


        user.setModifyDate(new Date());
        user.setModifyBy(((User) (session.getAttribute(Constants.USER_SESSION))).getId());
        if (user.getId()==((User)(session.getAttribute(Constants.USER_SESSION))).getId()) {
            session.setAttribute(Constants.USER_SESSION,user);
        }
        int i1 = userService.upUserById(user);
        if(i1>0)
            return "redirect:/sys/user/userList.html";
        return "usermodify";
    }


    //ajax获取用户角色
    @ResponseBody
    @RequestMapping("/role.html")
    public Object getRoleName(){
        List<Role> roleList = roleService.getRoleList();
        return roleList;
    }

    //删除用户
    @ResponseBody
    @RequestMapping("/delUser.html")
    public String delUser(String uid){
        HashMap<String, String> result = new HashMap<String,String>();
        if(org.apache.commons.lang.StringUtils.isEmpty(uid)){
            result.put("delResult", "notexist");
        }else {
            int i = userService.delUserById(uid);
            if(i>0){
                result.put("delResult", "true");
            }else{
                result.put("delResult", "false");
            }

        }
        return JSONArray.toJSONString(result);
    }

}
