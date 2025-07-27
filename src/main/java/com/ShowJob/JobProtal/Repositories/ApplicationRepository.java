package com.ShowJob.JobProtal.Repositories;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.ShowJob.JobProtal.Entity.Application;
import com.ShowJob.JobProtal.Entity.User;

@Repository
public interface ApplicationRepository extends JpaRepository<Application, Long> {
    List<Application> findByApplicant(User user);
}
