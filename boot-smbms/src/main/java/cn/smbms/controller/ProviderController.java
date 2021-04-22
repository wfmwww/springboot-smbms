package cn.smbms.controller;

import cn.smbms.entity.Provider;
import cn.smbms.entity.User;
import cn.smbms.service.bill.BIllService;
import cn.smbms.service.provider.ProviderService;
import cn.smbms.util.Constants;
import cn.smbms.util.PageSupport;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;
import java.util.Date;
import java.util.List;

@Controller
@RequestMapping("/sys/pro")
public class ProviderController {
    @Resource
    private ProviderService providerService;
    @Resource
    private BIllService billService;
    private static Logger logger = Logger.getLogger(ProviderController.class);

    //根据供应商编码和供应商名称模糊分页查询
    @RequestMapping("/proList.html")
    public String getProList(Model model,
                             @RequestParam(value = "queryProCode", required = false) String queryProCode,
                             @RequestParam(value = "queryProName", required = false) String queryProName,
                             @RequestParam(value = "pageIndex", required = false) String pageIndex) {
      //  logger.info("getProList---->queryProCode--" + queryProCode);
      //  logger.info("getProList---->queryProName--" + queryProName);
      // logger.info("getProList---->pageIndex--" + pageIndex);

        List<Provider> providerList = null;
        //设置页面容量
        int pageSize = Constants.pageSize;
        //当前页码
        int currenPageNo = 1;
        if (queryProCode == null || queryProCode.equals(""))
            queryProCode = "";
        if (queryProName == null || queryProName.equals(""))
            queryProName = "";
        if (pageIndex != null) {
            try {
                currenPageNo = Integer.parseInt(pageIndex);
            } catch (NumberFormatException e) {
                return "syserror";
            }
        }
        //总数量
        int totalCount = providerService.getProCount(queryProCode, queryProName);
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
        providerList = providerService.getProList(queryProCode, queryProName, currenPageNo, pageSize);

        model.addAttribute("providerList", providerList);
        model.addAttribute("queryProCode", queryProCode);
        model.addAttribute("queryProName", queryProName);
        model.addAttribute("totalPageCount", totalPageCount);
        model.addAttribute("totalCount", totalCount);
        model.addAttribute("currentPageNo", currenPageNo);
        return "providerlist";
    }

    //增加供应商
    @RequestMapping(value = "/addPro.html", method = RequestMethod.GET)
    public String addPro(@ModelAttribute("pro") Provider pro) {
        return "provideradd";
    }

    @RequestMapping(value = "/addProSave.html", method = RequestMethod.POST)
    public String addProSave(Provider pro, HttpSession session) {
        pro.setCreationDate(new Date());
        pro.setCreatedBy(((User) (session.getAttribute(Constants.USER_SESSION))).getId());
        int i = providerService.addPro(pro);
        if (i > 0)
            return "redirect:/sys/pro/proList.html";
        else
            return "provideradd";
    }

    //修改供应商
    @RequestMapping("/upPro.html")
    public String upPro(Model model, @RequestParam("proid") String proid) {
        Provider pro = providerService.getProById(proid);
        model.addAttribute(pro);
        return "providermodify";
    }

    @RequestMapping(value = "/upProSave.html", method = RequestMethod.POST)
    public String upProSave(Provider pro, HttpSession session) {
        pro.setModifyDate(new Date());
        pro.setModifyBy(((User) (session.getAttribute(Constants.USER_SESSION))).getId());
        int i = providerService.upPro(pro);
        if(i>0)
            return "redirect:/sys/pro/proList.html";
        else
            return "providermodify";

    }

    //展示
    @ResponseBody
    @RequestMapping("/viewPro.html")
    public Provider viewPro(@RequestParam("proid")String proid){
        Provider pro= new Provider();
        try {
            pro = providerService.getProById(proid);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return pro;
    }

    //删除
    @ResponseBody
    @RequestMapping("/delPro.html")
    public String delPro(@RequestParam("proid") String proid){
        int res = billService.getListByProId(proid);
        if (res > 0) {
            return "has";
        } else {
            int i = providerService.delPro(proid);
            if (i > 0)
                return "true";
            else
                return "false";
        }

    }


    //供应商编码是否存在
    @ResponseBody
    @RequestMapping("/proCodeExist.html")
    public int proCodeExist(String proCode){
        List<Provider> all = providerService.getAll();
        for (Provider provider : all) {
            if (provider.getProCode().equals(proCode)) {
                return 1;
            }
        }
        return 0;
    }
}
