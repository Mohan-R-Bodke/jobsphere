package JobSphere.service;

import org.springframework.stereotype.Service;

import JobSphere.entity.Application;
import JobSphere.entity.Job;
import JobSphere.entity.User;
import JobSphere.repository.ApplicationRepository;
import JobSphere.repository.CandidateProfileRepository;
import JobSphere.repository.JobRepository;
import JobSphere.repository.RecruiterProfileRepository;
import JobSphere.repository.UserRepository;

@Service
public class AdminService {

    private final UserRepository userRepository;
    private final JobRepository jobRepository;
    private final ApplicationRepository applicationRepository;
    private final CandidateProfileRepository candidateProfileRepository;
    private final RecruiterProfileRepository recruiterProfileRepository;

    public AdminService(
            UserRepository userRepository,
            JobRepository jobRepository,
            ApplicationRepository applicationRepository,
            CandidateProfileRepository candidateProfileRepository,
            RecruiterProfileRepository recruiterProfileRepository) {

        this.userRepository = userRepository;
        this.jobRepository = jobRepository;
        this.applicationRepository = applicationRepository;
        this.candidateProfileRepository = candidateProfileRepository;
        this.recruiterProfileRepository = recruiterProfileRepository;
    }

    public void deleteUser(Long userId) {

        User user = userRepository.findById(userId)
                .orElseThrow(() ->
                        new RuntimeException("User not found"));

        if (applicationRepository.existsByCandidate(user)) {
            throw new RuntimeException(
                    "Cannot delete user because applications exist"
            );
        }

        if (!jobRepository.findByRecruiter(user).isEmpty()) {
            throw new RuntimeException(
                    "Cannot delete recruiter because jobs exist"
            );
        }

        candidateProfileRepository.deleteByUser(user);
        recruiterProfileRepository.deleteByUser(user);

        userRepository.delete(user);
    }

    public void deleteJob(Long jobId) {

        Job job = jobRepository.findById(jobId)
                .orElseThrow(() ->
                        new RuntimeException("Job not found"));

        if (applicationRepository.existsByJob(job)) {
            throw new RuntimeException(
                    "Cannot delete job because applications exist"
            );
        }

        jobRepository.delete(job);
    }

    public void deleteApplication(Long applicationId) {

        Application application = applicationRepository
                .findById(applicationId)
                .orElseThrow(() ->
                        new RuntimeException("Application not found"));

        applicationRepository.delete(application);
    }
}