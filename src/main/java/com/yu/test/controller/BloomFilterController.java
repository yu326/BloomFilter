package com.yu.test.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.sun.xml.internal.fastinfoset.stax.events.Util;
import com.yu.test.job.FilterAdapter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * Created by korey on 2017/10/30.
 */
@RestController
@RequestMapping("BloomFilter-Api")
public class BloomFilterController {

    private static final  int EXCEPTION_ERROR = 1;
    private static final  int PARAM_ERROR = 2;

    @Autowired
    private FilterAdapter filterAdapter;

    @RequestMapping(produces = "text/xml;charset=utf8", value = "check", method = {RequestMethod.POST})
    @ResponseBody
    public String test(@RequestBody String requestDataStr) {
        // TODO: 2017/10/31 接口安全验证
        JSONObject responseData = new JSONObject();

        try {
            JSONObject doc = JSON.parseObject(requestDataStr);
            if(doc.containsKey("text") && !Util.isEmptyString(doc.getString("text"))){
                String s = doc.getString("text");
                boolean res = filterAdapter.checkInRedis(s);
                System.out.println(res);
                if(res){
                    responseData.put("result",true);
                }else{
                    responseData.put("result",false);
                }
            }else{
                responseData.put("result",false);
                responseData.put("errorCode",PARAM_ERROR);
                responseData.put("errorMsg","param error");
            }
        } catch (Exception e) {
            responseData.put("result",false);
            responseData.put("errorCode",EXCEPTION_ERROR);
            responseData.put("errorMsg","exception");
            e.printStackTrace();
        } finally {
            return responseData.toString();
        }
    }

}
