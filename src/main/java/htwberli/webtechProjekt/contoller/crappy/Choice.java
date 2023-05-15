package htwberli.webtechProjekt.contoller.crappy;

import com.fasterxml.jackson.annotation.JsonProperty;

public class Choice {
    @JsonProperty("message")
    private Message message;
    @JsonProperty("finish_reason")
    private String finish_reason;
    @JsonProperty("index")
    private int index;

    public Message getMessage() {
        return message;
    }

    public void setMessage(Message message) {
        this.message = message;
    }

    public String getFinish_reason() {
        return finish_reason;
    }

    public void setFinish_reason(String finish_reason) {
        this.finish_reason = finish_reason;
    }

    public int getIndex() {
        return index;
    }

    public void setIndex(int index) {
        this.index = index;
    }

    @Override
    public String toString() {
        return "Choice{" +
                "message=" + message +
                ", finish_reason='" + finish_reason + '\'' +
                ", index=" + index +
                '}';
    }
}
