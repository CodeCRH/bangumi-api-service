package com.tokyo3rd.service;

import cn.hutool.http.HttpRequest;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

/**
 * @Author: CodeCRH
 * @Date: 2020/1/19
 */
@Service
public class BangumiApiService {

    private static Logger log = LoggerFactory.getLogger(BangumiApiService.class);

    /**
     * 登陆(post)
     *
     * @param appName
     * @param userName
     * @param password
     * @return bangumi返回的数据 JSONObject
     * @throws Exception
     */
    public JSONObject login(String appName, String userName, String password) throws Exception {
        Map<String, Object> formMap = new HashMap<>(2);
        formMap.put("username", userName);
        formMap.put("password", password);
        HttpRequest httpRequest = HttpRequest.post("https://api.bgm.tv/auth?source=" + appName)
                .form(formMap);
        JSONObject jsonObject = JSONObject.parseObject(httpRequest.execute().body());
        return jsonObject;
    }

    /**
     * 获取所有收藏内容(Get)[仅在看]
     *
     * @param userId
     * @return bangumi返回的数据 JSONArray
     * @throws Exception
     */
    public JSONArray collection(String userId) throws Exception {
        HttpRequest httpRequest = HttpRequest.get("https://api.bgm.tv/user/" + userId + "/collection?cat=tokyo3rdcat");
        String body = httpRequest.execute().body();
        JSONArray jsonArray = JSONArray.parseArray(body);
        return jsonArray;
    }

    /**
     * 获取某用户某部剧观看详细信息(Get)
     *
     * @param userId
     * @param subjectId
     * @param appName
     * @param authEncode
     * @return
     * @throws Exception
     */
    public JSONObject progress(String userId, String subjectId, String appName, String authEncode) throws Exception {
        HttpRequest httpRequest = HttpRequest.get("https://api.bgm.tv/user/" + userId + "/progress?subject_id=" + subjectId + "&source=" + appName + "&auth=" + authEncode);
        JSONObject jsonObject = JSONObject.parseObject(httpRequest.execute().body());
        return jsonObject;
    }

    /**
     * 获取某部剧的摘要描述(Get)
     *
     * @param subjectId
     * @return Json，包含该剧的摘要信息，如果没有详细信息，返回json code 404。
     * @throws Exception
     */
    public JSONObject simpleSubject(String subjectId) throws Exception {
        HttpRequest httpRequest = HttpRequest.get("https://api.bgm.tv/subject/" + subjectId + "?responseGroup=simple");
        JSONObject jsonObject = JSONObject.parseObject(httpRequest.execute().body());
        return jsonObject;
    }

    /**
     * 获取某部剧的详细描述(Get)
     *
     * @param subjectId
     * @return Json，包含该剧的详细信息，如果没有详细信息，返回json code 404。
     * @throws Exception
     */
    public JSONObject largeSubject(String subjectId) throws Exception {
        HttpRequest httpRequest = HttpRequest.get("https://api.bgm.tv/subject/" + subjectId + "?responseGroup=large");
        JSONObject jsonObject = JSONObject.parseObject(httpRequest.execute().body());
        return jsonObject;
    }
}
