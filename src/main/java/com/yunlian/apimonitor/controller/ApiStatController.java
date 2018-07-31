package com.yunlian.apimonitor.controller;

import com.yunlian.apimonitor.dataprovider.StatCallDataProvider;
import com.yunlian.apimonitor.response.ApiResponse;
import com.yunlian.apimonitor.response.vo.StatCallApiVo;
import com.yunlian.apimonitor.response.vo.StatCallDailyApiVo;
import com.yunlian.apimonitor.response.vo.StatCallDailyVo;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @author qiang.wen
 * @date 2018/7/31 10:10
 *
 * API 统计的controller
 */
@RestController
@RequestMapping("/api/stat")
public class ApiStatController {


    /**
     * 查询每天api调用次数
     * @param startDate
     * @param endDate
     * @return
     */
    @RequestMapping(value = "/queryStatDaily",method = RequestMethod.GET)
    public ApiResponse queryStatDaily(@RequestParam(value = "startDate",required = false) String startDate,
                                          @RequestParam(value = "endDate",required = false) String endDate){
        List<StatCallDailyVo> list = StatCallDataProvider.assemStatCallDailyVos();
        ApiResponse<List<StatCallDailyVo>> response = ApiResponse.OK(list);
        return response;
    }

    /**
     * 查询api的总调用情况
     * @return
     */
    @RequestMapping(value = "/queryStatApi",method = RequestMethod.GET)
    public ApiResponse queryStatApi(){
        List<StatCallApiVo> list = StatCallDataProvider.assemStatCallApiVos();
        ApiResponse<List<StatCallApiVo>> response = ApiResponse.OK(list);
        return response;
    }


    /**
     * 查询每天每个api的总调用情况
     * @param statDate
     * @param sortedField
     * @param direction
     * @return
     */
    @RequestMapping(value = "/queryStatDailyApi",method = RequestMethod.GET)
    public ApiResponse queryStatDailyApi(@RequestParam(value = "statDate",required = false) String statDate,
                                         @RequestParam(value = "sortedField",required = false) String sortedField,
                                         @RequestParam(value = "sortedDirection",required = false) Integer direction){
        List<StatCallDailyApiVo> list = StatCallDataProvider.assemStatCallDailyApiVos();
        ApiResponse<List<StatCallDailyApiVo>> response = ApiResponse.OK(list);
        return response;
    }


    /**
     * 查询每个调用方每天每个api的调用情况
     * @param statDate
     * @param appCode
     * @param sortedField
     * @param direction
     * @return
     */
    @RequestMapping(value = "/queryStatPartnerDailyApi",method = RequestMethod.GET)
    public ApiResponse queryStatPartnerDailyApi(@RequestParam(value = "statDate",required = false) String statDate,
                                         @RequestParam(value = "appCode",required = false) String appCode,
                                         @RequestParam(value = "sortedField",required = false) String sortedField,
                                         @RequestParam(value = "sortedDirection",required = false) Integer direction){
        Object ret = StatCallDataProvider.assemStatCallPartnerDailyApiVos(appCode);
        ApiResponse<Object> response = ApiResponse.OK(ret);
        return response;
    }
}
