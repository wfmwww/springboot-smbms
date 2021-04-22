package cn.smbms.controller;

import cn.smbms.entity.Bill;
import cn.smbms.entity.Provider;
import cn.smbms.entity.User;
import cn.smbms.service.bill.BIllService;
import cn.smbms.service.provider.ProviderService;
import cn.smbms.util.Constants;
import cn.smbms.util.PageSupport;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;
import java.util.Date;
import java.util.List;

@Controller
@RequestMapping("/sys/bill")
public class BillController {
    private static final Logger logger = Logger.getLogger(BillController.class);
    @Resource
    private ProviderService providerService;
    @Resource
    private BIllService billService;


    //根据商品名，供应商，和是否付款分页查询
    @RequestMapping(value = "/billList.html")
    public String getBill(Model model,
                          @RequestParam(value = "queryProductName", required = false) String queryProductName,
                          @RequestParam(value = "queryProviderId", required = false) String queryProviderId,
                          @RequestParam(value = "queryIsPayment", required = false) String queryIsPayment,
                          @RequestParam(value = "pageIndex", required = false) String pageIndex) {
        List<Bill> billList = null;
        //设置页面容量
        int pageSize = Constants.pageSize;
        //当前页码
        int currenPageNo = 1;
        if (pageIndex != null) {
            try {
                currenPageNo = Integer.parseInt(pageIndex);
            } catch (NumberFormatException e) {
                return "syserror";
            }
        }
        //总数量
        int totalCount = billService.getBillCount(queryProductName, queryProviderId, queryIsPayment);
        //总页数,调用工具类
        PageSupport pages = new PageSupport();
        pages.setCurrentPageNo(currenPageNo);//传入当前页码
        pages.setPageSize(pageSize);//传入页面容量
        pages.setTotalCount(totalCount);//传入总数量
        int totalPageCount = pages.getTotalPageCount();//得到总页数

        //控制首尾
        if (currenPageNo < 1)
            currenPageNo = 1;
        if (currenPageNo > totalPageCount)
            currenPageNo = totalPageCount;
        billList = billService.getBillList(queryProductName, queryProviderId, queryIsPayment, currenPageNo, pageSize);
        model.addAttribute("billList", billList);
        //动态显示角色下拉列表
        List<Provider> providerList = null;
        providerList = providerService.getAll();
        model.addAttribute("providerList", providerList);
        model.addAttribute("queryProductName", queryProductName);
        model.addAttribute("queryProviderId", queryProviderId);
        model.addAttribute("queryIsPayment", queryIsPayment);
        model.addAttribute("totalCount", totalCount);
        model.addAttribute("currentPageNo", currenPageNo);
        model.addAttribute("totalPageCount", totalPageCount);
        return "billlist";
    }

    //查询
    @RequestMapping("/billView.html")
    public String billView(String billid, Model model) {
        Bill bill = billService.getBillById(billid);
        model.addAttribute(bill);
        return "billview";
    }

    //供应商下拉列表
    @ResponseBody
    @RequestMapping("/ajaxPro.html")
    public Object ajaxPro() {
        List<Provider> providerList = providerService.getAll();
        return providerList;
    }

    //增加
    @RequestMapping("/addBill.html")
    public String add(@ModelAttribute("bill") Bill bill) {
        return "billadd";
    }

    @RequestMapping("/addBillSave.html")
    public String addSave(Bill bill, HttpSession session) {
        bill.setCreationDate(new Date());
        bill.setCreatedBy(((User) (session.getAttribute(Constants.USER_SESSION))).getId());
        int i = billService.addBill(bill);
        if (i > 0) {
            return "redirect:/sys/bill/billList.html";
        } else
            return "billadd";
    }

    //ajax验证订单编码是否存在
    @ResponseBody
    @RequestMapping("/ajaxBillCode.html")
    public Object ajaxBillCode(@RequestParam("billCode") String billCode) {
        int i = billService.isBillCode(billCode);
        if (i > 0)
            return "true";
        else
            return "false";
    }

    //修改订单
    @RequestMapping("/upBill.html")
    public String upBill(Model model, String billid) {
        Bill bill = billService.getBillById(billid);
        model.addAttribute(bill);
        return "billmodify";
    }

    @RequestMapping("/upBillSave.html")
    public String upBillSave(Bill bill,HttpSession session){
        bill.setModifyDate(new Date());
        bill.setModifyBy(((User)(session.getAttribute(Constants.USER_SESSION))).getId());
        int i = billService.upBill(bill);
        if(i>0)
            return "redirect:/sys/bill/billList.html";
        else
            return "billmodify";
    }


    //删除订单
    @ResponseBody
    @RequestMapping("/delBill.html")
    public Object del(String billid){
        System.out.println(billid);
        System.out.println(!StringUtils.isEmpty(billid));
        if(!StringUtils.isEmpty(billid)){
            int del = billService.delBill(billid);
            System.out.println(del);
            if (del > 0) {
                return "true";
            }else{
                return "false";
            }

        }

        return "notexist";
    }
}
