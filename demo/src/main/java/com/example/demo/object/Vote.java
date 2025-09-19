package com.example.demo.object;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

import java.time.Instant;

@Entity
@Table(name = "votes")
public class Vote {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int vid;
	private Instant publishedAt;
	
	@ManyToOne
	@JoinColumn
	 @JsonBackReference("voted")
	private User user;
	
	@ManyToOne
	@JoinColumn
	@JsonBackReference("has")
	private VoteOption votesOn;

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

	

	public VoteOption getVotesOn() {
		return votesOn;
	}

	public void setVotesOn(VoteOption votesOn) {
		this.votesOn = votesOn;
	}

	public Vote() {
	}

	public Vote(VoteOption option) {
		this.votesOn = option;
	}

}
