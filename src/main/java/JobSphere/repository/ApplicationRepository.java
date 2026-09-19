package JobSphere.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import JobSphere.entity.Application;
import JobSphere.entity.Job;
import JobSphere.entity.User;

public interface ApplicationRepository extends JpaRepository<Application, Long> {

    List<Application> findByCandidate(User candidate);

    List<Application> findByJob(Job job);

    boolean existsByCandidateAndJob(User candidate, Job job);

    boolean existsByCandidate(User candidate);

    boolean existsByJob(Job job);

    void deleteByCandidate(User candidate);

    void deleteByJob(Job job);
}