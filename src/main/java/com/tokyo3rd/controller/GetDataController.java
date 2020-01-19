package com.tokyo3rd.controller;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.tokyo3rd.util.RedisCacheUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.view.freemarker.FreeMarkerConfigurer;

import javax.servlet.http.HttpServletRequest;
import java.util.Iterator;
import java.util.Map;

/**
 * @Author: CodeCRH
 * @Date: 2020/1/19
 */
@RestController
@RequestMapping("/api")
public class GetDataController {

    private static Logger log = LoggerFactory.getLogger(GetDataController.class);

    @Value("${system.redishost}")
    private String redishost;

    @Value("${system.redisport}")
    private Integer redisPort;

    @Value("${system.redispassword}")
    private String redispassword;

    @Value("${system.rediskey}")
    private String rediskey;

    @GetMapping("/getBangumiData")
    public JSONArray getBangumiData(){
        RedisCacheUtil redisCacheUtil = RedisCacheUtil.getRedisCacheUtil(redishost, redisPort, redispassword);
        Map<String,String> bangumiData = redisCacheUtil.hgetAll(rediskey);
        Iterator<String> keys = bangumiData.keySet().iterator();
        JSONArray jsonArray = new JSONArray();
        while (keys.hasNext()){
            JSONObject jsonObject = JSONObject.parseObject(bangumiData.get(keys.next()));
            jsonArray.add(jsonObject);
        }
        return jsonArray;
    }

    @GetMapping("/index")
    public String getBangumiHtml(HttpServletRequest request){
        RedisCacheUtil redisCacheUtil = RedisCacheUtil.getRedisCacheUtil(redishost, redisPort, redispassword);
        Map<String,String> bangumiData = redisCacheUtil.hgetAll(rediskey);
        Iterator<String> keys = bangumiData.keySet().iterator();
//        JSONArray jsonArray = new JSONArray();
//        while (keys.hasNext()){
//            JSONObject jsonObject = JSONObject.parseObject(bangumiData.get(keys.next()));
//            jsonArray.add(jsonObject);
//        }
        StringBuilder result = new StringBuilder();
        result.append("          <style>\n" +
                "          div.bangumItem{\n" +
                "            height:60px;\n" +
                "            line-height:20px;\n" +
                "            margin-bottom:5px;\n" +
                "            border:1px solid #ff8c83;\n" +
                "            max-width:280px;\n" +
                "            white-space:nowrap;\n" +
                "          }\n" +
                "          div.bangumItem img{\n" +
                "            width:60px;\n" +
                "            height:auto;\n" +
                "            display:inline-block;\n" +
                "            float:left;\n" +
                "            padding-right:5px;\n" +
                "          }\n" +
                "          div.bangumItem a{\n" +
                "            text-decoration:none;\n" +
                "            color:#ff8c83;\n" +
                "          }\n" +
                "          div.bangumItem p{\n" +
                "            text-overflow:ellipsis;overflow:hidden;\n" +
                "            margin:0 auto auto 0;\n" +
                "          }\n" +
                "          div.bangumItem div.jinduBG{\n" +
                "            position:relative;\n" +
                "            height:16px;\n" +
                "            width:195px;\n" +
                "            background-color:gray;\n" +
                "            display:inline-block;\n" +
                "            border-radius:4px;\n" +
                "          }\n" +
                "          div.bangumItem div.jinduFG\n" +
                "          {\n" +
                "            height:16px;\n" +
                "            background-color:#ff8c83;\n" +
                "            border-radius:4px;\n" +
                "          }\n" +
                "          div.bangumItem div.jinduText\n" +
                "          {\n" +
                "            position:absolute;\n" +
                "            width:100%;height:auto;\n" +
                "            z-index:99;\n" +
                "            text-align:center;\n" +
                "            color:#fff;\n" +
                "            line-height:15px;\n" +
                "            font-size:15px;\n" +
                "          }\n" +
                "          </style>");
        while (keys.hasNext()){
            JSONObject jsonObject = JSONObject.parseObject(bangumiData.get(keys.next()));
            Integer nowEps = 0;
            try{
                nowEps =  jsonObject.getJSONObject("progressData").getJSONArray("eps").size();
            }
            catch (Exception e){
                log.info("get eps error,set defult 0");
            }

            result.append("  <div class = 'bangumItem'>\n" +
                    "          <a href=' "+ jsonObject.getJSONObject("subject").getString("url") +"' target='_blank'>\n" +
                    "            <img src='"+ jsonObject.getJSONObject("subject").getJSONObject("images").getString("grid") +"' />\n" +
                    "            <p> "+ jsonObject.getJSONObject("subject").getString("name") +"<br>\n" +
                    "           "+ jsonObject.getJSONObject("subject").getString("name_cn") +"<br>\n" +
                    "            <div class='jinduBG'>\n" +
                    "            <div class='jinduText'>进度:" +
                    nowEps +" / "+ jsonObject.getJSONObject("subject").getString("eps")
                    + "</div>\n" +
                    "            <div class='jinduFG' style='width:\" "+( nowEps /  Integer.parseInt(jsonObject.getJSONObject("subject").getString("eps")) * 195 )+" \"px;'>\n" +
                    "            </div>\n" +
                    "            </div>\n" +
                    "            </p>\n" +
                    "          </a>\n" +
                    "          </div>");
        }

        return result.toString();
    }
}
