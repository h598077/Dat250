package com.example.demo.object;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
@Entity
@Table(name = "polls")
public class Poll {
	 @Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int pid;
	
	private String question;
	private Instant publishedAt;
	private Instant validUntil;
	@ManyToOne
	 @JoinColumn(name = "created_by_user")
	 @JsonBackReference("creates")
	private User createdBy;
	
	@OneToMany(mappedBy = "poll", cascade = CascadeType.ALL, orphanRemoval = true)
	 @JsonManagedReference("contains")
	private List<VoteOption> options  = new ArrayList<>();
	
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
		return createdBy;
	}
	public void setUser(User user) {
		this.createdBy = user;
	}
	public List<VoteOption> getVoteoption() {
		return options;
	}
	public void setVoteoption(List<VoteOption> voteoption) {
		this.options = voteoption;
	}
	public Poll() {
	}
	
	 public Poll(String question) {
		this.question=question;
	}
	 /**
    *
    * Adds a new option to this Poll and returns the respective
    * VoteOption object with the given caption.
    * The value of the presentationOrder field gets determined
    * by the size of the currently existing VoteOptions for this Poll.
    * I.e. the first added VoteOption has presentationOrder=0, the secondly
    * registered VoteOption has presentationOrder=1 ans so on.
    */
   public VoteOption addVoteOption(String caption) {
        List<VoteOption> options = this.getVoteoption();
        int size =  options.size();
        VoteOption voteoption= new VoteOption(caption);
        voteoption.setPresentationOrder(size);
        voteoption.setPoll(this);
        options.add(voteoption);
        return voteoption;
   }
}
