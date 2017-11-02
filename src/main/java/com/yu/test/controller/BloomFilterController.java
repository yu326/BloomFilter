package com.yu.test.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.yu.test.config.RedisServiceConfig;
import com.yu.test.job.FilterAdapter;
import com.yu.test.service.impl.BloomFilterServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * Created by korey on 2017/10/30.
 */
@RestController
@RequestMapping("BloomFilter-Api")
public class BloomFilterController {
    //error type
    private static final int EXCEPTION_ERROR = 1;
    private static final int PARAM_ERROR = 2;

    @Autowired
    private BloomFilterServiceImpl bloomFilterService;

    @Autowired
    private FilterAdapter filterAdapter;

    public static final int DATA_LEN_M = 1024 * 1024;

    private RedisServiceConfig redisServiceConfig = RedisServiceConfig.getConfig();

    @RequestMapping(produces = "application/json;charset=utf8", value = "check", method = {RequestMethod.POST})
    @ResponseBody
    public String test(@RequestBody String requestDataStr) {
        // TODO: 2017/10/31 接口安全验证
        JSONObject responseData = new JSONObject();
        try {
            //验证数据大小，不超过1Mb
            int dataByteLen = requestDataStr.getBytes().length;
            if (dataByteLen > DATA_LEN_M) {
                throw new RuntimeException("data len too big,max len is:[" + DATA_LEN_M + "].");
            }

            JSONObject doc = JSON.parseObject(requestDataStr);
            String filterData = bloomFilterService.getFilterParam(doc);
            boolean res = bloomFilterService.filterByData(redisServiceConfig,filterAdapter, filterData);
            if (res) {
                responseData.put("result", true);
            } else {
                responseData.put("result", false);
            }

//            if(doc.containsKey("text") && !Util.isEmptyString(doc.getString("text"))){
//                String s = doc.getString("text");
////                boolean res = filterAdapter.checkInRedis(s);
//
//
//                boolean res = true;
//                System.out.println(res);
//                if(res){
//                    responseData.put("result",true);
//                }else{
//                    responseData.put("result",false);
//                }
//            }else{
//                responseData.put("result",false);
//                responseData.put("errorCode",PARAM_ERROR);
//                responseData.put("errorMsg","param error");
//            }
        } catch (Exception e) {
            responseData.put("result", false);
            responseData.put("errorCode", EXCEPTION_ERROR);
            responseData.put("errorMsg", "exception");
            e.printStackTrace();
        } finally {
            return responseData.toString();
        }
    }

}
