package com.tweetapp.case0205.controller;

import com.tweetapp.case0205.model.Reply;
import com.tweetapp.case0205.model.Tweet;
import com.tweetapp.case0205.model.User;
import com.tweetapp.case0205.repository.UserRepo;
import com.tweetapp.case0205.service.UserService;
import com.tweetapp.case0205.service.UserServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;


@RestController
@CrossOrigin(origins = "http://localhost:3000")
//@RequestMapping("/api/v1.0/tweets")
public class UserController {

    @Autowired
    private UserRepo userRepo;

    @Autowired
    private UserService userService;

    //User Registration
    @PostMapping("/register")
    public User registration(@RequestBody User user){
        userService.registerUser(user);
        return user;
    }

    //User Login
    @PostMapping("/login")
    public String login(@RequestBody Map<String,Object> user){
        //System.out.println("abcdu"+user);
        User user1 = userService.login((String)user.get("username"),(String)user.get("password"));
        if(user1 != null){
            String username = user1.getUserName();
            return username+" successfully logged in";
        }
        else{
            return "no such user";
        }
    }

    //Get all users
    @GetMapping("/users/all")
    public List<User> getUsers(){
        return userService.listUsers();
    }

    //Get user by Username
    @GetMapping("/user/search/{username}")
    public List<User> findByUsername(@PathVariable String username){
        return userService.findByUsername(username);
    }

    //Forgot Password
    @PostMapping("/forgot")
    public String forgotPassword(@RequestBody Map<String,String> credentials){
        //System.out.println(credentials);
        return userService.forgotPassword(credentials.get("userid"),credentials.get("password"));
    }

    //Post a tweet
    @PostMapping("/addTweet")
    public Tweet newTweet(@RequestBody Map<String,String> addTweet){
        return userService.postTweet(addTweet.get("tweet"),addTweet.get("username"));
    }

    //get all tweets
    @GetMapping("/allTweets")
    public List<Tweet> listTweets(){
        return userService.getAllTweets();
    }

    //get all tweets of a user
    @GetMapping("/allTweetsOfUser")
    public List<Tweet> listTweetsOfaUser(@RequestBody String username){
        return userService.getAllTweetsOfUser(username);
    }

    //update tweet
    @PutMapping("/updateTweet")
    public Tweet updateTweet(@RequestBody Map<String,String> newTweet){
        return userService.updateTweet(newTweet.get("id"),newTweet.get("content"));
    }

    //Delete tweet
    @DeleteMapping ("/deleteTweet")
    public String deleteTweet(@RequestBody String id){
        return userService.deleteTweet(id);
    }

    //like tweet
    @PutMapping("/likeTweet")
     public Tweet likeTweet(@RequestBody String id){
        return userService.likeTweet(id);
    }


    //Reply to a tweet
    @PostMapping("/replyTweet")
    public Reply replyTweet(@RequestBody Map<String,String> reply){
        return userService.postTweetReply(reply.get("tweetid"),reply.get("username"),reply.get("tweetreply"));
    }

    //Get all replies
    @GetMapping("/getAllReplies")
    public List<Reply> listReplies(){
        return userService.getAllReplies();
    }
}
