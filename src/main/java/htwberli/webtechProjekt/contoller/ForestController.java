package htwberli.webtechProjekt.contoller;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import htwberli.webtechProjekt.messages.AIMessage;
import htwberli.webtechProjekt.messages.AIMessageEntity;
import htwberli.webtechProjekt.messages.UserMessage;
import htwberli.webtechProjekt.service.ForestService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@CrossOrigin(origins = "http://localhost:5173/")
@RestController
public class ForestController {
    @Autowired
    ForestService service;
    Logger logger = LoggerFactory.getLogger(ForestController.class);

    @GetMapping("/greeting")
    public String greeting(@RequestParam(name = "name", required = false, defaultValue = "World") String name) {

        return "hello, " + name;
    }

    @PostMapping("/messages")
    public AIMessageEntity createAIMessage(@RequestBody AIMessage aimessage) {
        AIMessageEntity aiMessageEntity = new AIMessageEntity(aimessage);
        return service.save(aiMessageEntity);
    }

/*    @GetMapping("/messages/{id}")
    public AIMessageEntity getMessage(@PathVariable Long id) {
        logger.info("GET request on route things with {}", id);
        Long messageId = id;
        return service.get(messageId);
    }*/

    @DeleteMapping("messages/delete/{id}")
    public void deleteAIMessageEnitiy(@PathVariable Long id) {
        logger.info("Delete request with {}", id);
        service.deleteAIMessageByID(id);
    }

    @GetMapping("/messages")
    public List<AIMessageEntity> getAllMessages() {
        return service.getAllAIMessages();
    }

    @PostMapping("/api/chat")
    public Map<String, String> chatResponse(@RequestBody UserMessage request) throws Exception {
        // Parse the response from the VUE frontend and extract the userinput.
        String content = request.getContent().getMessage();
        System.out.println(content);
        // Build and return the response to the frontend
        Map<String, String> responseMap = new HashMap<>();
        responseMap.put("reply", openAIConnection(content));
        //System.out.println(responseMap.get("reply"));
        return responseMap;
    }

    @GetMapping("/")
    public String root() {
        return "hello";
    }

    private String openAIConnection(String userMessage) {
        RestTemplate restTemplate = new RestTemplate();
        String url = "https://api.openai.com/v1/chat/completions";
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        //OPENAI Key
        String openAIKey = System.getenv("OPENAI_API_KEY");
        headers.setBearerAuth(openAIKey);
        String message = "[{\"role\": \"user\", \"content\": \"" + userMessage + "\"}]";
        String model = "gpt-3.5-turbo";
        int maxTokens = 2000;
        String requestBody = "{\"model\": \"" + model + "\", \"messages\": " + message + ", \"max_tokens\": " + maxTokens + "}";
        HttpEntity<String> requestEntity = new HttpEntity<>(requestBody, headers);
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            String response = restTemplate.postForObject(url, requestEntity, String.class);
            JsonNode jsonNode = objectMapper.readTree(response);
            //Todo fix json from personaChoice Button inputs...
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
