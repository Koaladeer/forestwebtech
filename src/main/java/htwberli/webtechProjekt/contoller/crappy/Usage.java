package htwberli.webtechProjekt.contoller.crappy;

import com.fasterxml.jackson.annotation.JsonProperty;

public class Usage {
    public int getPrompt_tokens() {
        return prompt_tokens;
    }

    public void setPrompt_tokens(int prompt_tokens) {
        this.prompt_tokens = prompt_tokens;
    }

    public int getCompletion_tokens() {
        return completion_tokens;
    }

    public void setCompletion_tokens(int completion_tokens) {
        this.completion_tokens = completion_tokens;
    }

    public int getTotal_tokens() {
        return total_tokens;
    }

    public void setTotal_tokens(int total_tokens) {
        this.total_tokens = total_tokens;
    }

    @JsonProperty("promt_tokens")
    private int prompt_tokens;
    @JsonProperty("completion_tokens")
    private int completion_tokens;
    @JsonProperty("total_tokens")
    private int total_tokens;

    @Override
    public String toString() {
        return "Usage{" +
                "prompt_tokens=" + prompt_tokens +
                ", completion_tokens=" + completion_tokens +
                ", total_tokens=" + total_tokens +
                '}';
    }
}
