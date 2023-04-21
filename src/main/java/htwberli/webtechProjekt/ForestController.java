package htwberli.webtechProjekt;
import ch.qos.logback.core.net.SyslogOutputStream;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;
import java.util.HashMap;
import java.util.Map;

@RestController
public class ForestController {
    @GetMapping("/greeting")
        public String greeting(@RequestParam(name="name", required=false, defaultValue="World") String name) {
            return "hello, " + name;
        }
        @PostMapping("/api/chat")
        public Map<String, String> chatResponse(@RequestBody Message request) throws Exception {
            // Parse the response from the VUE frontend and extract the userinput.
            //request.forEach((key,value) -> System.out.println(key+" = "+ value));
            //System.out.println(request.getContent()+request.getFrom()+request.getId());
            String content = request.getContent().getMessage();
            System.out.println(content);
            // Build and return the response to the frontend
            Map<String, String> responseMap = new HashMap<>();
            responseMap.put("reply", openAIConnection(content));
            return responseMap;
        }
        @GetMapping("/")
        public String root() {
            return "hello";
        }
        private String openAIConnection(String userMessage){
            RestTemplate restTemplate = new RestTemplate();
            String url = "https://api.openai.com/v1/chat/completions";
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_JSON);
            //OPENAI Key
            String openAIKey = System.getenv("OPENAI_API_KEY");
            headers.setBearerAuth(openAIKey);
            String message = "[{\"role\": \"user\", \"content\": \""+userMessage+"\"}]";
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
                return messageContent;

            } catch (Exception e) {
                // Handle the error here
                e.printStackTrace();
            }
            return "err";
    }
    }
