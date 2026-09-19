package JobSphere.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import JobSphere.dto.RecruiterProfileRequest;
import JobSphere.dto.RecruiterProfileResponse;
import JobSphere.entity.RecruiterProfile;
import JobSphere.service.RecruiterProfileService;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/recruiter/profile")
public class RecruiterProfileController {

    private final RecruiterProfileService profileService;

    public RecruiterProfileController(
            RecruiterProfileService profileService) {

        this.profileService = profileService;
    }

    // Create or update recruiter profile
    @PostMapping
    public ResponseEntity<RecruiterProfileResponse> saveProfile(
            @Valid @RequestBody RecruiterProfileRequest request,
            Authentication authentication) {

        String email = authentication.getName();

        RecruiterProfile savedProfile =
                profileService.saveProfile(email, request);

        return ResponseEntity.ok(
                convertToResponse(savedProfile)
        );
    }

    // Get logged-in recruiter profile
    @GetMapping
    public ResponseEntity<RecruiterProfileResponse> getProfile(
            Authentication authentication) {

        String email = authentication.getName();

        RecruiterProfile profile =
                profileService.getProfile(email);

        return ResponseEntity.ok(
                convertToResponse(profile)
        );
    }

    // Convert entity to DTO
    private RecruiterProfileResponse convertToResponse(
            RecruiterProfile profile) {

        return new RecruiterProfileResponse(
                profile.getId(),
                profile.getUser().getId(),
                profile.getUser().getName(),
                profile.getUser().getEmail(),
                profile.getCompanyName(),
                profile.getCompanyDescription(),
                profile.getWebsite(),
                profile.getLocation(),
                profile.getIndustry(),
                profile.getCompanySize()
        );
    }
}