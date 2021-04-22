package cn.smbms.service.role;

import cn.smbms.dao.role.RoleMapper;
import cn.smbms.entity.Role;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
@Service("roleService")
public class RoleServiceImpl implements RoleService {
    @Resource
    private RoleMapper roleMapper;
    //展示角色
    @Override
    public List<Role> getRoleList() {
        return roleMapper.getRoleList();
    }
}
