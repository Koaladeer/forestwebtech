package htwberli.webtechProjekt.messages;

import com.fasterxml.jackson.annotation.JsonProperty;

public class AIMessage {
    @JsonProperty("sId")
    private Long sId;
    @JsonProperty("id")
    private Long id;
    @JsonProperty("content")
    private String content;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getsId() {
        return sId;
    }

    public void setsId(Long sId) {
        this.sId = sId;
    }


    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }


}


