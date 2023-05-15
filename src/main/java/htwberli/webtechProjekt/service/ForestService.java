package htwberli.webtechProjekt.service;

import htwberli.webtechProjekt.AIMessageRepo;
import htwberli.webtechProjekt.ChatMessagesRepo;
import htwberli.webtechProjekt.messages.AIMessageEntity;
import htwberli.webtechProjekt.messages.ChatMessage;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

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
        List<ChatMessage> messages = new ArrayList<ChatMessage>();
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

}

