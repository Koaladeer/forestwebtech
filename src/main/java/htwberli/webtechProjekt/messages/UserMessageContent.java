package htwberli.webtechProjekt.messages;

import com.fasterxml.jackson.annotation.JsonProperty;

public class UserMessageContent {
    @JsonProperty("id")
    private long id;
    @JsonProperty("content")
    private String message;
    @JsonProperty("from")
    private String from;

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
