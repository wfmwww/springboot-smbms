package cn.smbms.service.provider;

import cn.smbms.entity.Provider;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Service;

import java.util.List;
public interface ProviderService {

    //根据供应商编码和供应商名称模糊分页查询
    List<Provider> getProList(String proCode, String proName,
                              Integer pageIndex, Integer pageSize);

    //根据用户名和角色查询用户总数
    int getProCount(String proCode, String proName);

    //增加供应商
    int addPro(Provider pro);

    //修改供应商
    int upPro(Provider pro);

    //删除供应商
    int delPro(String id);

    //根基id查询供应商
    Provider getProById(String id);

    //查询所有
    List<Provider> getAll();
}
