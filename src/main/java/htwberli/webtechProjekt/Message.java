package htwberli.webtechProjekt;

import com.fasterxml.jackson.annotation.JsonProperty;

public class Message {
    @JsonProperty("message")

    private MessageContent content;

    public MessageContent getContent() {
        return content;
    }

    public void setContent(MessageContent content) {
        this.content = content;
    }

}

    /*private String content;
    private String from;
    private int id;

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getFrom() {
        return from;
    }

    public void setFrom(String from) {
        this.from = from;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }*/

