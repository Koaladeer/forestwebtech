package htwberli.webtechProjekt.messages;

import com.fasterxml.jackson.annotation.JsonProperty;

public class UserMessage {
    @JsonProperty("message")
    private UserMessageContent content;
    public UserMessageContent getContent() {
        return content;
    }
    public void setContent(UserMessageContent content) {
        this.content = content;
    }
}

