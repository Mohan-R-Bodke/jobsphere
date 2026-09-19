package JobSphere.controller;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import JobSphere.dto.JobRequest;
import JobSphere.dto.JobResponse;
import JobSphere.entity.Job;
import JobSphere.entity.User;
import JobSphere.repository.UserRepository;
import JobSphere.service.JobService;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/jobs")
public class JobController {

    private final JobService jobService;
    private final UserRepository userRepository;

    public JobController(
            JobService jobService,
            UserRepository userRepository) {

        this.jobService = jobService;
        this.userRepository = userRepository;
    }

    // Get all jobs + search/filter
    @GetMapping
    public ResponseEntity<List<JobResponse>> getAllJobs(
            @RequestParam(required = false) String title,
            @RequestParam(required = false) String location,
            @RequestParam(required = false) String company,
            @RequestParam(required = false) String jobType) {

        List<JobResponse> jobs = jobService
                .searchJobs(title, location, company, jobType)
                .stream()
                .map(this::convertToResponse)
                .collect(Collectors.toList());

        return ResponseEntity.ok(jobs);
    }

    // Get job by ID
    @GetMapping("/{id}")
    public ResponseEntity<JobResponse> getJobById(
            @PathVariable Long id) {

        return jobService.getJobById(id)
                .map(this::convertToResponse)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    // Create job
    @PostMapping
public ResponseEntity<JobResponse> createJob(
        @Valid @RequestBody JobRequest request,
        Authentication authentication) {

    String email = authentication.getName();

    User recruiter = userRepository.findByEmail(email)
            .orElseThrow(() -> new RuntimeException("Recruiter not found"));

    Job savedJob = jobService.createJob(request, recruiter);

    return ResponseEntity
            .status(org.springframework.http.HttpStatus.CREATED)
            .body(convertToResponse(savedJob));
}

    // Update job
    @PutMapping("/{id}")
    public ResponseEntity<JobResponse> updateJob(
            @PathVariable Long id,
            @Valid @RequestBody JobRequest request,
            Authentication authentication) {

        String email = authentication.getName();

        User recruiter = userRepository.findByEmail(email)
                .orElseThrow(() ->
                        new RuntimeException("Recruiter not found"));

        Job updatedJob =
                jobService.updateJob(
                        id,
                        request,
                        recruiter
                );

        return ResponseEntity.ok(
                convertToResponse(updatedJob)
        );
    }

    // Delete job
    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteJob(
            @PathVariable Long id,
            Authentication authentication) {

        String email = authentication.getName();

        User recruiter = userRepository.findByEmail(email)
                .orElseThrow(() ->
                        new RuntimeException("Recruiter not found"));

        jobService.deleteJob(id, recruiter);

        return ResponseEntity.ok(
                "Job deleted successfully"
        );
    }

    // Convert Job entity to safe JobResponse
    private JobResponse convertToResponse(Job job) {

        User recruiter = job.getRecruiter();

        return new JobResponse(
                job.getId(),
                job.getTitle(),
                job.getCompany(),
                job.getDescription(),
                job.getLocation(),
                job.getJobType(),
                job.getSkills(),
                job.getSalary(),
                job.getPostedAt(),
                recruiter != null ? recruiter.getId() : null,
                recruiter != null ? recruiter.getName() : null,
                recruiter != null ? recruiter.getEmail() : null
        );
    }
}