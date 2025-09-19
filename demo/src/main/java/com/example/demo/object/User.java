package com.example.demo.object;

import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonManagedReference;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;

@Entity
@Table(name = "users")
public class User {
	 @Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	
	private int id;
	
	private String username;
	private String email;
	 @OneToMany
    @JsonManagedReference("voted")
	private List<Vote> vote = new ArrayList<>();
    @OneToMany
	 @JsonManagedReference("creates")
	private List<Poll> poll = new ArrayList<>();
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public User() {

	}
	public List<Vote> getVote() {
		return vote;
	}
	public void setVote(List<Vote> vote) {
		this.vote = vote;
	}
	public List<Poll> getPoll() {
		return poll;
	}
	public void setPoll(List<Poll> poll) {
		this.poll = poll;
	}
	
	 /**
     * Creates a new User object with given username and email.
     * The id of a new user object gets determined by the database.
     */
    public User(String username, String email) {
        this.username = username;
        this.email = email;
        this.poll= new ArrayList<>();
       
    }

    /**
     * Creates a new Poll object for this user
     * with the given poll question
     * and returns it.
     */
    public Poll createPoll(String question) {
        // TODO: implement
    	Poll poll2 = new Poll(question);
    	poll.add(poll2);
    	poll2.setUser(this);
    	return poll2;
    }

    /**
     * Creates a new Vote for a given VoteOption in a Poll
     * and returns the Vote as an object.
     */
    public Vote voteFor(VoteOption option) {
       Vote vote = new Vote(option);
       vote.setUser(this);
       return vote;
       
    }
}
