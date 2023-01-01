package SportRecap.DAO;


import SportRecap.model.Exercice;
import SportRecap.model.VerificationToken;
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
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
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
         PreparedStatement stat = connection.prepareStatement("INSERT INTO  exerciceliste (`name`, `category`,`muscle`,`description`) VALUES (?,?,?,?)");
         stat.setString(1, exercice.getName());
         stat.setString(2, exercice.getCategorie());
         if(exercice.getMuscle()!=null) stat.setString(3,exercice.getMuscle());
         else stat.setString(3,"null");
         stat.setString(4,exercice.getDescription());
         stat.executeUpdate();
         connection.close();
     }

     public void grabCategory() throws SQLException, JSONException, IOException {
         CloseableHttpClient httpClient = HttpClientBuilder.create().build();
         JSONObject json = new JSONObject();
         try{
             HttpGet request = new HttpGet("https://wger.de/api/v2/exercisecategory/");
             StringEntity params = new StringEntity(json.toString());
             CloseableHttpResponse response = httpClient.execute(request);

             String str  = EntityUtils.toString(response.getEntity());
             JSONObject jsonResult = new JSONObject(str);
             JSONArray ja = jsonResult.getJSONArray("results");

             int n = ja.length();
             for (int i = 0; i < n; i++) {

                 JSONObject jo = ja.getJSONObject(i);
                 int id = jo.getInt("id");
                 String name = jo.getString("name");
                 this.saveCategory(id,name);
             }
         } catch (Exception e) {
             throw e;
         } finally {
             httpClient.close();
         }

     }

    public void grabMuscles() throws SQLException, JSONException, IOException {
        CloseableHttpClient httpClient = HttpClientBuilder.create().build();
        JSONObject json = new JSONObject();
        try{
            HttpGet request = new HttpGet("https://wger.de/api/v2/muscle");
            StringEntity params = new StringEntity(json.toString());
            CloseableHttpResponse response = httpClient.execute(request);

            String str  = EntityUtils.toString(response.getEntity());
            JSONObject jsonResult = new JSONObject(str);
            JSONArray ja = jsonResult.getJSONArray("results");

            int n = ja.length();
            for (int i = 0; i < n; i++) {

                JSONObject jo = ja.getJSONObject(i);
                int id = jo.getInt("id");
                String name = "";
                if(jo.getString("name_en").equals("")) name = jo.getString("name");
                else name = jo.getString("name_en");
                this.saveMuscles(id,name);
            }
        } catch (Exception e) {
            throw e;
        } finally {
            httpClient.close();
        }

    }

    private void saveMuscles(int id, String name) throws SQLException {
        Connection connection = this.pool.getConnection();
        PreparedStatement stat = connection.prepareStatement("INSERT INTO  muscle (`id`,`name`) VALUES (?,?)");
        stat.setInt(1, id);
        stat.setString(2, name);
        stat.executeUpdate();
        connection.close();
    }


    public String getCategory(int id ) throws SQLException {
         Connection connection = this.pool.getConnection();
         PreparedStatement stat = connection.prepareStatement("Select name FROM category where id= ?");
         stat.setInt(1, id);
         ResultSet res = stat.executeQuery();
         while (res.next()) {
             String name = res.getString(1);
             connection.close();
             return name;
         }
         connection.close();
         return "";
     }

    public String getMuscle(int id ) throws SQLException {
        Connection connection = this.pool.getConnection();
        PreparedStatement stat = connection.prepareStatement("Select name FROM muscle where id= ?");
        stat.setInt(1, id);
        ResultSet res = stat.executeQuery();
        while (res.next()) {
            String name = res.getString(1);
            connection.close();
            return name;
        }
        connection.close();
        return "";
    }

    private void saveCategory(int id, String name) throws SQLException {
        Connection connection = this.pool.getConnection();
        PreparedStatement stat = connection.prepareStatement("INSERT INTO  category (`id`,`name`) VALUES (?,?)");
        stat.setInt(1, id);
        stat.setString(2, name);
        stat.executeUpdate();
        connection.close();
    }


    public void grabExercice() throws IOException, SQLException, JSONException {
         this.grabCategory();
         this.grabMuscles();
         CloseableHttpClient httpClient = HttpClientBuilder.create().build();
         try {
             HttpGet request = new HttpGet("https://wger.de/api/v2/exercise/?language=2%0A&limit=300");
             CloseableHttpResponse response = httpClient.execute(request);
             String str = EntityUtils.toString(response.getEntity());
             JSONObject jsonResult = new JSONObject(str);
             JSONArray ja = jsonResult.getJSONArray("results");

             int n = ja.length();
             for (int i = 0; i < n; i++) {
                 // GET INDIVIDUAL JSON OBJECT FROM JSON ARRAY
                 JSONObject jo = ja.getJSONObject(i);

                 // RETRIEVE EACH JSON OBJECT'S FIELDS

                 String  name= jo.getString("name");
                 String description= jo.getString("description");

                 String categorie = getCategory(jo.getInt("category"));
                 String muscle = "";
                 try{
                 JSONArray mu = jo.getJSONArray("muscles");
                     muscle = getMuscle(mu.getInt(1));
                 }catch (Exception e){
                     muscle = "";
                 }

                 Exercice exercice = new Exercice(name,categorie,muscle,description);
                 System.out.println(exercice.toString());
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
