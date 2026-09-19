package JobSphere.service;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.stereotype.Service;

import JobSphere.entity.Application;
import JobSphere.entity.ApplicationStatus;
import JobSphere.entity.Job;
import JobSphere.entity.Role;
import JobSphere.entity.User;
import JobSphere.repository.ApplicationRepository;
import JobSphere.repository.JobRepository;
import JobSphere.repository.UserRepository;

@Service
public class ApplicationService {

    private final ApplicationRepository applicationRepository;
    private final UserRepository userRepository;
    private final JobRepository jobRepository;

    public ApplicationService(
            ApplicationRepository applicationRepository,
            UserRepository userRepository,
            JobRepository jobRepository) {

        this.applicationRepository = applicationRepository;
        this.userRepository = userRepository;
        this.jobRepository = jobRepository;
    }

    public Application applyForJob(String email, Long jobId) {

    User candidate = userRepository.findByEmail(email)
            .orElseThrow(() ->
                    new RuntimeException("Candidate not found"));

    if (candidate.getRole() != Role.CANDIDATE) {
        throw new RuntimeException(
                "Only candidates can apply for jobs");
    }

    Job job = jobRepository.findById(jobId)
            .orElseThrow(() ->
                    new RuntimeException("Job not found"));

    if (applicationRepository
            .existsByCandidateAndJob(candidate, job)) {

        throw new RuntimeException(
                "You have already applied for this job");
    }

    Application application = new Application();

    application.setCandidate(candidate);
    application.setJob(job);
    application.setStatus(ApplicationStatus.APPLIED);
    application.setAppliedAt(LocalDateTime.now());

    return applicationRepository.save(application);
}


    public List<Application> getApplicationsByCandidate(
        String email) {

    User candidate = userRepository.findByEmail(email)
            .orElseThrow(() ->
                    new RuntimeException("Candidate not found"));

    if (candidate.getRole() != Role.CANDIDATE) {
        throw new RuntimeException(
                "Only candidates can view applications");
    }

    return applicationRepository.findByCandidate(candidate);
}

    public List<Application> getApplicationsByJob(
        Long jobId,
        String email) {

    User recruiter = userRepository.findByEmail(email)
            .orElseThrow(() ->
                    new RuntimeException("Recruiter not found"));

    if (recruiter.getRole() != Role.RECRUITER) {
        throw new RuntimeException(
                "Only recruiters can view job applications");
    }

    Job job = jobRepository
            .findByIdAndRecruiter(jobId, recruiter)
            .orElseThrow(() ->
                    new RuntimeException(
                            "Job not found or you are not the owner"));

    return applicationRepository.findByJob(job);
}

public List<Application> getAllApplications() {
    return applicationRepository.findAll();
}

    public Application updateApplicationStatus(
        Long applicationId,
        ApplicationStatus status,
        String email) {

    User recruiter = userRepository.findByEmail(email)
            .orElseThrow(() ->
                    new RuntimeException("Recruiter not found"));

    if (recruiter.getRole() != Role.RECRUITER) {
        throw new RuntimeException(
                "Only recruiters can update application status");
    }

    Application application =
            applicationRepository.findById(applicationId)
                    .orElseThrow(() ->
                            new RuntimeException(
                                    "Application not found"));

    Job job = application.getJob();

    if (!job.getRecruiter().getId().equals(recruiter.getId())) {
        throw new RuntimeException(
                "You are not the owner of this job");
    }

    application.setStatus(status);

    return applicationRepository.save(application);
}
}