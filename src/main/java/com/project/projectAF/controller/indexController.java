package com.project.projectAF.controller;

import com.fasterxml.jackson.core.json.JsonReadFeature;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.exc.MismatchedInputException;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


@Controller
public class indexController {



    @RequestMapping(value = "/index.do" , produces ="application/json")
    public Map<String, Object> getGraph(
            @RequestParam(required = false, name = "productclscode") String productclscode,
            @RequestParam(required = false, name = "itemcategory") String itemcategory,
            @RequestParam(required = false, name = "itemcode") String itemcode,
            @RequestParam(required = false, name = "kindcode") String kindcode,
            @RequestParam(required = false, name = "itemcategoryname") String itemcategoryname,
            @RequestParam(required = false, name = "itemname") String itemname,
            @RequestParam(required = false, name = "itemname2") String itemname2
    ) throws Exception {

        System.out.println(itemcategoryname);
        System.out.println(itemname);
        System.out.println(itemname2);
        productclscode =           (productclscode != null) ? productclscode : "2";
        itemcategory =             (itemcategory != null)   ? itemcategory : "200";
        itemcode =                 (itemcode != null)       ? itemcode : "211";
        kindcode =                 (kindcode != null)       ? kindcode : "06";
        itemcategoryname =         (itemcategoryname != null)       ? itemcategoryname : "채소류";
        itemname =                 (itemname != null)       ? itemname : "배추";
        itemname2 =                (itemname2 != null)       ? itemname2 : "월동";


        LocalDate currentDate = LocalDate.now();

        // 15일 전 날짜 계산
        LocalDate startDate = currentDate.minusDays(15);

        // 날짜 포맷 설정
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");

        //결과를 담을 변수들
        StringBuffer result = new StringBuffer();
        String strResult = "";


        URL url = new URL("http://www.kamis.or.kr/service/price/xml.do?action=periodProductList" +
                "&p_productclscode=0"  + productclscode +                         // 도/소매
                "&p_startday="        + startDate.format(formatter) +             // 시작일
                "&p_endday="          + currentDate.format(formatter) +           // 종료일
                "&p_itemcategory="    + itemcategory +                            // 부류코드
                "&p_itemcode="        + itemcode +                                // 품목코드
                "&p_kindcode="        + kindcode +                                // 품종코드
                "&p_countrycode=1101" +                                           // 지역코드
                "&p_cert_key=111"     +
                "&p_cert_id=222&p_returntype=json");
        HttpURLConnection conn = (HttpURLConnection)url.openConnection();
        System.out.println(url);
        // Request 형식 설정
        conn.setRequestMethod("GET");
        conn.setRequestProperty("Content-Type", "application/json; charset=UTF-8");

        // 응답 데이터 받아오기
        BufferedReader rd;
        System.out.println(conn.getResponseCode());
        if(conn.getResponseCode() == 302) {
            String newUrl = conn.getHeaderField("Location");
            System.out.println(newUrl);
            URL redirectUrl = new URL(newUrl);
            try {
                conn = (HttpURLConnection) redirectUrl.openConnection();
                // 다시 응답 데이터 받아오기
                rd = new BufferedReader(new InputStreamReader(conn.getInputStream(), "UTF-8"));
            } catch (IOException e) {
                rd = null;
            }

        } else {
            rd = new BufferedReader(new InputStreamReader(conn.getErrorStream(), "UTF-8"));
        }

        String line = "";
        System.out.println("line=" +line);
        try{
            while((line = rd.readLine()) != null) {
                result.append(line);
            }
            rd.close();
            conn.disconnect();
        } catch(NullPointerException e) {
            System.out.println("rd = " + rd);
        }



        strResult = result.toString();
        System.out.println("strResult=" + strResult);


        ObjectMapper mapper = new ObjectMapper();
        mapper.configure(DeserializationFeature.ACCEPT_SINGLE_VALUE_AS_ARRAY, true);
        mapper.enable(JsonReadFeature.ALLOW_NON_NUMERIC_NUMBERS.mappedFeature());

        //URL 2222222222
        StringBuffer result2 = new StringBuffer();
        String strResult2 = "";

        // 상대경로를 사용하기 위해 Resource 사용.
        String filePath = "templates/data_a.json";
        Resource resource = new ClassPathResource(filePath);



        // 파일 읽어오는 코드
        try (BufferedReader rd2 = new BufferedReader(new InputStreamReader(resource.getInputStream(), StandardCharsets.UTF_8))) {
            String line2;
            while ((line2 = rd2.readLine()) != null) {
                result2.append(line2);
            }
        } catch (IOException e) {
            // 예외 처리 (파일 읽기 중 에러가 발생한 경우)
            e.printStackTrace();
        }

        strResult2 = result2.toString();
        System.out.println("strResult2 = " +strResult2);

        try {
            Map<String, Object> map;
            try {
                map = mapper.readValue(strResult, new TypeReference<Map<String, Object>>() {});
                System.out.println("map = " + map);
            } catch (MismatchedInputException e) {
                // 데이터가 없는 경우, 빈 JSON 객체로 초기화
                map = Collections.emptyMap();
                System.out.println("mapempty = " + map);
            }
            System.out.println("map = "+ map);
            Map<String, Object> map2 = mapper.readValue(strResult2, Map.class);
            System.out.println("map2 = " + map2);

            // 첫번째 map이 null인 경우  초기화 해서 NullException 방지
            if (map.isEmpty()) {
                return  map2;
            }
            System.out.println("111map = "+ map);
            System.out.println("111map2 = " + map2);
            map.putAll(map2);
            System.out.println("222map = "+ map);
            System.out.println("222map2 = " + map2);
            map.put("productclscode", productclscode);
            map.put("itemcategory", itemcategory);
            map.put("itemcode", itemcode);
            map.put("kindcode", kindcode);
            map.put("itemcategoryname", itemcategoryname);
            map.put("itemname", itemname);
            map.put("itemname2", itemname2);
            return map;
        }catch(Exception e) {
            List<String> data = mapper.readValue(strResult, List.class);
            Map<String, Object> map = new HashMap<>();
            map.put("data", data);
            System.out.println("catch");
            return map;
        }

    }
}