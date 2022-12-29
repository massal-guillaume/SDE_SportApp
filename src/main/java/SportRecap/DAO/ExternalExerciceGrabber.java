package SportRecap.DAO;


import SportRecap.security.JWTUtil;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.http.HttpEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.util.EntityUtils;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.http.*;
import org.springframework.stereotype.Repository;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URL;
import java.net.http.HttpClient;
import java.net.http.HttpResponse;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;

@Repository
public class ExternalExerciceGrabber {


     public String getToken() throws JSONException, IOException {

         JSONObject json = new JSONObject();
         json.put("username",JWTUtil.API_USERNAME);
         json.put("password",JWTUtil.API_PASSWORD);

         CloseableHttpClient httpClient = HttpClientBuilder.create().build();
         try {
             HttpPost request = new HttpPost("https://wger.de/api/v2/token/");
             StringEntity params = new StringEntity(json.toString());
             request.addHeader("content-type", "application/json");
             request.setEntity(params);
             CloseableHttpResponse response = httpClient.execute(request);
             String token  = EntityUtils.toString(response.getEntity()).split("\"")[7];
             return token;
         } catch (Exception e) {
             throw e;
         } finally {
             httpClient.close();
         }
     }



}
