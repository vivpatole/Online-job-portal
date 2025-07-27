package com.ShowJob.JobProtal.Entity;

import java.util.Optional;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;

@Entity
public class SaveJob {
	
	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    private User user;

    @ManyToOne
    private Job job;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user2) {
		this.user = user2;
	}

	public Job getJob() {
		return job;
	}

	public void setJob(Job job) {
		this.job = job;
	}

	@Override
	public String toString() {
		return "SaveJob [id=" + id + ", user=" + user + ", job=" + job + "]";
	}

	public SaveJob(Long id, User user, Job job) {
		super();
		this.id = id;
		this.user = user;
		this.job = job;
	}

	public SaveJob() {
		super();
		
	}
    
    

}
