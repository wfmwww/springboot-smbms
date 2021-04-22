package cn.smbms.util;

import cn.smbms.entity.User;
import cn.smbms.service.user.UserService;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.*;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.subject.Subject;
import org.apache.shiro.util.ByteSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

public class MyShiroRealm extends AuthorizingRealm {

    @Autowired
    private UserService userService;

    //处理授权
    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principals) {
        SimpleAuthorizationInfo azi = new SimpleAuthorizationInfo();
        Subject subject = SecurityUtils.getSubject();
        //获取的是user对象
        User user = (User)subject.getPrincipal();
        //获取了用户的角色编号
        String roleCode = user.getRole().getRoleCode();
        System.out.println("角色是"+roleCode);
        //添加角色
        azi.addRole(roleCode);
        //返回对象
        return azi;
    }

    //处理认证
    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken token) throws AuthenticationException {
        //获取用户名
        String userCode = (String) token.getPrincipal();
        //获取密码
        String password = new String((char[]) token.getCredentials());
        //调用service
        User user = userService.shiroUserCode(userCode);
        System.out.println("user = " + user);
        ByteSource bytes = null;
        if (user == null) {
            throw new UnknownAccountException();
        }else {
            //密码不正确可以自动判断句
            String salt = user.getSalt();//获取盐值
            bytes = ByteSource.Util.bytes(salt);
        }
        //密码不正确可以自动判断
        //返回SimpleAuthenticationInfo对象，将user、密码和realm名称绑定到构造方法中
        //第一个参数叫做principal  绑定什么，subject实质内容就是什么
        //绑定user，指代的user对象；绑定的userName，则指代的是用户名
        return new SimpleAuthenticationInfo(user, user.getUserPassword(),bytes,this.getName());
    }
}
