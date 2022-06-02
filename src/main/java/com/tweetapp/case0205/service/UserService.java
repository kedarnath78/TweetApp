package com.tweetapp.case0205.service;

import com.tweetapp.case0205.model.Reply;
import com.tweetapp.case0205.model.Tweet;
import com.tweetapp.case0205.model.User;

import java.util.List;
import java.util.Optional;


public interface UserService {
    public User registerUser(User user);

    List<User> listUsers();

    List<User> findByUsername(String username);

    User login(String username, String password);

    String forgotPassword(String userid, String password);

    Tweet postTweet(String tweet, String username);

    List<Tweet> getAllTweets();

    List<Tweet> getAllTweetsOfUser(String username);

    Tweet updateTweet(String id, String content);

    String deleteTweet(String id);

    Tweet likeTweet(String id);

    Reply postTweetReply(String tweetid, String username, String tweetreply);

    List<Reply> getAllReplies();
}
