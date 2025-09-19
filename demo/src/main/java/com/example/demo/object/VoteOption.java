package com.example.demo.object;

import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
@Entity
@Table(name = "vote_options")
public class VoteOption {

	 @Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int voteopid;
	private String caption;
	private int presentationOrder;

	@OneToMany
	  @JsonManagedReference("has")
	private List<Vote> vote = new ArrayList<>();
  
	@ManyToOne
    @JoinColumn
    @JsonBackReference("contains")
	private Poll poll;
	
	public String getCaption() {
		return caption;
	}
	public void setCaption(String caption) {
		this.caption = caption;
	}
	public int getPresentationOrder() {
		return presentationOrder;
	}
	public void setPresentationOrder(int presentationOrder) {
		this.presentationOrder = presentationOrder;
	}
	
	public List<Vote> getVote() {
		return vote;
	}
	public void setVote(List<Vote> vote) {
		this.vote = vote;
	}
	public Poll getPoll() {
		return poll;
	}
	public void setPoll(Poll poll) {
		this.poll = poll;
	}
	public VoteOption() {
;
	}
	public VoteOption(String caption) {
		this.caption=caption;
	}
	
	
	
}
