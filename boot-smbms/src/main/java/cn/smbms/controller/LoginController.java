package cn.smbms.controller;

import cn.smbms.entity.User;
import cn.smbms.service.user.UserService;
import cn.smbms.util.Constants;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.IncorrectCredentialsException;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;
import java.util.HashMap;


@Controller
public class LoginController {
    //private Logger logger = Logger.getLogger(LoginController.class);
    @Resource
    private UserService userService;

    //跳转登录页面
    @RequestMapping("/login.html")
    public String login() {
       // logger.info("login.html===============================");
        return "login";
    }

    /*//进行登录判断
    @RequestMapping(value = "/doLogin.html", method = RequestMethod.POST)
    public String doLogin(@RequestParam(value = "userCode", required = true) String userCode,
                          @RequestParam(value = "userPassword", required = true) String userPassword,
                          HttpSession session,
                          HttpServletRequest request) {
        User user = userService.getLogin(userCode, userPassword);
        if (user == null) {
            request.setAttribute("error", "用户名或者密码错误");
            return "login";
        }
        session.setAttribute(Constants.USER_SESSION, user);
        return "redirect:/sys/main.html";

    }*/
    //shiro 登录处理
    //@ResponseBody
    @RequestMapping("/doShiroLogin.html")
    public String doLogin(@RequestParam(value = "userCode", required = true) String userCode,
                                           @RequestParam(value = "userPassword", required = true) String userPassword){
        HashMap<String, Object> map = new HashMap<String, Object>();
        try {
            //获得登录的主题对象
            Subject subject = SecurityUtils.getSubject();
            //创建UsernamePasswordToken对象
            UsernamePasswordToken token =
                    new UsernamePasswordToken(userCode, userPassword);
            //登录
            subject.login(token);
            map.put("retcode", "200");
            map.put("retmsg", "登录成功！");
            subject.getSession().setAttribute(Constants.USER_SESSION,subject.getPrincipal());
        } catch (UnknownAccountException e) {
            map.put("retcode", "500");
            map.put("retmsg", "未知账户！");
            System.out.println(map);
            return "login";
        }catch (IncorrectCredentialsException e){
            map.put("retcode", "500");
            map.put("retmsg", "密码错误！");
            System.out.println(map);
            return "login";
        }catch (Exception e){
            map.put("retcode", "500");
            map.put("retmsg", "其他错误！");
            System.out.println(map);
            return "login";
        }
        //return map;
        return "redirect:/sys/main.html";
    }



    //进入首页
    @RequestMapping("/sys/main.html")
    public String main() {
        return "frame";
    }

    /*//注销的方法
    @RequestMapping("/logOut.html")
    public String logout(HttpSession session) {
        //清楚session
        session.removeAttribute(Constants.USER_SESSION);
        return "login";
    }*/
   /* //shiro注销的方法
    @RequestMapping("/logOut.html")
    public String logout(HttpSession session) {
        //清楚session
        Subject currentUser = SecurityUtils.getSubject();
        currentUser.logout();
        return "login";
    }*/

    //修改密码
    @RequestMapping("/sys/upPassword.html")
    public String upmima(){
        return "pwdmodify";
    }
    @ResponseBody
    @RequestMapping("/sys/ajaxPw.html")
    public Object getPw(String oldpassword,HttpSession session){
        String old=((User)session.getAttribute(Constants.USER_SESSION)).getUserPassword();
        if (!StringUtils.isEmpty(oldpassword)) {
            if (oldpassword.equals(old)) {
                return "true";
            }
            return "false";
        }else
            return "error";

    }

    @RequestMapping("/sys/savePw.html")
    public String savepw(String newpassword,HttpSession session){
        int i = userService.upPw(newpassword, ((User) (session.getAttribute(Constants.USER_SESSION))).getId());
        if (i > 0) {
            session.invalidate();
            return "redirect:/login.html";
        }else
            return "pwdmodify";

    }
}
