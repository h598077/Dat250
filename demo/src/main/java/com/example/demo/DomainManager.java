package com.example.demo;

import java.time.Instant;
import java.util.*;

import org.springframework.stereotype.Component;

import com.example.demo.object.Poll;
import com.example.demo.object.User;
import com.example.demo.object.Vote;
import com.example.demo.object.VoteOption;

@Component
public class DomainManager {

	private final Map<String , User> users = new HashMap<>();
	private final Map<String, Poll> polls = new HashMap<>();
	
	
	
	
	
	public User createUser(String username, String email) {
		User user = new User();
		user.setUsername(username);
		user.setEmail(email);
		users.put(username, user);
		return user;
	}
	
	public Collection<User> listUsers() {
        return users.values();
    }
	
	
	
	public Poll createPoll(User user, String question, Instant validUntil, List<String> option) {
		Poll poll = new Poll();
		poll.setCreatedBy(user);
		poll.setQuestion(question);
		poll.setPublishedAt(Instant.now());
		poll.setValidUntil(validUntil);
		List<VoteOption> voteoptionlist = new ArrayList<>();
		for(int i=0;i<option.size();i++) {
			VoteOption voteoption = new VoteOption();
			voteoption.setCaption(option.get(i));
			voteoption.setPresentationOrder(i+1);
			voteoption.setPoll(poll);
			voteoptionlist.add(voteoption);
		}
		poll.setOptions(voteoptionlist);
		user.getPoll().add(poll);
		polls.put(question, poll);
		return poll;
		
	}
	
	 public Collection<Poll> listPolls() {
	        return polls.values();
	    }
	
	public Vote vote(User user, Poll poll, VoteOption option) {

        // Find any existing vote by this user in this poll
        Optional<Vote> existingVote = user.getVote().stream()
                .filter(v -> poll.getOptions().contains(v.getVotesOn()))
                .findFirst();

        // Remove it if present
        existingVote.ifPresent(v -> {
            user.getVote().remove(v);
            v.getVotesOn().getVote().remove(v);
        });

		Vote vote = new Vote();
		vote.setPublishedAt(Instant.now());
		vote.setVotesOn(option);
        vote.setUser(user);

		user.getVote().add(vote);

        option.getVote().add(vote);
		return vote;
		
	}
	  public Collection<Vote> listVotes(User user) {
	        return user.getVote();
	    }
	
	public void deletePoll(Poll poll) {
		polls.remove(poll.getQuestion());
		users.values().forEach(u -> u.getPoll().remove(poll));
		users.values().forEach(u -> u.getVote().removeIf(v -> poll.getOptions().contains(v.getVotesOn())));
	}
	
}
