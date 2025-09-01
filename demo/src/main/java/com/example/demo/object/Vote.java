package com.example.demo.object;

import java.time.Instant;

public class Vote {

	private Instant publishedAt;
	private User user;
	private VoteOption voteoption;

	public Instant getPublishedAt() {
		return publishedAt;
	}

	public void setPublishedAt(Instant publishedAt) {
		this.publishedAt = publishedAt;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public VoteOption getVoteoption() {
		return voteoption;
	}

	public void setVoteoption(VoteOption voteoption) {
		this.voteoption = voteoption;
	}

	public Vote() {
	}
	
	
	
}
