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

import javax.annotation.Resource;

public class MyRealm extends AuthorizingRealm {
    @Resource
    private UserService userService;
    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principalCollection) {
        SimpleAuthorizationInfo azi = new SimpleAuthorizationInfo();
        Subject subject = SecurityUtils.getSubject();
        //获取的是user对象
        User principal = (User)subject.getPrincipal();
        //获取了用户的角色编号
        String roleCode = principal.getRole().getRoleName();
        //添加角色
        azi.addRole(roleCode);
        //返回对象
        return azi;
    }

    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken token) throws AuthenticationException {
        String userCode = (String)token.getPrincipal();//获取用户名
        String password = new String((char[])token.getCredentials());//获取密码
        ByteSource bytes = null;
        //调用service
        User user = userService.shiroUserCode(userCode);
        if(user == null){//账户不存在
            throw new UnknownAccountException();
        }/*else{//密码不正确可以自动判断句
            String salt = user.getSalt();//获取盐值
            bytes = ByteSource.Util.bytes(salt);

        }*/
        //返回SimpleAuthenticationInfo对象，将user、密码和realm名称绑定到构造方法中
        //第一个参数叫做principal  绑定什么，subject实质内容就是什么
        //绑定user，指代的user对象；绑定的userName，则指代的是用户名
        return new SimpleAuthenticationInfo(user,user.getUserPassword()/*,bytes*/,this.getName());
    }
}
