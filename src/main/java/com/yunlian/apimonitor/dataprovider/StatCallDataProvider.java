package com.yunlian.apimonitor.dataprovider;

import com.yunlian.apimonitor.enums.AppCode;
import com.yunlian.apimonitor.response.vo.StatCallApiVo;
import com.yunlian.apimonitor.response.vo.StatCallDailyApiVo;
import com.yunlian.apimonitor.response.vo.StatCallDailyVo;
import com.yunlian.apimonitor.response.vo.StatCallPartnerDailyApiVo;
import org.springframework.util.StringUtils;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.*;

/**
 * @author qiang.wen
 * @date 2018/7/31 15:27
 * 统计调用数据提供者，供mock使用
 */
public class StatCallDataProvider {

    private static final Random random = new Random();

    private static final LocalDate currStatDate = LocalDate.of(2018,7,20);

    private static final DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");

    /**
     * 组装“每日接口调用统计”的数据
     * @return
     */
    public static List<StatCallDailyVo> assemStatCallDailyVos(){
        List<StatCallDailyVo> voList = new ArrayList<>(7);
        int i = -1;
        while (++i < 7){
            voList.add(genStatCallDailyVo(i));
        }
        return voList;
    }

    private static StatCallDailyVo genStatCallDailyVo(int dateStep){
        StatCallDailyVo vo = new StatCallDailyVo();
        vo.setStatDate(currStatDate.plusDays(dateStep).format(dateFormatter));
        vo.setSuccessCallNum(random.nextInt(100));
        vo.setFailureCallNum(random.nextInt(100));
        vo.setTotalCallNum(vo.getFailureCallNum() + vo.getSuccessCallNum());
        return vo;
    }

    /**
     * 组装“按api维度统计”的数据
     * @return
     */
    public static List<StatCallApiVo> assemStatCallApiVos() {
        List<StatCallApiVo> voList = new ArrayList<>(50);
        int i = -1;
        while (++i < 50){
            voList.add(genStatCallApiVo(i));
        }
        return voList;
    }

    private static StatCallApiVo genStatCallApiVo(int step) {
        StatCallApiVo vo = new StatCallApiVo();
        vo.setApiUrl("http://66yuanlian.com/api" + (step + 1));
        vo.setSuccessCallNum(1000 * random.nextInt(100));
        vo.setFailureCallNum(1000 * random.nextInt(100));
        vo.setTotalCallNum(vo.getFailureCallNum() + vo.getSuccessCallNum());
        return vo;
    }

    /**
     * 组装“按每天每个api的维度统计”的数据
     * @return
     */
    public static List<StatCallDailyApiVo> assemStatCallDailyApiVos() {
        List<StatCallDailyApiVo> voList = new ArrayList<>(50);
        int i = -1;
        while (++i < 50){
            voList.add(genStatCallDailyApiVo(i));
        }
        return voList;

    }

    private static StatCallDailyApiVo genStatCallDailyApiVo(int step) {
        StatCallDailyApiVo vo = new StatCallDailyApiVo();
        vo.setApiUrl("http://66yuanlian.com/api" + (step + 1));
        vo.setSuccessCallNum(1000 * random.nextInt(100));
        vo.setFailureCallNum(1000 * random.nextInt(100));
        vo.setTotalCallNum(vo.getFailureCallNum() + vo.getSuccessCallNum());
        vo.setStatDate(currStatDate.plusDays(step).format(dateFormatter));
        vo.setMinResponseTime(random.nextInt(5000));
        vo.setMaxResponseTime(random.nextInt(5000));
        vo.setAvgResponseTime((vo.getMaxResponseTime() + vo.getMinResponseTime())/2);
        return vo;
    }

    /**
     * 组装“按调用方每天每个api统计”的数据
     * @param appCode
     * @return
     */
    public static Object assemStatCallPartnerDailyApiVos(String appCode) {

        if(StringUtils.isEmpty(appCode)){
            Map<String,List<StatCallPartnerDailyApiVo>> retMap = new HashMap<>();
            for(AppCode code : AppCode.values()){
                retMap.put(code.getAppCode(),genStatCallPartnerDailyApiList(code.getAppCode()));
            }
            return retMap;
        }else{
            return genStatCallPartnerDailyApiList(appCode);
        }

    }

    private static  List<StatCallPartnerDailyApiVo> genStatCallPartnerDailyApiList(String appCode){
        List<StatCallPartnerDailyApiVo> voList = new ArrayList<>(50);
        int i = -1;
        while (++i < 50){
            voList.add(genStatCallPartnerDailyApiVo(i,appCode));
        }
        return voList;
    }

    private static StatCallPartnerDailyApiVo genStatCallPartnerDailyApiVo(int step, String appCode) {
        StatCallPartnerDailyApiVo vo = new StatCallPartnerDailyApiVo();
        vo.setApiUrl("http://66yuanlian.com/api" + (step + 1));
        vo.setSuccessCallNum(1000 * random.nextInt(100));
        vo.setFailureCallNum(1000 * random.nextInt(100));
        vo.setTotalCallNum(vo.getFailureCallNum() + vo.getSuccessCallNum());
        vo.setStatDate(currStatDate.plusDays(step).format(dateFormatter));
        vo.setMinResponseTime(random.nextInt(5000));
        vo.setMaxResponseTime(random.nextInt(5000));
        vo.setAvgResponseTime((vo.getMaxResponseTime() + vo.getMinResponseTime())/2);
        vo.setAppCode(appCode);
        return vo;
    }
}
