package com.example.demo;

import java.time.Instant;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import com.example.demo.object.Poll;
import com.example.demo.object.User;
import com.example.demo.object.Vote;
import com.example.demo.object.VoteOption;

@RestController
@CrossOrigin
public class DemoController {
	
	
	private final DomainManager domainmanager;
	
	public DemoController(@Autowired DomainManager domain) {
		this.domainmanager=domain;
	}
	
	@GetMapping("/polls/{pollId}/votes")
	public Map<Integer, Long> getPollVotes(@PathVariable("pollId") int pollId) {
	    Poll poll = domainmanager.listPolls().stream()
	            .filter(p -> p.getPid() == pollId)
	            .findFirst()
	            .orElseThrow();
	    return domainmanager.getPollVotes(poll);
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
	                     @RequestParam("optionindex") int optionIndex) throws Exception {
	        User user = domainmanager.listUsers().stream().filter(u -> u.getUsername().equals(username)).findFirst().orElseThrow();
	        Poll poll = domainmanager.listPolls().stream().filter(p -> p.getQuestion().equals(pollQuestion)).findFirst().orElseThrow();
	        VoteOption option = poll.getOptions().get(optionIndex);
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
