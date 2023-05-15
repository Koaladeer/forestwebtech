package htwberli.webtechProjekt.messages;

import com.fasterxml.jackson.annotation.JsonProperty;

public class AIMessage {
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @JsonProperty("id")
    private Long id;
    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    @JsonProperty("content")
    private String content;
   /*
    @JsonProperty("object")
    private String object;
    @JsonProperty("created")
    private long created;
    @JsonProperty("model")
    private String model;
    @JsonProperty("usage")
    private Usage usage;
    @JsonProperty("choices")
    private Choice choices;


    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getObject() {
        return object;
    }

    public void setObject(String object) {
        this.object = object;
    }

    public long getCreated() {
        return created;
    }

    public void setCreated(long created) {
        this.created = created;
    }

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public Usage getUsage() {
        return usage;
    }

    public void setUsage(Usage usage) {
        this.usage = usage;
    }

    public Choice getChoices() {
        return choices;
    }

    public void setChoices(Choice choices) {
        this.choices = choices;
    }*/
}


