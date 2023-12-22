package com.project.projectAF.controller;

import com.fasterxml.jackson.core.json.JsonReadFeature;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import java.net.HttpURLConnection;
import java.net.URL;
import java.io.InputStreamReader;
import java.io.BufferedReader;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


@Controller
public class priceController {


    @RequestMapping(value = "/daily.do" , produces ="application/json")
    public Map<String, Object> getDaily() throws Exception {



        //결과를 담을 변수들
        StringBuffer result = new StringBuffer();
        String strResult = "";


        URL url = new URL("http://www.kamis.co.kr/service/price/xml.do?action=dailySalesList" +
                "&p_cert_key=test" +
                "&p_cert_id=test" +
                "&date=20231201" +
                "&p_returntype=json");
        HttpURLConnection conn = (HttpURLConnection)url.openConnection();

        // Request 형식 설정
        conn.setRequestMethod("GET");
        conn.setRequestProperty("Content-Type", "application/json; charset=UTF-8");

        // 응답 데이터 받아오기
        BufferedReader rd;
        if(conn.getResponseCode() >= 200 & conn.getResponseCode() <= 300) {
            rd = new BufferedReader(new InputStreamReader(conn.getInputStream(), "UTF-8"));
        } else {
            rd = new BufferedReader(new InputStreamReader(conn.getErrorStream(), "UTF-8"));
        }

        String line;
        while((line = rd.readLine()) != null) {
            result.append(line);
        }
        rd.close();
        conn.disconnect();
        strResult = result.toString();


        ObjectMapper mapper = new ObjectMapper();
        mapper.configure(DeserializationFeature.ACCEPT_SINGLE_VALUE_AS_ARRAY, true);
        mapper.enable(JsonReadFeature.ALLOW_NON_NUMERIC_NUMBERS.mappedFeature());




        try {
            Map<String, Object> map = mapper.readValue(strResult, Map.class);
            System.out.println(map);
            return map;
        }catch(Exception e) {
            List<String> data = mapper.readValue(strResult, List.class);
            Map<String, Object> map = new HashMap<>();
            map.put("data", data);
            return map;
        }

    }
}