package com.example.demo.object;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;

public class Poll {

	private String question;
	private Instant publishedAt;
	private Instant validUntil;
	 @JsonBackReference("creates")
	private User user;
	 @JsonManagedReference("contains")
	private List<VoteOption> voteoption = new ArrayList<>();
	
	public String getQuestion() {
		return question;
	}
	public void setQuestion(String question) {
		this.question = question;
	}
	public Instant getPublishedAt() {
		return publishedAt;
	}
	public void setPublishedAt(Instant publishedAt) {
		this.publishedAt = publishedAt;
	}
	public Instant getValidUntil() {
		return validUntil;
	}
	public void setValidUntil(Instant validUntil) {
		this.validUntil = validUntil;
	}
	
	public User getUser() {
		return user;
	}
	public void setUser(User user) {
		this.user = user;
	}
	public List<VoteOption> getVoteoption() {
		return voteoption;
	}
	public void setVoteoption(List<VoteOption> voteoption) {
		this.voteoption = voteoption;
	}
	public Poll() {
	}
	
	
}
