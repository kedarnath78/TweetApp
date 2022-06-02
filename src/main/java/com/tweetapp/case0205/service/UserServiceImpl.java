package com.tweetapp.case0205.service;

import com.tweetapp.case0205.model.Reply;
import com.tweetapp.case0205.model.Tweet;
import com.tweetapp.case0205.model.User;
import com.tweetapp.case0205.repository.ReplyRepo;
import com.tweetapp.case0205.repository.TweetRepo;
import com.tweetapp.case0205.repository.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class UserServiceImpl implements UserService{

    @Autowired
    private UserRepo userRepo;

    @Autowired
    private TweetRepo tweetRepo;

    @Autowired
    private ReplyRepo replyRepo;

    @Override
    public User registerUser(User user) {
        User newUser = new User();
        try{
            if(user!=null){
                newUser = userRepo.save(user);
            }
        } catch(Exception e){
            e.printStackTrace();
        }
        return newUser;
    }

    //all the registered user
    public List<User> listUsers(){
        return userRepo.findAll();
    }

    //username as input -> list of users with that username as output
    @Override
    public List<User> findByUsername(String username) {
        List<User> users = userRepo.findAll();
        List<User> usersByUname = new ArrayList<>();
        for (int i = 0; i < users.size(); i++) {
            if ((users.get(i).getUserName()).equals(username)) {
                User reqUser=users.get(i);
                usersByUname.add(reqUser);
            }
        }
        return usersByUname;
    }

    //username & password as input -> success if credentials are right
    @Override
    public User login(String username, String password) {
        User user;
        try{
            user = userRepo.findUserByUserNameAndPassword(username,password);
            System.out.println(user.getEmail());
            return user;
        } catch (Exception e){
            e.printStackTrace();
        }
        return null;
    }

    //user id as input -> changes the password of that particular user
    @Override
    public String forgotPassword(String userid,String password) {
        if(userid!=null && password!=null) {
            try {
                User user = userRepo.findUserByUserId(userid);
                user.setPassword(password);
                System.out.println(user.getUserName()+" "+user.getPassword());
                userRepo.save(user);
                return "Password updated successfully";
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return "insufficient data";
    }

    //any authorised user after login can post a tweet
    // tweet content and username as input -> the content is added to list of tweets
    @Override
    public Tweet postTweet(String tweet, String username) {
        Tweet newTweet = new Tweet();
        newTweet.setTweetContent(tweet);
        newTweet.setUsername(username);
        int max=10000;
        int min = 100;
        String tweetid = "T" + Integer.toString((int)(Math.random()*(max-min+1)+min));
        newTweet.setTweetId(tweetid);
        LocalDateTime current =  LocalDateTime.now();
        newTweet.setPostTime(current);
        tweetRepo.save(newTweet);
        return newTweet;
    }

    //all tweets
    @Override
    public List<Tweet> getAllTweets() {
        return tweetRepo.findAll();
    }

    // all tweets of particular user
    @Override
    public List<Tweet> getAllTweetsOfUser(String username) {
        List<Tweet> newTweet = tweetRepo.getTweetsByUsername(username);
        return newTweet;
    }

    //logged in user can update tweet by providing the content
    @Override
    public Tweet updateTweet(String id, String content) {
        Tweet updateTweet = tweetRepo.findTweetById(id);
        updateTweet.setTweetContent(content);
        tweetRepo.save(updateTweet);
        return updateTweet;
    }

    //delete a particular tweet
    @Override
    public String deleteTweet(String id) {
        Tweet deleteTweet = tweetRepo.findTweetById(id);
        String ret_str;
        if(deleteTweet!=null){
            tweetRepo.deleteById(id);
            ret_str = "deleted tweet with id "+id;
        }
        else
            ret_str = "no tweet found with id "+id;
        return ret_str;
    }

   @Override
    public Tweet likeTweet(String id) {
        Tweet likeTweet = tweetRepo.findTweetById(id);
        String ret_str= " ";
        System.out.println(likeTweet);
        if(likeTweet!=null) {
            likeTweet.setLike(1);
            tweetRepo.save(likeTweet);
            //ret_str = "you liked a tweet with id: "+id+" value: "+likeTweet.getLike();
        }
        else
            ret_str = "not a valid tweet";
        return likeTweet;

    }

    //reply to tweet
    @Override
    public Reply postTweetReply(String tweetid, String username, String tweetreply) {
        Reply newReply = new Reply();
        newReply.setTweetId(tweetid);
        newReply.setUsername(username);
        newReply.setReplyContent(tweetreply);
        LocalDateTime current =  LocalDateTime.now();
        newReply.setReplyPostTime(current);
        int max=10000;
        int min = 100;
        String replyid = "Rep" + Integer.toString((int)(Math.random()*(max-min+1)+min));
        newReply.setReplyId(replyid);
        replyRepo.save(newReply);
        return newReply;
    }

    // all replies
    @Override
    public List<Reply> getAllReplies() {
        return replyRepo.findAll();
    }
}
