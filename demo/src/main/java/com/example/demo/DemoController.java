package com.example.demo;

import java.time.Instant;
import java.util.Collection;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.object.Poll;
import com.example.demo.object.User;
import com.example.demo.object.Vote;
import com.example.demo.object.VoteOption;

@RestController
public class DemoController {
	
	
	private final DomainManager domainmanager;
	
	public DemoController(@Autowired DomainManager domain) {
		this.domainmanager=domain;
	}
	
	
	
	@GetMapping("/")
    public String hello() {
      return "Hello World";
    }
	
	
	@PostMapping("/users")
    public User createUser(@RequestParam("username")  String username, @RequestParam("email") String email) {
        return domainmanager.createUser(username, email);
    }
	
	 @GetMapping("/users")
	    public Collection<User> listUsers() {
	        return domainmanager.listUsers();
	    }
	 
	
	 
	 
	 
	 
	 
	 @GetMapping("/polls")
	    public Collection<Poll> listPolls() {
	        return domainmanager.listPolls();
	    }
	 
	 
	 
	 @PostMapping("/polls")
	    public Poll createPoll(@RequestParam("username") String username,
	                           @RequestParam("question") String question,
	                           @RequestParam ("validuntil")long validUntil,
	                           @RequestBody  List<String> options) {
		 	
	        User user = domainmanager.listUsers().stream().filter(u -> u.getUsername().equals(username)).findFirst().orElseThrow();
	        return domainmanager.createPoll(user, question, Instant.ofEpochMilli(validUntil), options);
	    }
	 
	 
	 @GetMapping("/votes")
	    public Collection<Vote> listVotes(@RequestParam("username") String username) {
	        User user = domainmanager.listUsers().stream().filter(u -> u.getUsername().equals(username)).findFirst().orElseThrow();
	        return domainmanager.listVotes(user);
	    }
	 
	 @PostMapping("/votes")
	    public Vote vote(@RequestParam("username") String username,
	                     @RequestParam("pollquestion") String pollQuestion,
	                     @RequestParam("optionindex") int optionIndex) {
	        User user = domainmanager.listUsers().stream().filter(u -> u.getUsername().equals(username)).findFirst().orElseThrow();
	        Poll poll = domainmanager.listPolls().stream().filter(p -> p.getQuestion().equals(pollQuestion)).findFirst().orElseThrow();
	        VoteOption option = poll.getVoteoption().get(optionIndex);
	        return domainmanager.vote(user, poll, option);
	    }
	 
	 @DeleteMapping("/polls")
	    public void deletePoll(@RequestParam("question") String question) {
	        Optional<Poll> poll = domainmanager.listPolls().stream().filter(p -> p.getQuestion().equals(question)).findFirst();
	        if(!poll.isEmpty()) {
	        	domainmanager.deletePoll(poll.get());
	        }
	        
	    }
}
