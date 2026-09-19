package JobSphere.controller;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import JobSphere.dto.ApplicationRequest;
import JobSphere.dto.ApplicationResponse;
import JobSphere.dto.ApplicationStatusRequest;
import JobSphere.entity.Application;
import JobSphere.service.ApplicationService;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/applications")
public class ApplicationController {

    private final ApplicationService applicationService;

    public ApplicationController(
            ApplicationService applicationService) {

        this.applicationService = applicationService;
    }

    // Candidate applies for a job
    @PostMapping("/apply")
public ResponseEntity<ApplicationResponse> applyForJob(
        @Valid @RequestBody ApplicationRequest request,
        Authentication authentication) {

    String email = authentication.getName();

    Application application =
            applicationService.applyForJob(email, request.getJobId());

    return ResponseEntity
            .status(org.springframework.http.HttpStatus.CREATED)
            .body(convertToResponse(application));
}

    // Get all applications of logged-in candidate
    @GetMapping("/candidate")
    public ResponseEntity<List<ApplicationResponse>>
    getCandidateApplications(
            Authentication authentication) {

        String email = authentication.getName();

        List<ApplicationResponse> responses =
                applicationService
                        .getApplicationsByCandidate(email)
                        .stream()
                        .map(this::convertToResponse)
                        .collect(Collectors.toList());

        return ResponseEntity.ok(responses);
    }

    // Get all applicants for a job
    @GetMapping("/job/{jobId}")
    public ResponseEntity<List<ApplicationResponse>>
    getJobApplications(
            @PathVariable Long jobId,
            Authentication authentication) {

        String email = authentication.getName();

        List<ApplicationResponse> responses =
                applicationService
                        .getApplicationsByJob(jobId, email)
                        .stream()
                        .map(this::convertToResponse)
                        .collect(Collectors.toList());

        return ResponseEntity.ok(responses);
    }

    // Recruiter updates application status
    @PutMapping("/{applicationId}/status")
    public ResponseEntity<ApplicationResponse>
    updateApplicationStatus(
            @PathVariable Long applicationId,
            @Valid @RequestBody ApplicationStatusRequest request,
            Authentication authentication) {

        String email = authentication.getName();

        Application application =
                applicationService.updateApplicationStatus(
                        applicationId,
                        request.getStatus(),
                        email
                );

        return ResponseEntity.ok(
                convertToResponse(application)
        );
    }

    // Convert entity to DTO
    private ApplicationResponse convertToResponse(
            Application application) {

        return new ApplicationResponse(
                application.getId(),
                application.getJob().getId(),
                application.getJob().getTitle(),
                application.getJob().getCompany(),
                application.getCandidate().getName(),
                application.getCandidate().getEmail(),
                application.getStatus(),
                application.getAppliedAt()
        );
    }
}