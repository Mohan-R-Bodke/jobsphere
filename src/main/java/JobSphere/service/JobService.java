package JobSphere.service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import JobSphere.dto.JobRequest;
import JobSphere.entity.Job;
import JobSphere.entity.Role;
import JobSphere.entity.User;
import JobSphere.repository.ApplicationRepository;
import JobSphere.repository.JobRepository;

@Service
public class JobService {

    private final JobRepository jobRepository;
    private final ApplicationRepository applicationRepository;

    public JobService(JobRepository jobRepository,
                      ApplicationRepository applicationRepository) {
        this.jobRepository = jobRepository;
        this.applicationRepository = applicationRepository;
    }

    public Job createJob(JobRequest request, User recruiter) {

        if (recruiter.getRole() != Role.RECRUITER) {
            throw new RuntimeException("Only recruiters can create jobs");
        }

        Job job = new Job();

        job.setTitle(request.getTitle());
        job.setCompany(request.getCompany());
        job.setDescription(request.getDescription());
        job.setLocation(request.getLocation());
        job.setJobType(request.getJobType());
        job.setSkills(request.getSkills());
        job.setSalary(request.getSalary());
        job.setRecruiter(recruiter);
        job.setPostedAt(LocalDateTime.now());

        return jobRepository.save(job);
    }

    public List<Job> getAllJobs() {
        return jobRepository.findAll();
    }

    public Optional<Job> getJobById(Long id) {
        return jobRepository.findById(id);
    }

    public List<Job> searchJobs(String title,
                                String location,
                                String company,
                                String jobType) {

        title = normalizeFilter(title);
        location = normalizeFilter(location);
        company = normalizeFilter(company);
        jobType = normalizeFilter(jobType);

        return jobRepository.searchJobs(
                title,
                location,
                company,
                jobType
        );
    }

    private String normalizeFilter(String value) {

        if (value == null || value.isBlank()) {
            return null;
        }

        return value.trim();
    }

    public Job updateJob(Long id,
                          JobRequest request,
                          User recruiter) {

        Job existingJob = jobRepository
                .findByIdAndRecruiter(id, recruiter)
                .orElseThrow(() ->
                        new RuntimeException(
                                "Job not found or you are not the owner"
                        ));

        existingJob.setTitle(request.getTitle());
        existingJob.setCompany(request.getCompany());
        existingJob.setDescription(request.getDescription());
        existingJob.setLocation(request.getLocation());
        existingJob.setJobType(request.getJobType());
        existingJob.setSkills(request.getSkills());
        existingJob.setSalary(request.getSalary());

        return jobRepository.save(existingJob);
    }

    public void deleteJob(Long id, User recruiter) {

        Job job = jobRepository
                .findByIdAndRecruiter(id, recruiter)
                .orElseThrow(() ->
                        new RuntimeException(
                                "Job not found or you are not the owner"
                        ));

        if (applicationRepository.existsByJob(job)) {
            throw new RuntimeException(
                    "Cannot delete job because applications exist"
            );
        }

        jobRepository.delete(job);
    }
}