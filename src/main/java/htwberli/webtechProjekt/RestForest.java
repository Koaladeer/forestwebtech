package htwberli.webtechProjekt;

//import org.json.simple.JSONObject;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import okhttp3.OkHttpClient;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.web.client.RestTemplate;


public class RestForest {

 public static void main(String[] args) {

 RestTemplate restTemplate = new RestTemplate();
     String url = "https://api.openai.com/v1/chat/completions";
     HttpHeaders headers = new HttpHeaders();
     headers.setContentType(MediaType.APPLICATION_JSON);
     headers.setBearerAuth("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");
     String message = "[{\"role\": \"user\", \"content\": \"Hello, how are you?\"}]";

     String model = "gpt-3.5-turbo";
     int maxTokens = 100;
     String requestBody = "{\"model\": \"" + model + "\", \"messages\": " + message + ", \"max_tokens\": " + maxTokens + "}";
     HttpEntity<String> requestEntity = new HttpEntity<>(requestBody, headers);
     ObjectMapper objectMapper = new ObjectMapper();
     try {
         String response = restTemplate.postForObject(url, requestEntity, String.class);
         JsonNode jsonNode =  objectMapper.readTree(response);
         String messageContent = jsonNode.get("choices").get(0).get("message").get("content").asText();
         System.out.println(messageContent);
     } catch (Exception e) {
         // Handle the error here
         e.printStackTrace();
     }
        }
}

