package cn.smbms.service.bill;

import cn.smbms.dao.bill.BillMapper;
import cn.smbms.entity.Bill;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
@Service("billService")
public class BIllServiceImpl implements BIllService {
    @Resource
    private BillMapper billMapper;

    //根据商品名，供应商，和是否付款分页查询
    @Override
    public List<Bill> getBillList(String productName, String providerId,
                                  String isPayment, Integer pageIndex, Integer pageSize) {
        return billMapper.getBillList(productName,providerId,isPayment,
                (pageIndex-1)*pageSize,pageSize);
    }

    //条件查询总数
    @Override
    public int getBillCount(String productName, String providerId, String isPayment) {
        return billMapper.getBillCount(productName,providerId,isPayment);
    }

    //根据id查询
    @Override
    public Bill getBillById(String id) {
        return billMapper.getBillById(id);
    }

    //增
    @Override
    public int addBill(Bill bill) {
        return billMapper.addBill(bill);
    }

    //改
    @Override
    public int upBill(Bill bill) {
        return billMapper.upBill(bill);
    }

    //删
    @Override
    public int delBill(String billid) {
        return billMapper.delBill(billid);
    }

    //查询编码是否存在
    @Override
    public int isBillCode(String billCode) {
        return billMapper.isBillCode(billCode);
    }

    @Override
    public int getListByProId(String providerId) {
        return billMapper.getListByProId(providerId);
    }
}
