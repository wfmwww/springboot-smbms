package cn.smbms.dao.bill;

import cn.smbms.entity.Bill;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface BillMapper {

    //根据商品名，供应商，和是否付款分页查询
    List<Bill> getBillList(@Param("productName") String productName,
                           @Param("providerId") String providerId,
                           @Param("isPayment") String isPayment,
                           @Param("pageIndex") Integer pageIndex,
                           @Param("pageSize") Integer pageSize);

    //根据商品名，供应商，和是否付款查询总数
    int getBillCount(@Param("productName") String productName,
                     @Param("providerId") String providerId,
                     @Param("isPayment") String isPayment);

    //查看订单
    Bill getBillById(@Param("id") String id);

    //增加订单
    int addBill(Bill bill);

    //修改订单
    int upBill(Bill bill);

    //删除订单
    int delBill(@Param("billid") String billid);

    //根据编码查询是否存在
    int isBillCode(@Param("billCode") String billCode);

    //根据供应商id查询和是否付款查询
    int getListByProId(String providerId);
}
