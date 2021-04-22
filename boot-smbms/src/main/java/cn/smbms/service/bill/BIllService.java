package cn.smbms.service.bill;

import cn.smbms.entity.Bill;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface BIllService {

    //根据商品名，供应商，和是否付款分页查询
    List<Bill> getBillList(String productName, String providerId,
                           String isPayment, Integer pageIndex,
                           Integer pageSize);

    //根据商品名，供应商，和是否付款查询总数
    int getBillCount(String productName, String providerId, String isPayment);

    //查看订单
    Bill getBillById(String id);

    //增加订单
    int addBill(Bill bill);

    //修改订单
    int upBill(Bill bill);

    //删除订单
    int delBill(String billid);

    //根据编码查询是否存在
    int isBillCode(String billCode);

    //根据供应商id查询和是否付款查询
    int getListByProId(String providerId);
}
