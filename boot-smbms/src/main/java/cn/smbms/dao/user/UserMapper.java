package cn.smbms.dao.user;

import cn.smbms.entity.Provider;
import cn.smbms.entity.User;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserMapper {
    //根据用户userCode和userpassword进行登录
    User getLogin(@Param("userCode") String userCode, @Param("userPassword") String userPassword);
    //根据用户名和校色分页展示用户列表
    List<User> getUserList(@Param("userName") String userName,
                           @Param("userRole") Integer userRole,
                           @Param("pageIndex") Integer pageIndex,
                           @Param("pageSize") Integer pageSize);
    //根据用户名和角色查询用户总数
    int getUserCount(@Param("userName") String userName,
                     @Param("userRole") Integer userRole);
    //增加用户
    int addUser(User user);

    //查询用户编码是否存在
    int userCodeExist(@Param("userCode") String userCode);

    //根据用户id查询用户
    User getUserById(@Param("id") String id);

    //修改用户信息
    int upUserById(User user);

    //根据id删除用户
    int delUserById(@Param("id") String id);

    //修改密码
    int upPw(@Param("newPw") String newPw, @Param("id") Integer id);

    //shiro查询用户是存在
    User shiroUserCode(@Param("userCode") String userCode);

    //查询所有用户
    List<User> getAllUser();

}
