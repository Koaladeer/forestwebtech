package htwberli.webtechProjekt.contoller;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import htwberli.webtechProjekt.messages.AIMessage;
import htwberli.webtechProjekt.messages.AIMessageEntity;
import htwberli.webtechProjekt.messages.ChatMessage;
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

/**
 * Controller class that handles HTTP requests for the forest application.
 */
@CrossOrigin(origins = "http://localhost:5173/")
@RestController
public class ForestController {

    @Autowired
    ForestService service;

    Logger logger = LoggerFactory.getLogger(ForestController.class);

    /**
     * Handles GET requests to the "/greeting" endpoint.
     *
     * @param name the name parameter (optional) to include in the greeting
     * @return a greeting message
     */
    @GetMapping("/greeting")
    public String greeting(@RequestParam(name = "name", required = false, defaultValue = "World") String name) {
        return "hello, " + name;
    }

    /**
     * Handles POST requests to the "/messages" endpoint.
     *
     * @param aimessage the request body containing an AIMessage
     * @return the created AIMessageEntity
     */
    @PostMapping("/messages")
    public AIMessageEntity createAIMessage(@RequestBody AIMessage aimessage) {
        AIMessageEntity aiMessageEntity = new AIMessageEntity(aimessage);
        return service.save(aiMessageEntity);
    }

    /**
     * Handles POST requests to the "/chatmessages" endpoint.
     *
     * @param chatMessage the request body containing a ChatMessage
     * @return the created ChatMessage
     */
    @PostMapping("/chatmessages")
    public ChatMessage createChatMessage(@RequestBody ChatMessage chatMessage) {
        ChatMessage message = new ChatMessage();
        return service.save(message);
    }

    /**
     * Handles DELETE requests to the "/messages/delete/{id}" endpoint.
     *
     * @param id the ID of the AIMessageEntity to be deleted
     */
    @DeleteMapping("messages/delete/{id}")
    public void deleteAIMessageEnitiy(@PathVariable Long id) {
        logger.info("Delete request with {}", id);
        service.deleteAIMessageByID(id);
    }

    /**
     * Handles GET requests to the "/messages" endpoint.
     *
     * @return a list of all AIMessageEntities
     */
    @GetMapping("/messages")
    public List<AIMessageEntity> getAllMessages() {
        return service.getAllAIMessages();
    }

    /**
     * Handles POST requests to the "/api/chat" endpoint.
     *
     * @param request the request body containing a UserMessage
     * @return a Map containing the chat response
     * @throws Exception if an error occurs during the chat response generation
     */
    @PostMapping("/api/chat")
    public Map<String, String> chatResponse(@RequestBody UserMessage request) throws Exception {
        // Parse the response from the VUE frontend and extract the userinput.
        String userMessageContent = request.getContent().getContent();
        /*System.out.println("id:"+request.getContent().getId());
        System.out.println("role:"+request.getContent().getRole());
        System.out.println("content:"+userMessageContent);*/
        // Save the UserMessage to DB to use as context for the AI
        service.save(request.getContent());
        // Build and return the response to the frontend
        Map<String, String> responseMap = new HashMap<>();
        responseMap.put("reply", service.openAIConnection(service.getContextMessages(request)));
        System.out.println(responseMap.toString());
        return responseMap;
    }

    @GetMapping("/")
    public String root() {
        return "hello";
    }
}
