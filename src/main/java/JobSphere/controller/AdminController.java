package JobSphere.controller;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import JobSphere.dto.ApplicationResponse;
import JobSphere.dto.JobResponse;
import JobSphere.dto.UserResponse;
import JobSphere.entity.Application;
import JobSphere.entity.Job;
import JobSphere.entity.User;
import JobSphere.service.AdminService;
import JobSphere.service.ApplicationService;
import JobSphere.service.JobService;
import JobSphere.service.UserService;

@RestController
@RequestMapping("/api/admin")
public class AdminController {

    private final UserService userService;
    private final JobService jobService;
    private final ApplicationService applicationService;
    private final AdminService adminService;

    public AdminController(UserService userService,
                           JobService jobService,
                           ApplicationService applicationService,
                           AdminService adminService) {
        this.userService = userService;
        this.jobService = jobService;
        this.applicationService = applicationService;
        this.adminService = adminService;
    }

    @GetMapping("/users")
    public ResponseEntity<List<UserResponse>> getAllUsers() {
        List<UserResponse> users = userService.getAllUsers()
                .stream()
                .map(this::convertUserToResponse)
                .collect(Collectors.toList());

        return ResponseEntity.ok(users);
    }

    @GetMapping("/jobs")
    public ResponseEntity<List<JobResponse>> getAllJobs() {
        List<JobResponse> jobs = jobService.getAllJobs()
                .stream()
                .map(this::convertJobToResponse)
                .collect(Collectors.toList());

        return ResponseEntity.ok(jobs);
    }

    @GetMapping("/applications")
    public ResponseEntity<List<ApplicationResponse>> getAllApplications() {
        List<ApplicationResponse> responses = applicationService
                .getAllApplications()
                .stream()
                .map(this::convertApplicationToResponse)
                .collect(Collectors.toList());

        return ResponseEntity.ok(responses);
    }

    @DeleteMapping("/users/{id}")
    public ResponseEntity<String> deleteUser(@PathVariable Long id) {
        adminService.deleteUser(id);
        return ResponseEntity.ok("User deleted successfully");
    }

    @DeleteMapping("/jobs/{id}")
    public ResponseEntity<String> deleteJob(@PathVariable Long id) {
        adminService.deleteJob(id);
        return ResponseEntity.ok("Job deleted successfully");
    }

    @DeleteMapping("/applications/{id}")
public ResponseEntity<String> deleteApplication(
        @PathVariable Long id) {

    adminService.deleteApplication(id);

    return ResponseEntity.ok(
            "Application deleted successfully"
    );
}


    private UserResponse convertUserToResponse(User user) {
        return new UserResponse(
                user.getId(),
                user.getName(),
                user.getEmail(),
                user.getRole()
        );
    }

    private JobResponse convertJobToResponse(Job job) {
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

    private ApplicationResponse convertApplicationToResponse(
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