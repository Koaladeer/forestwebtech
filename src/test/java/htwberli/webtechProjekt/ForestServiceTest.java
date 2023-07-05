package htwberli.webtechProjekt;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import htwberli.webtechProjekt.AIMessageRepo;
import htwberli.webtechProjekt.ChatMessagesRepo;
import htwberli.webtechProjekt.messages.AIMessageEntity;
import htwberli.webtechProjekt.messages.ChatMessage;

import htwberli.webtechProjekt.messages.UserMessage;
import htwberli.webtechProjekt.service.ForestService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

import static junit.framework.TestCase.assertEquals;
import static org.mockito.Mockito.*;
@SpringBootTest
@ContextConfiguration(classes = ForestService.class)
public class ForestServiceTest {
    @MockBean
    AIMessageRepo aiMessageRepo;
    @Autowired
    ForestService forestService;
    @MockBean
    ChatMessagesRepo chatMessagesRepo;
    @Test
    @DisplayName("should find message by id")
    public void testGetAIMessageEntity() {
        Long id = 1L;
        AIMessageEntity aiMessageEntity = new AIMessageEntity();
        aiMessageEntity.setId(id);
        when(aiMessageRepo.findById(id)).thenReturn(java.util.Optional.of(aiMessageEntity));

        AIMessageEntity retrievedMessageEntity = forestService.get(id);

        assertEquals(aiMessageEntity, retrievedMessageEntity);
        verify(aiMessageRepo, times(1)).findById(id);
    }
    @Test
    public void testSaveChatMessage() {
        ChatMessage chatMessage = new ChatMessage();
        chatMessage.setContent("Hi");
        when(chatMessagesRepo.save(chatMessage)).thenReturn(chatMessage);

        ChatMessage savedMessage = forestService.save(chatMessage);
        assertEquals(chatMessage, savedMessage);
    }
    @Test
    public void testDeleteChatMessageById() {
        Long id = 1L;
        doNothing().when(chatMessagesRepo).deleteById(id);

        forestService.deleteChatMessagesByID(id);

        verify(chatMessagesRepo, times(1)).deleteById(id);
    }

    @Test
    public void testGetAllChatMessages() {
        List<ChatMessage> chatMessages = new ArrayList<>();
        when(chatMessagesRepo.findAll()).thenReturn(chatMessages);

        List<ChatMessage> retrievedMessages = forestService.getAllChatMessages();

        assertEquals(chatMessages, retrievedMessages);
        verify(chatMessagesRepo, times(1)).findAll();
    }

    @Test
    public void testSaveAIMessageEntity() {
        AIMessageEntity aiMessageEntity = new AIMessageEntity();
        when(aiMessageRepo.save(aiMessageEntity)).thenReturn(aiMessageEntity);

        AIMessageEntity savedMessageEntity = forestService.save(aiMessageEntity);

        assertEquals(aiMessageEntity, savedMessageEntity);
        verify(aiMessageRepo, times(1)).save(aiMessageEntity);
    }

    @Test
    public void testDeleteAIMessageEntityById() {
        Long id = 1L;
        doNothing().when(aiMessageRepo).deleteById(id);

        forestService.deleteAIMessageByID(id);

        verify(aiMessageRepo, times(1)).deleteById(id);
    }



    @Test
    public void testGetAllAIMessages() {
        List<AIMessageEntity> aiMessages = new ArrayList<>();
        when(aiMessageRepo.findAll()).thenReturn(aiMessages);

        List<AIMessageEntity> retrievedMessages = forestService.getAllAIMessages();

        assertEquals(aiMessages, retrievedMessages);
        verify(aiMessageRepo, times(1)).findAll();
    }
}
