package com.yunlian.loganalysis.convertor;

import com.yunlian.loganalysis.dto.StatOriginLogDataDto;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Arrays;

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
            if(!"null".equals(httpToken)){
                dataDto.setHttpToken(httpToken);
            }
            String httpAppCode = dataArr[13].substring(1);// $http_appCode
            if(!"null".equals(httpAppCode)){
                dataDto.setHttpAppCode(httpAppCode);
            }
            dataDto.setHttpUserAgent(dataArr[14].substring(1));// $http_user_agent
            return dataDto;
        }catch (Exception e){
            log.error("转换日志数据错误，e:{}",e);
        }
        return null;
    }

    public static void main(String[] args) {

        String str = "[aaaaa][bbbbb][ccccc][dddddd][vvvvvv][xxxxxx]";
        System.out.println(Arrays.asList(str.split(splitReg)));
    }
}
