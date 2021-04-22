package cn.smbms.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author: it-boy-www
 * @date: 2021/4/20
 * @description:
 */
@RestController
@RequestMapping("/test")
public class TestController {
    @RequestMapping("/test0")
    public String test0() {
        return "string";
    }
}
