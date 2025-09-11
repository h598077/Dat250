package com.example.demo.object;

import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;

public class VoteOption {

	private String caption;
	private int presentationOrder;

    @JsonManagedReference("has")
	private List<Vote> vote = new ArrayList<>();
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
	
	
	
}
