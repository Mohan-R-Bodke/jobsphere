package JobSphere.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/test")
public class RoleTestController {

    @GetMapping("/candidate")
    public String candidateAccess() {
        return "Candidate access granted";
    }

    @GetMapping("/recruiter")
    public String recruiterAccess() {
        return "Recruiter access granted";
    }

    @GetMapping("/admin")
    public String adminAccess() {
        return "Admin access granted";
    }
}