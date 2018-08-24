package com.yunlian.loganalysis.convertor;

import com.yunlian.loganalysis.dto.StatOriginLogDataDto;
import com.yunlian.loganalysis.po.*;
import com.yunlian.loganalysis.util.GlobalIdGenerator;
import org.apache.commons.lang.StringUtils;
import org.apache.storm.shade.org.apache.commons.collections.CollectionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

/**
 * @author qiang.wen
 * @date 2018/8/9 16:21
 *
 * 日志相关数据转换
 */
public class LogDataConvertor {

    private static final Logger log = LoggerFactory.getLogger(LogDataConvertor.class);

    private static final String splitReg = "]";//分隔符

    private static final int array_length = 15;

    private static final String http_protol = "HTTP/1.1";

//    private static final String api_url_format = "%s%s%s";

    private static final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MMM/yyyy:HH:mm:ss Z", Locale.US);


    /**
     * 把data转换为StatOriginLogDataDto
     * @param data
     * @return
     */
    public static StatOriginLogDataDto convertToStatOriginLogDataDto(String data) {
        if(StringUtils.isBlank(data)){
            return null;
        }
        /**
         * 格式：
         * [$remote_addr][$remote_user][$time_local][$http_host][$request][$request_length][$http_referer][$upstream_addr]
         * [$status][$request_time][$upstream_response_time][$body_bytes_sent][$http_token][$http_appCode][$http_user_agent]
         */
        String[] dataArr = data.split(splitReg);
        if(dataArr.length != 15){
            log.error("日志数据格式有误，分组后长度不等于{}",array_length);
            return null;
        }
        try{
            String status = dataArr[8].substring(1);
            //404不计入到接口统计中
            if(is404(status)){
                return null;
            }
            StatOriginLogDataDto dataDto = new StatOriginLogDataDto();
            dataDto.setRemoteAddr(dataArr[0].substring(1));// $remote_addr
            dataDto.setRemoteUser(dataArr[1].substring(1));// $remote_user
            dataDto.setTimeLocal(dataArr[2].substring(1));// $time_local
            dataDto.setHttpHost(dataArr[3].substring(1));// $http_host
            dataDto.setRequest(dataArr[4].substring(1));// $request
            dataDto.setRequestLength(dataArr[5].substring(1));// $request_length
            dataDto.setHttpReferer(dataArr[6].substring(1));// $http_referer
            dataDto.setUpstreamAddr(dataArr[7].substring(1));// $upstream_addr
            dataDto.setStatus(dataArr[8].substring(1));// $status
            dataDto.setRequestTime(dataArr[9].substring(1));// $request_time
            dataDto.setUpstreamResponseTime(dataArr[10].substring(1));// $upstream_response_time
            dataDto.setBodyBytesSent(dataArr[11].substring(1));// $body_bytes_sent
            String httpToken = dataArr[12].substring(1);// $http_token
            if(!"null".equals(httpToken) && !"-".equals(httpToken)){
                dataDto.setHttpToken(httpToken);
            }
            String httpAppCode = dataArr[13].substring(1);// $http_appCode
            if(!"null".equals(httpAppCode) && !"-".equals(httpAppCode)){
                dataDto.setHttpAppCode(httpAppCode);
            }
            dataDto.setHttpUserAgent(dataArr[14].substring(1));// $http_user_agent
            return dataDto;
        }catch (Exception e){
            log.error("转换日志数据错误，e:{}",e);
        }
        return null;
    }


    /**
     * 把StatOriginLogDataDto集合转换为StatOriginLogDataPo集合
     * @param logDataDtos
     * @return
     */
    public static List<StatOriginLogDataPo> convertToStatOriginLogDataPos(List<StatOriginLogDataDto> logDataDtos) {
        List<StatOriginLogDataPo> logDataPos = new ArrayList<>();
        GlobalIdGenerator idGenerator = GlobalIdGenerator.getInstance();
        logDataDtos.forEach(dataDto -> {
            StatOriginLogDataPo po = new StatOriginLogDataPo();
            //这里生成了uid
            po.setUid(String.valueOf(idGenerator.nextId()));
            Integer bodyByteSent = StringUtils.isBlank(dataDto.getBodyBytesSent())?0:Integer.valueOf(dataDto.getBodyBytesSent());
            po.setBodyBytesSent(bodyByteSent);
            po.setHttpAppCode(dataDto.getHttpAppCode());
            po.setHttpHost(dataDto.getHttpHost());
            po.setHttpReferer(dataDto.getHttpReferer());
            po.setHttpToken(dataDto.getHttpToken());
            po.setHttpUserAgent(dataDto.getHttpUserAgent());
            po.setRemoteAddr(dataDto.getRemoteAddr());
            po.setRemoteUser(dataDto.getRemoteUser());
            po.setRequest(dataDto.getRequest());
            Integer requestLength = StringUtils.isBlank(dataDto.getRequestLength())?0:Integer.valueOf(dataDto.getRequestLength());
            po.setRequestLength(requestLength);
            po.setRequestTime(dataDto.getRequestTime());
            Integer status = StringUtils.isBlank(dataDto.getStatus())?null:Integer.valueOf(dataDto.getStatus());
            po.setStatus(status);
            po.setTimeLocal(dataDto.getTimeLocal());
            po.setUpstreamAddr(dataDto.getUpstreamAddr());
            po.setUpstreamResponseTime(dataDto.getUpstreamResponseTime());
            logDataPos.add(po);
        });
        return logDataPos;
    }


    /**
     * 把StatOriginLogDataDto集合转换为StatCallApiPo集合
     * @param logDataDtos
     * @param apiPathPos
     * @return
     */
    public static List<StatCallApiPo> convertToStatCallApiPos(List<StatOriginLogDataDto> logDataDtos, List<StatSysApiPathPo> apiPathPos) {
        //不生成uid
        List<StatCallApiPo> statCallApiPos = new ArrayList<>();
        logDataDtos.forEach(dto -> {
            StatCallApiPo po = new StatCallApiPo();
            //url解析和转换
            String apiUrl = resolveUrl(dto.getRequest());
            apiUrl = urlConvert(apiUrl,apiPathPos);
            po.setApiUrl(apiUrl);
            po.setTotalCallnum(1);
            if(isSuccessed(dto.getStatus())){
                po.setSuccessCallnum(1);
            }else {
                po.setFailureCallnum(1);
            }
            long reqTime = resolveRequestTime(dto.getRequestTime());
            po.setMaxResponseTime(reqTime);
            po.setMinResponseTime(reqTime);
            statCallApiPos.add(po);
        });
        return statCallApiPos;
    }


    /**
     * 把StatOriginLogDataDto集合转换为StatCallDailyPo集合
     * @param logDataDtos
     * @return
     */
    public static List<StatCallDailyPo> convertToStatCallDailyPos(List<StatOriginLogDataDto> logDataDtos) {
        //不生成uid
        List<StatCallDailyPo> statCallDailyPos = new ArrayList<>();
        logDataDtos.forEach(dto -> {
            StatCallDailyPo po = new StatCallDailyPo();
            po.setStatDate(resolveStatDate(dto.getTimeLocal()));
            po.setTotalCallnum(1);
            if(isSuccessed(dto.getStatus())){
                po.setSuccessCallnum(1);
            }else {
                po.setFailureCallnum(1);
            }
            statCallDailyPos.add(po);
        });
        return statCallDailyPos;
    }

    /**
     * 把StatOriginLogDataDto集合转换为StatCallDailyApiPo集合
     * @param logDataDtos
     * @param apiPathPos
     * @return
     */
    public static List<StatCallDailyApiPo> convertToStatCallDailyApiPos(List<StatOriginLogDataDto> logDataDtos, List<StatSysApiPathPo> apiPathPos) {
        //不生成uid
        List<StatCallDailyApiPo> statCallDailyApiPos = new ArrayList<>();
        logDataDtos.forEach(dto -> {
            StatCallDailyApiPo po = new StatCallDailyApiPo();
            //url解析和转换
            String apiUrl = resolveUrl(dto.getRequest());
            apiUrl = urlConvert(apiUrl,apiPathPos);
            po.setApiUrl(apiUrl);
            po.setStatDate(resolveStatDate(dto.getTimeLocal()));
            po.setTotalCallnum(1);
            if(isSuccessed(dto.getStatus())){
                po.setSuccessCallnum(1);
            }else {
                po.setFailureCallnum(1);
            }
            long reqTime = resolveRequestTime(dto.getRequestTime());
            po.setMaxResponseTime(reqTime);
            po.setMinResponseTime(reqTime);
            statCallDailyApiPos.add(po);
        });
        return statCallDailyApiPos;
    }

    /**
     * 把StatOriginLogDataDto集合转换为StatCallPartnerDailyApiPo集合
     * @param logDataDtos
     * @param apiPathPos
     * @return
     */
    public static List<StatCallPartnerDailyApiPo> converttoStatCallPartnerDailyApiPos(List<StatOriginLogDataDto> logDataDtos, List<StatSysApiPathPo> apiPathPos) {
        //不生成uid
        List<StatCallPartnerDailyApiPo> statCallPartnerDailyApiPos = new ArrayList<>();
        logDataDtos.forEach(dto -> {
            //appCode为空不添加进入按调用方统计
            if(StringUtils.isNotBlank(dto.getHttpAppCode())){
                StatCallPartnerDailyApiPo po = new StatCallPartnerDailyApiPo();
                po.setAppCode(dto.getHttpAppCode());
                //url解析和转换
                String apiUrl = resolveUrl(dto.getRequest());
                apiUrl = urlConvert(apiUrl,apiPathPos);
                po.setApiUrl(apiUrl);
                po.setStatDate(resolveStatDate(dto.getTimeLocal()));
                po.setTotalCallnum(1);
                if(isSuccessed(dto.getStatus())){
                    po.setSuccessCallnum(1);
                }else {
                    po.setFailureCallnum(1);
                }
                long reqTime = resolveRequestTime(dto.getRequestTime());
                po.setMaxResponseTime(reqTime);
                po.setMinResponseTime(reqTime);
                statCallPartnerDailyApiPos.add(po);
            }
        });
        return statCallPartnerDailyApiPos;
    }

    /**
     * 通过request和httpHost解析url
     * @param request
     * @return
     */
    private static String resolveUrl(String request){
        try{
            //POST /logout/sso/user/v1/userSession HTTP/1.1
            String[] reqArr = request.split(" ");
//            String apiUrl = String.format(api_url_format,"http://",httpHost,reqArr[1]);
            String apiUrl = reqArr[1];
            if(StringUtils.isNotBlank(apiUrl)){
                apiUrl = apiUrl.split("\\?")[0];
            }
            return apiUrl;
        }catch (Exception e){
            log.error("解析url失败，e:{}",e);
//            return String.format(api_url_format,"http://",httpHost,request);
            return request;
        }
    }

    /**
     * 是否成功
     * @param status
     * @return
     */
    private static boolean isSuccessed(String status){
        if(StringUtils.isBlank(status)){
            return false;
        }
        return "200".equals(status);
    }

    /**
     * 是否为404
     * @param status
     * @return
     */
    private static boolean is404(String status){
        if(StringUtils.isBlank(status)){
            return true;
        }
        return "404".equals(status);
    }

    /**
     * 解析日期
     * @param timeLocal
     * @return
     */
    private static LocalDate resolveStatDate(String timeLocal){
        //08/Aug/2018:19:47:01 +0800
        try{
            LocalDateTime dateTime = LocalDateTime.parse(timeLocal,formatter);
            return dateTime.toLocalDate();
        }catch (Exception e){
            log.error("解析日期出错，e:{}",e);
        }
        return LocalDate.now();
    }

    /**
     * 解析请求时间
     * @param requestTime
     * @return
     */
    private static long resolveRequestTime(String requestTime){
        // 0.589s 需要转换为ms
        try{
            long reqTime = (long) (Double.valueOf(requestTime) * 1000);
            return reqTime;
        }catch (Exception e){
            log.error("解析请求时间错误，e:{}",e);
        }
        return 0;
    }

    /**
     * url转换
     * @param apiUrl
     * @param apiPathPos
     * @return
     */
    private static String urlConvert(String apiUrl, List<StatSysApiPathPo> apiPathPos) {
        if(CollectionUtils.isEmpty(apiPathPos)){
            return apiUrl;
        }
        for(StatSysApiPathPo pathPo : apiPathPos){
            String path = pathPo.getApiPath();
            if(matchPath(apiUrl,path)){
                return path;
            }
        }
        return apiUrl;
    }

    /**
     * 是否匹配
     * @param url
     * @param path
     * @return
     */
    private static boolean matchPath(String url,String path){
        String[] pathArr = path.split("/");
        String[] urlArr = url.split("/");
        if(pathArr.length != urlArr.length){
            return false;
        }
        for(int i = 0; i < pathArr.length ; i++){
            String pathPart = pathArr[i];
            String urlPart = urlArr[i];
            if(pathPart.startsWith("{") && pathPart.endsWith("}") && StringUtils.isEmpty(urlPart)){
                return false;
            }else if(!pathPart.equals(urlPart)){
                return false;
            }
        }
        return true;
    }
}
