package com.example.demo.object;

import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonManagedReference;

public class User {

	
	private String username;
	private String email;
    @JsonManagedReference
	private List<Vote> vote = new ArrayList<>();
	 @JsonManagedReference
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
	
	
}
