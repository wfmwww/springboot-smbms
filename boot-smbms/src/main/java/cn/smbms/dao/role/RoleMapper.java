package cn.smbms.dao.role;

import cn.smbms.entity.Role;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RoleMapper {
    //角色列表
    List<Role> getRoleList();
}
