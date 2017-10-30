package com.yu.test.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * Created by koreyoshi on 2017/8/1.
 */
@Controller
@RequestMapping("test")
public class TestController {
    @RequestMapping("index")
    @ResponseBody
    public String test(){
        return "index";
    }

}