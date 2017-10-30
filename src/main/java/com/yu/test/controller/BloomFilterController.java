package com.yu.test.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by korey on 2017/10/30.
 */
@RestController
@RequestMapping("BloomFilter-Api")
public class BloomFilterController {

    @RequestMapping("check")
    @ResponseBody
    public String test() {
        return "success";
    }
}
