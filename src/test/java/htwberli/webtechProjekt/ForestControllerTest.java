package htwberli.webtechProjekt;
import com.fasterxml.jackson.databind.ObjectMapper;
import htwberli.webtechProjekt.contoller.ForestController;
import htwberli.webtechProjekt.messages.AIMessage;
import htwberli.webtechProjekt.messages.AIMessageEntity;
import htwberli.webtechProjekt.messages.ChatMessage;
import htwberli.webtechProjekt.messages.UserMessage;
import htwberli.webtechProjekt.service.ForestService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
@WebMvcTest(ForestController.class)
public class ForestControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ForestService forestService;

    @Test
    public void testGreeting() throws Exception {
        mockMvc.perform(get("/greeting"))
                .andExpect(status().isOk())
                .andExpect(content().string("hello, World"));
    }
    @Test
    public void testGetAllMessages() throws Exception {
        List<AIMessageEntity> aiMessageEntities = new ArrayList<>();

        when(forestService.getAllAIMessages()).thenReturn(aiMessageEntities);

        mockMvc.perform(get("/messages"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").isArray());
    }
    @Test
    public void testCreateChatMessage() throws Exception {
        ChatMessage chatMessage = new ChatMessage();
        ChatMessage chatmessage = new ChatMessage();
        chatmessage.setContent("Hello");
        chatmessage.setId(1L);
        when(forestService.save(any(ChatMessage.class))).thenReturn(chatMessage);

        mockMvc.perform(post("/chatmessages")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(asJsonString(chatMessage)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(chatMessage.getId()));
    }
    @Test
    public void testDeleteAIMessageEntity() throws Exception {
        Long id = 1L;

        mockMvc.perform(delete("/messages/delete/{id}", id))
                .andExpect(status().isOk());
        verify(forestService, times(1)).deleteAIMessageByID(id);
    }
    @Test
    public void testChatResponse() throws Exception {
        UserMessage userMessage = new UserMessage();
        ChatMessage chatmessage = new ChatMessage();
        chatmessage.setContent("Hello");
        userMessage.setContent(chatmessage);

        String response = "Hello, how can I help you?";
        when(forestService.openAIConnection(any())).thenReturn(response);

        mockMvc.perform(post("/api/chat")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(asJsonString(userMessage)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.reply").value(response));

    }


    private String asJsonString(Object obj) {
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            return objectMapper.writeValueAsString(obj);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
