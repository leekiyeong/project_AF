package com.project.projectAF.controller;

import com.fasterxml.jackson.core.json.JsonReadFeature;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.net.HttpURLConnection;
import java.net.URL;
import java.io.InputStreamReader;
import java.io.BufferedReader;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


@Controller
public class priceController {

    @RequestMapping(value = "/price.do" , produces ="application/json")
    public Map<String, Object> getPrice() throws Exception {



        //결과를 담을 변수들
        StringBuffer result = new StringBuffer();
        String strResult = "";

        // URL 설정
        StringBuilder urlBuilder = new StringBuilder("https://at.agromarket.kr/openApi/price/real.do");

        // search 변수는 인코딩이 필요하다고 했으므로 그 부분만 인코딩
        urlBuilder.append("?serviceKey=9840142966CE4DFCA4AC414062727788");
        urlBuilder.append("&apiType=json");
        urlBuilder.append("&pageNo=1");
        urlBuilder.append("&whsalCd=110001");

        URL url = new URL(urlBuilder.toString());
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
            return map;
        }catch(Exception e) {
            List<String> data = mapper.readValue(strResult, List.class);
            Map<String, Object> map = new HashMap<>();
            map.put("data", data);
            return map;
        }

    }


    @RequestMapping(value = "/dome.do" , produces ="application/json")
    public Map<String, Object> getDome() throws Exception {



        //결과를 담을 변수들
        StringBuffer result = new StringBuffer();
        String strResult = "";


        URL url = new URL("https://at.agromarket.kr/openApi/code/whsal.do?serviceKey=9840142966CE4DFCA4AC414062727788&apiType=json");
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
            map.put("datas", data);
            return map;
        }
    }



    @RequestMapping(value = "/combined.do" , produces ="application/json")
    public Map<String, Object> getPriceDome(@RequestParam(value = "codeId", required = false)
                                                String codeId) throws Exception {


        //결과를 담을 변수들
        StringBuffer result = new StringBuffer();
        String strResult = "";

        // URL 설정
        StringBuilder urlBuilder = new StringBuilder("https://at.agromarket.kr/openApi/price/real.do");

        // search 변수는 인코딩이 필요하다고 했으므로 그 부분만 인코딩
        urlBuilder.append("?serviceKey=9840142966CE4DFCA4AC414062727788");
        urlBuilder.append("&apiType=json");
        urlBuilder.append("&pageNo=1");
        if (codeId != null && !codeId.isEmpty()) {
            urlBuilder.append("&whsalCd=" + codeId);
        } else {
            urlBuilder.append("&whsalCd=110001");
        }

        URL url = new URL(urlBuilder.toString());
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();

        // Request 형식 설정
        conn.setRequestMethod("GET");
        conn.setRequestProperty("Content-Type", "application/json; charset=UTF-8");

        // 응답 데이터 받아오기
        BufferedReader rd;
        if (conn.getResponseCode() >= 200 & conn.getResponseCode() <= 300) {
            rd = new BufferedReader(new InputStreamReader(conn.getInputStream(), "UTF-8"));
        } else {
            rd = new BufferedReader(new InputStreamReader(conn.getErrorStream(), "UTF-8"));
        }

        String line;
        while ((line = rd.readLine()) != null) {
            result.append(line);
        }
        rd.close();
        conn.disconnect();
        strResult = result.toString();


        ObjectMapper mapper = new ObjectMapper();
        mapper.configure(DeserializationFeature.ACCEPT_SINGLE_VALUE_AS_ARRAY, true);
        mapper.enable(JsonReadFeature.ALLOW_NON_NUMERIC_NUMBERS.mappedFeature());


        //결과를 담을 변수들
        StringBuffer result2 = new StringBuffer();
        String strResult2 = "";


        URL url2 = new URL("https://at.agromarket.kr/openApi/code/whsal.do?serviceKey=9840142966CE4DFCA4AC414062727788&apiType=json");
        HttpURLConnection conn2 = (HttpURLConnection) url2.openConnection();

        // Request 형식 설정
        conn2.setRequestMethod("GET");
        conn2.setRequestProperty("Content-Type", "application/json; charset=UTF-8");

        // 응답 데이터 받아오기
        BufferedReader rd2;
        if (conn2.getResponseCode() >= 200 & conn2.getResponseCode() <= 300) {
            rd2 = new BufferedReader(new InputStreamReader(conn2.getInputStream(), "UTF-8"));
        } else {
            rd2 = new BufferedReader(new InputStreamReader(conn2.getErrorStream(), "UTF-8"));
        }

        String line2;
        while ((line2 = rd2.readLine()) != null) {
            result2.append(line2);
        }
        rd2.close();
        conn2.disconnect();
        strResult2 = result2.toString();

        try {
            Map<String, Object> map1 = mapper.readValue(strResult, Map.class);
            Map<String, Object> map2 = mapper.readValue(strResult2, Map.class);

            Map<String, Object> combinedData = new HashMap<>();
            combinedData.put("price", map1);
            combinedData.put("dome", map2);
            System.out.println(map2);
            System.out.println(map1);
            return combinedData;
        } catch (Exception e) {
            List<String> data = mapper.readValue(strResult, List.class);
            Map<String, Object> map = new HashMap<>();
            map.put("data", data);
            return map;
        }

    }

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