package com.apitore.cherry.handson.vol3;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.charset.Charset;
import java.util.List;
import java.util.Map;

import javax.imageio.ImageIO;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.StringHttpMessageConverter;
import org.springframework.web.client.RestTemplate;


public class Step5_MsImageSearch {

  static String ENDPOINT     = "https://api.cognitive.microsoft.com/bing/v5.0/images/search";
  static String ACCESS_TOKEN = "YOUR-ACCESS-TOKEN";

  @SuppressWarnings({ "unchecked", "rawtypes" })
  public static void main(String[] args) throws MalformedURLException, IOException {
    RestTemplate restTemplate = new RestTemplate();
    restTemplate.getMessageConverters().add(0, new StringHttpMessageConverter(Charset.forName("UTF-8")));

    String url = String.format("%s?q=%s", ENDPOINT,"wall");

    HttpHeaders headers = new HttpHeaders();
    headers.setContentType(MediaType.APPLICATION_JSON);
    headers.set("Ocp-Apim-Subscription-Key", ACCESS_TOKEN);
    HttpEntity<String> entity = new HttpEntity<String>("", headers);

    ResponseEntity<Map> response = restTemplate
        .exchange(url, HttpMethod.GET, entity, Map.class);
    Map res = response.getBody();
    System.out.println(res);
    List<Map> list = (List<Map>) res.get("value");
    for (Map mp: list) {
      System.out.println(mp.get("thumbnailUrl"));

      String format = (String) mp.get("encodingFormat");
      String rtn = "./images/neta."+format;
      BufferedImage image = ImageIO.read(new URL((String) mp.get("thumbnailUrl")));
      ImageIO.write(image, format, new File(rtn));
      break;
    }
  }

}
