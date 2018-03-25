package stream.mrtg.rest;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;
import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.util.Properties;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.HttpClientBuilder;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

/**
 * @author Tyler
 */
// http://api.wunderground.com/api/9b1037b02b57292a/conditions/q/CA/San_Francisco.json
public class WeatherHandler {

  String zip;
  String url;
  String key;

  public WeatherHandler(String zip) {
    this.zip = zip;
    this.url = "http://api.wunderground.com/api/";
  }

  String getZip() {
    return this.zip;
  }

  void setKey() throws FileNotFoundException, IOException {
    Properties prop = new Properties();
    InputStream input = new FileInputStream("config.properties");
    prop.load(input);
    this.key = prop.getProperty("api-key");
  }

  void makeUrl() {
    StringBuilder builder = new StringBuilder(this.url);
    builder.append(key);
    builder.append("/conditions/q/");
    builder.append(zip);
    builder.append(".json");
    this.url = builder.toString();
  }

  String makeRequest() throws MalformedURLException, IOException {
    HttpClient client = HttpClientBuilder.create().build();
    HttpGet request = new HttpGet(url);

    request.addHeader("User-Agent", "Mozilla/5.0");
    HttpResponse response = client.execute(request);
    BufferedReader rd = new BufferedReader(
            new InputStreamReader(response.getEntity().getContent()));

    StringBuilder result = new StringBuilder();
    String line;
    while ((line = rd.readLine()) != null) {
      result.append(line);
    }
    return result.toString();
  }

  String parseJson(String json) throws ParseException {
    JSONParser parser = new JSONParser();
    JSONObject main = (JSONObject) parser.parse(json);
    JSONObject current_observation = (JSONObject) main.get("current_observation");
    JSONObject display_location = (JSONObject) current_observation.get("display_location");
    
    String city = (String) display_location.get("city");
    String state = (String) display_location.get("state");
    double temperature = (double) current_observation.get("temp_f");
    
    String response = Math.round(temperature) + " F in " + city + ", " + state;
    return response;
  }

  String prettyPrint(String uglyJson) {
    Gson gson = new GsonBuilder().setPrettyPrinting().create();
    JsonParser jp = new JsonParser();
    JsonElement je = jp.parse(uglyJson);
    String prettyJson = gson.toJson(je);
    return prettyJson;
  }

}
