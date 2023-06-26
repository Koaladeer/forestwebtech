package htwberli.webtechProjekt.messages;

import com.fasterxml.jackson.annotation.JsonProperty;

public class UserMessage {
    //todo ChatMessage oder UserMessage Content nutzen nicht beides...
    @JsonProperty("message")
    private ChatMessage content;

    public ChatMessage getContent() {
        return content;
    }

    public void setContent(ChatMessage content) {
        this.content = content;
    }
}

