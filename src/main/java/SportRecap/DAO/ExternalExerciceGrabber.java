package SportRecap.DAO;


import SportRecap.model.Exercice;
import SportRecap.security.JWTUtil;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.http.HttpEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.util.EntityUtils;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
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
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.HashMap;

@Repository
public class ExternalExerciceGrabber {

    private final ConnectionPoolManager pool;

    @Autowired
    public ExternalExerciceGrabber(ConnectionPoolManager pool) {
        this.pool = pool;
    }


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

     public void saveExo(Exercice exercice) throws SQLException {
         Connection connection = this.pool.getConnection();
         PreparedStatement stat = connection.prepareStatement("INSERT INTO  exerciceliste (`categorie`, `currentWeight`,`muscle`,`description) VALUES (?,?,?,?)");
         stat.setString(1, exercice.getCategorie());
         stat.setInt(2, exercice.getCurrentWeight());
         stat.setString(3,exercice.getMuscle());
         stat.setString(4,exercice.getDescription());
         stat.executeUpdate();
         connection.close();
     }


     public void getExercice() throws IOException, SQLException, JSONException {
         CloseableHttpClient httpClient = HttpClientBuilder.create().build();
         try {
             HttpGet request = new HttpGet("https://wger.de/api/v2/exercise/?language=2%0A&limit=300");
             CloseableHttpResponse response = httpClient.execute(request);
             String result = EntityUtils.toString(response.getEntity());

             JSONArray ja = new JSONArray(result);

             int n = ja.length();
             for (int i = 0; i < n; i++) {
                 // GET INDIVIDUAL JSON OBJECT FROM JSON ARRAY
                 JSONObject jo = ja.getJSONObject(i);

                 // RETRIEVE EACH JSON OBJECT'S FIELDS
                 long id = jo.getLong("id");
                 String name = jo.getString("name");
                 String address = jo.getString("address");
                 String country = jo.getString("country");
                 String zip = jo.getString("zip");
                 double clat = jo.getDouble("lat");
                 double clon = jo.getDouble("lon");
                 String url = jo.getString("url");
                 String number = jo.getString("number");


                 Exercice exercice = new Exercice();

                 try {
                     this.saveExo(exercice);
                 } catch (Exception e) {
                     throw e;
                 }
             }
         }catch(Exception e){
            throw e;
         }finally {
             httpClient.close();
         }
    }
}
