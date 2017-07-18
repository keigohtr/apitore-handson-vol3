package com.apitore.cherry.handson.vol3;

import java.nio.charset.Charset;
import java.util.Map;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.StringHttpMessageConverter;
import org.springframework.web.client.RestTemplate;

import com.fasterxml.jackson.databind.ObjectMapper;

public class Step1_MsComputerVision {

  static String ENDPOINT     = "https://westcentralus.api.cognitive.microsoft.com/vision/v1.0/analyze";
  static String ACCESS_TOKEN = "YOUR-ACCESS-TOKEN";

  public static void main(String[] args) {
    RestTemplate restTemplate = new RestTemplate();
    restTemplate.getMessageConverters().add(0, new StringHttpMessageConverter(Charset.forName("UTF-8")));

    String url = String.format("%s?visualFeatures=Tags", ENDPOINT);

    ObjectMapper mapper = new ObjectMapper();
    String json = "{\"url\":\"https://upload.wikimedia.org/wikipedia/commons/1/12/Broadway_and_Times_Square_by_night.jpg\"}";

    HttpHeaders headers = new HttpHeaders();
    headers.setContentType(MediaType.APPLICATION_JSON);
    headers.set("Ocp-Apim-Subscription-Key", ACCESS_TOKEN);
    HttpEntity<String> entity = new HttpEntity<String>(json, headers);

    ResponseEntity<Map> response = restTemplate
        .exchange(url, HttpMethod.POST, entity, Map.class);
    Map res = response.getBody();
    System.out.println(res);
  }

}
