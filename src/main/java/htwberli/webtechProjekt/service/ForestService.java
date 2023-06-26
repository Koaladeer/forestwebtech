package htwberli.webtechProjekt.service;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import htwberli.webtechProjekt.AIMessageRepo;
import htwberli.webtechProjekt.ChatMessagesRepo;
import htwberli.webtechProjekt.messages.AIMessageEntity;
import htwberli.webtechProjekt.messages.ChatMessage;

import htwberli.webtechProjekt.messages.UserMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

@Service
public class ForestService {
    @Autowired
    AIMessageRepo repo;
    @Autowired
    ChatMessagesRepo chatRepo;
    //CHATMESSAGE
    public ChatMessage save(ChatMessage message) {
        return chatRepo.save(message);
    }
    public void deleteChatMessagesByID(Long id){
        chatRepo.deleteById(id);
    }
    public List<ChatMessage> getAllChatMessages() {
        Iterable<ChatMessage> iterator = chatRepo.findAll();
        List<ChatMessage> messages = new ArrayList<>();
        for (ChatMessage message : iterator) messages.add(message);
        System.out.println();
        return messages;
    }
    //AIMESSAGE
    public AIMessageEntity save(AIMessageEntity message) {
        return repo.save(message);
    }
    public void deleteAIMessageByID(Long id){
        repo.deleteById(id);
    }

    public AIMessageEntity get(Long id) {
        return repo.findById(id).orElseThrow(() -> new RuntimeException());
    }

    public List<AIMessageEntity> getAllAIMessages() {
        Iterable<AIMessageEntity> iterator = repo.findAll();
        List<AIMessageEntity> messages = new ArrayList<AIMessageEntity>();
        for (AIMessageEntity message : iterator) messages.add(message);
        System.out.println();
        return messages;
    }
///e
    public String searchStringForCode(String message) {
        // Define the regular expression to match code blocks
        Pattern pattern = Pattern.compile("```.*?```", Pattern.DOTALL);
        // Use a matcher to find and remove the code blocks from the message
        Matcher matcher = pattern.matcher(message);
        String filteredMessage = matcher.replaceAll("");
        System.out.println(filteredMessage);
        return filteredMessage;
    }
    public String getContextMessages(UserMessage userMessage){
        long sid = userMessage.getContent().getsId();
        List<ChatMessage> chatMessages = new ArrayList<>();
        List<AIMessageEntity> aiMessages = new ArrayList<>();
        aiMessages.addAll(getAllAIMessages().stream()
                .filter(aim1 -> aim1.getsId() == sid)
                .collect(Collectors.toList()));
        chatMessages.addAll(getAllChatMessages().stream()
                .filter(cm1 -> cm1.getsId() == sid)
                .collect(Collectors.toList()));
        StringBuilder history = new StringBuilder();
        history.append("[");
        int index = 0;
        int i = 0;
        int j = 0;
        while (index < chatMessages.size()+aiMessages.size()) {
            if (i < chatMessages.size()) {
                ChatMessage cm = chatMessages.get(i);
                history.append("{\"role\": \"").append(cm.getRole()).append( "\", \"content\": \"" + cm.getContent() + "\"}");
                if (i+1 <chatMessages.size() ) history.append(",");
                i++;
                index++;
            }
            if (j < aiMessages.size()) {
                AIMessageEntity aim = aiMessages.get(j);
                history.append("{\"role\": \"system\", \"content\": \"").append(aim.getMessage()).append("\"}");
                if (j+1 <chatMessages.size() ) history.append(",");
                j++;
                index++;
            }
        }

        history.append("]");
        //System.out.println("Test:"+history.toString());
        return history.toString();
    }
    public String openAIConnection(String userMessage) {
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
        //String requestBody = "{\"model\": \"" + model + "\", \"messages\": " + message + ", \"max_tokens\": " + maxTokens + "}";
        String requestBody = "{\"model\": \"" + model + "\", \"messages\": " + userMessage + ", \"max_tokens\": " + maxTokens + "}";
        System.err.println(requestBody);
        HttpEntity<String> requestEntity = new HttpEntity<>(requestBody, headers);
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            String response = restTemplate.postForObject(url, requestEntity, String.class);
            JsonNode jsonNode = objectMapper.readTree(response);
            //Todo fix json from personaChoice Button inputs...
            String messageContent = jsonNode.get("choices").get(0).get("message").get("content").asText();
            //System.out.println(messageContent);
            return messageContent;
        } catch (Exception e) {
            // Handle the error here
            e.printStackTrace();
        }
        return "err";
    }
}

