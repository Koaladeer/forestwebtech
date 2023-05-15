package htwberli.webtechProjekt;

import htwberli.webtechProjekt.messages.AIMessageEntity;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AIMessageRepo extends CrudRepository<AIMessageEntity, Long> {

}
