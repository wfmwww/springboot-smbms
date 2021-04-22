package cn.smbms.controller;

import cn.smbms.entity.User;
import cn.smbms.service.user.UserService;
import com.github.pagehelper.PageInfo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author: it-boy-www
 * @date: 2021/4/21
 * @description: 测试swagger
 */
@RestController
@RequestMapping("/swag")
@Api("测试swagger的控制器")
public class SwaggerController {

    @Resource
    private UserService userService;

    @GetMapping("/getUserById0.html")
    @ApiOperation(notes = "获取单个用户信息0", value = "getUserById",
            protocols = "http", response = String.class)
    public String getUser0(@ApiParam(value = "主键", name = "id", defaultValue = "1"
            , required = true) @RequestParam String id) {
        return userService.getUserById(id).toString();
    }

    @GetMapping("/getUserById1.html")
    @ApiOperation(notes = "获取单个用户信息1", value = "getUserById1",
            protocols = "http", response = String.class, httpMethod = "GET",
            produces = "text/html")
    public String getUser1(String id) {
        return userService.getUserById(id).toString();
    }


    @GetMapping("/getPagehelper")
    @ApiOperation(notes = "Pagehelper分页工具类的测试",value = "getPagehelper")
    public PageInfo<User> getPagehelper(Integer pageIndex,Integer pageSize) {
        PageInfo<User> userListByPagehelper = userService.getUserListByPagehelper(pageIndex, pageSize);
        return userListByPagehelper;
    }

    @GetMapping("/testRedis")
    @ApiOperation(notes = "Redis测试",value = "testRedis")
    public List<User> testRedis() {
       return userService.testRedis();
    }


}
