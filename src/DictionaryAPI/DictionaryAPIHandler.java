package DictionaryAPI;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.json.JSONArray;
import org.json.JSONObject;

import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;

public class DictionaryAPIHandler {
    public boolean wordExists(String word) {
        try {
            String encodedWord = URLEncoder.encode(word, StandardCharsets.UTF_8);
            String apiUrl = "https://api.dictionaryapi.dev/api/v2/entries/en/" + encodedWord.toLowerCase();

            HttpClient httpClient = HttpClients.createDefault();
            HttpGet request = new HttpGet(apiUrl);

            HttpResponse response = httpClient.execute(request);
            int statusCode = response.getStatusLine().getStatusCode();

            if (statusCode == 200) {
                HttpEntity entity = response.getEntity();
                if (entity != null) {
                    String responseBody = EntityUtils.toString(entity);
                    JSONArray jsonArray = new JSONArray(responseBody);
                    return !jsonArray.isEmpty();
                }
            } else if (statusCode == 404) {
                String responseBody = EntityUtils.toString(response.getEntity());
                JSONObject jsonObject = new JSONObject(responseBody);

                if (jsonObject.has("title") && jsonObject.getString("title").equals("No Definitions Found")) {
                    return false;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return false;
    }
}
