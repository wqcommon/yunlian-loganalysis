package com.yunlian.apimonitor.controller;

import com.yunlian.apimonitor.response.ApiResponse;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;

/**
 * @author qiang.wen
 * @date 2018/7/31 10:10
 *
 * API 统计的controller
 */
@RestController("/api/stat")
public class ApiStatController {


    /**
     * 查询每天api调用次数
     * @param startDate
     * @param endDate
     * @return
     */
    public ApiResponse queryTotalByPerDay(@RequestParam(value = "startDate",required = false) Date startDate,
                                          @RequestParam(value = "endDate",required = false) Date endDate){


        return null;
    }

}
