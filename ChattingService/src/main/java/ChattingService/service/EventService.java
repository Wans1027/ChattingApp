package ChattingService.service;


import ChattingService.entity.Message;
import ChattingService.exception.RestException;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestClientResponseException;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class EventService {
    //mongoTemplate

   /* private final MongoTemplate mongoTemplate;

    public Message getEvent(String _id) {
        Message event = mongoTemplate.findById(_id, Message.class);
        return Optional.ofNullable(event).orElseThrow(() -> new RestException( HttpStatus.NOT_FOUND,"Not found event"));
    }

    public List<Message> getEventList(Long roomId) {
        Query query = new Query().addCriteria(Criteria.where("roomId").is(roomId));
        return mongoTemplate.find(query, Message.class);
    }

    public Message insertEvent(Message body) {
        return mongoTemplate.insert(body);
    }*/

}