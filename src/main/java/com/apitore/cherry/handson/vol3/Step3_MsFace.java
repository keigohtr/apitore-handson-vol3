package com.apitore.cherry.handson.vol3;

import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.Map;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.StringHttpMessageConverter;
import org.springframework.web.client.RestTemplate;

public class Step3_MsFace {

  static String ENDPOINT     = "https://westcentralus.api.cognitive.microsoft.com/face/v1.0/detect";
  static String ACCESS_TOKEN = "YOUR-ACCESS-TOKEN";
  static String[] LANDS = new String[]{
      "pupilLeft","pupilRight","noseTip",
      "mouthLeft","mouthRight","eyebrowLeftOuter",
      "eyebrowLeftInner","eyeLeftOuter","eyeLeftTop",
      "eyeLeftBottom","eyeLeftInner","eyebrowRightInner",
      "eyebrowRightOuter","eyeRightInner","eyeRightTop",
      "eyeRightBottom","eyeRightOuter","noseRootLeft",
      "noseRootRight","noseLeftAlarTop","noseRightAlarTop",
      "noseLeftAlarOutTip","noseRightAlarOutTip","upperLipTop",
      "upperLipBottom","underLipTop","underLipBottom",
      };

  @SuppressWarnings("rawtypes")
  public static void main(String[] args) throws IOException {
    RestTemplate restTemplate = new RestTemplate();
    restTemplate.getMessageConverters().add(0, new StringHttpMessageConverter(Charset.forName("UTF-8")));

    String url = String.format("%s?returnFaceLandmarks=true&returnFaceAttributes=emotion", ENDPOINT);

    Path path = Paths.get("./images/image1.JPG");
    byte[] data = Files.readAllBytes(path);

    HttpHeaders headers = new HttpHeaders();
    headers.setContentType(MediaType.APPLICATION_OCTET_STREAM);
    headers.set("Ocp-Apim-Subscription-Key", ACCESS_TOKEN);
    HttpEntity<byte[]> entity = new HttpEntity<byte[]>(data, headers);

    ResponseEntity<List> response = restTemplate
        .exchange(url, HttpMethod.POST, entity, List.class);
    List res = response.getBody();
    System.out.println(res);
    Map mp = (Map) res.get(0);
    Map fmp = (Map) mp.get("faceRectangle");
    int[] face = new int[4];
    face[0] = (int) fmp.get("left");
    face[1] = (int) fmp.get("top");
    face[2] = (int) fmp.get("width");
    face[3] = (int) fmp.get("height");
    Map lmp = (Map) mp.get("faceLandmarks");
    Map tmp = (Map) lmp.get(LANDS[0]);
    int x = (int)((double) tmp.get("x"));
    int y = (int)((double) tmp.get("y"));
  }

}
