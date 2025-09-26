package com.example.demo;

import java.time.Instant;
import java.util.*;
import java.util.stream.Collectors;

import org.springframework.stereotype.Component;

import com.example.demo.object.Poll;
import com.example.demo.object.User;
import com.example.demo.object.Vote;
import com.example.demo.object.VoteOption;

import redis.clients.jedis.Jedis;

@Component
public class DomainManager {

	private final Map<String , User> users = new HashMap<>();
	private final Map<String, Poll> polls = new HashMap<>();
	
	
	private static final String REDIS_HOST = "localhost";
    private static final int REDIS_PORT = 6379;
    private static final int CACHE_TTL_SECONDS = 300; // 5 minutes
	
	
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
        // Create new vote
		Vote vote = new Vote();
		vote.setPublishedAt(Instant.now());
		vote.setVotesOn(option);
        vote.setUser(user);

		user.getVote().add(vote);

        option.getVote().add(vote);
        
        
     // --- Update Redis cache ---
        String key = "poll:" + poll.getPid() + ":votes";
        String field = "option:" + option.getPresentationOrder(); 

        try (Jedis jedis = new Jedis(REDIS_HOST, REDIS_PORT)) {
            // Increment the count for this option
            jedis.hincrBy(key, field, 1);

            // Set TTL to ensure cache expires after some time
            jedis.expire(key, CACHE_TTL_SECONDS);
        }
        
        
        
		return vote;
		
	}
	
	public Map<Integer, Long> getPollVotes(Poll poll) {
	    String key = "poll:" + poll.getPid() + ":votes";

	    try (Jedis jedis = new Jedis(REDIS_HOST, REDIS_PORT)) {
	        Map<String, String> cached = jedis.hgetAll(key);

	        if (!cached.isEmpty()) {
	            // Cache hit → convert fields to Integer vote counts
	            return cached.entrySet().stream()
	                    .collect(Collectors.toMap(
	                            e -> Integer.parseInt(e.getKey().replace("option:", "")),
	                            e -> Long.parseLong(e.getValue())
	                    ));
	        }

	        // If not cached, compute from DB
	        Map<Integer, Long> aggregatedVotes = new HashMap<>();
	        for (VoteOption option : poll.getOptions()) {
	            aggregatedVotes.put(option.getPresentationOrder(), (long) option.getVote().size());
	        }
	        
	        // Store in Redis
	        Map<String, String> toCache = aggregatedVotes.entrySet().stream()
	                .collect(Collectors.toMap(
	                        e -> "option:" + e.getKey(),
	                        e -> String.valueOf(e.getValue())
	                ));

	        jedis.hset(key, toCache);
	        jedis.expire(key, CACHE_TTL_SECONDS);

	        return aggregatedVotes;
	    }
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
