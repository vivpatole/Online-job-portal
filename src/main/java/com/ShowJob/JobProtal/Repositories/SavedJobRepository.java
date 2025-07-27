package com.ShowJob.JobProtal.Repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ShowJob.JobProtal.Entity.SaveJob;

public interface SavedJobRepository extends JpaRepository<SaveJob, Long> {

}
