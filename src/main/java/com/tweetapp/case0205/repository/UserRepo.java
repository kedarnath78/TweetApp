package com.tweetapp.case0205.repository;

import com.tweetapp.case0205.model.User;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

public interface UserRepo extends MongoRepository<User,String> {
    @Query("{'userName': ?0, 'password': ?1}")
    User findUserByUserNameAndPassword(String username, String password);

    @Query("{'_id': ?0}")
    User findUserByUserId(String userid);

}
