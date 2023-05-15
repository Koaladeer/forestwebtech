package htwberli.webtechProjekt;

import htwberli.webtechProjekt.messages.ChatMessage;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ChatMessagesRepo extends CrudRepository<ChatMessage, Long> {

}
