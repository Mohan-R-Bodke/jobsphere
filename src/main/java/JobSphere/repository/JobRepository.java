package JobSphere.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import JobSphere.entity.Job;
import JobSphere.entity.User;

public interface JobRepository extends JpaRepository<Job, Long> {

    List<Job> findByRecruiter(User recruiter);

    Optional<Job> findByIdAndRecruiter(Long id, User recruiter);

    @Query("""
        SELECT j FROM Job j
        WHERE (:title IS NULL OR LOWER(j.title) LIKE LOWER(CONCAT('%', :title, '%')))
        AND (:location IS NULL OR LOWER(j.location) LIKE LOWER(CONCAT('%', :location, '%')))
        AND (:company IS NULL OR LOWER(j.company) LIKE LOWER(CONCAT('%', :company, '%')))
        AND (:jobType IS NULL OR LOWER(j.jobType) LIKE LOWER(CONCAT('%', :jobType, '%')))
        """)
    List<Job> searchJobs(
            @Param("title") String title,
            @Param("location") String location,
            @Param("company") String company,
            @Param("jobType") String jobType
    );
}