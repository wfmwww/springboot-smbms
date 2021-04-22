package cn.smbms.service.user;

import cn.smbms.entity.User;
import com.github.pagehelper.PageInfo;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface UserService {
    //登录
    User getLogin(String userCode, String userPassword);

    //用户列表展示
    List<User> getUserList(String userName, Integer userRole, Integer pageIndex, Integer pageSize);

    //根据用户名和角色查询用户总数
    int getUserCount(String userName, Integer userRole);

    //增加用户
    int addUser(User user);

    //查询用户编码是否存在
    int userCodeExist(String userCode);

    //根据用户id查询用户
    User getUserById(String id);

    //修改用户信息
    int upUserById(User user);

    //根据id删除用户
    int delUserById(String id);

    //修改密码
    int upPw(String newpw, Integer id);

    //shiro查询用户是存在
    User shiroUserCode(String userCode);

    //测试pagehelper
    PageInfo<User> getUserListByPagehelper(Integer pageIndex, Integer pageSize);

    //测试Redis
    List<User> testRedis();
}