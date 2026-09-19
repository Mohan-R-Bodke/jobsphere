package JobSphere.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import JobSphere.entity.RecruiterProfile;
import JobSphere.entity.User;

public interface RecruiterProfileRepository
        extends JpaRepository<RecruiterProfile, Long> {

    Optional<RecruiterProfile> findByUser(User user);

    boolean existsByUser(User user);

    void deleteByUser(User user);
}