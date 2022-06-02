package com.tweetapp.case0205.repository;

import com.tweetapp.case0205.model.Reply;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface ReplyRepo extends MongoRepository<Reply,String> {
}
