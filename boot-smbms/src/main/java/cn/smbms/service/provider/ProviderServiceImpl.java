package cn.smbms.service.provider;

import cn.smbms.dao.provider.ProviderMapper;
import cn.smbms.entity.Provider;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
@Service("providerService")
public class ProviderServiceImpl implements ProviderService {
    @Resource
    private ProviderMapper providerMapper;

    //根据供应商编码和供应商名称模糊分页查询
    @Override
    public List<Provider> getProList(String proCode, String proName, Integer pageIndex, Integer pageSize) {
        return providerMapper.getProList(proCode,proName,(pageIndex-1)*pageSize,pageSize);
    }
    //条件查询总数
    @Override
    public int getProCount(String proCode, String proName) {
        return providerMapper.getProCount(proCode,proName);
    }

    //增加
    @Override
    public int addPro(Provider pro) {
        return providerMapper.addPro(pro);
    }

    //修改
    @Override
    public int upPro(Provider pro) {
        return providerMapper.upPro(pro);
    }

    //删除
    @Override
    public int delPro(String id) {
        return providerMapper.delPro(id);
    }

    //根基id查
    @Override
    public Provider getProById(String id) {
        return providerMapper.getProById(id);
    }

    //查询所有
    @Override
    public List<Provider> getAll() {
        return providerMapper.getAll();
    }
}
