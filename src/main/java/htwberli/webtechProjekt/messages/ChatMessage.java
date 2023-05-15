package htwberli.webtechProjekt.messages;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;

@Entity
public class ChatMessage {

    @Id
    private long id;
    private String message;
    private String role;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }
}
