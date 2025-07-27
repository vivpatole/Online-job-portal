package com.ShowJob.JobProtal.Repositories;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.ShowJob.JobProtal.Entity.Job;
import com.ShowJob.JobProtal.Entity.User;

@Repository
public interface JobRepository extends JpaRepository<Job, Long> {
    List<Job> findByPostedBy(User user);

	
}
