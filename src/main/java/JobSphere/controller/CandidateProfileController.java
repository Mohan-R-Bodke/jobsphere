package JobSphere.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import JobSphere.dto.CandidateProfileRequest;
import JobSphere.dto.CandidateProfileResponse;
import JobSphere.entity.CandidateProfile;
import JobSphere.service.CandidateProfileService;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/candidate/profile")
public class CandidateProfileController {

    private final CandidateProfileService profileService;

    public CandidateProfileController(CandidateProfileService profileService) {
        this.profileService = profileService;
    }

    @PostMapping
    public ResponseEntity<CandidateProfileResponse> saveProfile(
            @Valid @RequestBody CandidateProfileRequest request,
            Authentication authentication) {

        String email = authentication.getName();

        CandidateProfile savedProfile =
                profileService.saveProfile(email, request);

        return ResponseEntity.ok(convertToResponse(savedProfile));
    }

    @GetMapping
    public ResponseEntity<CandidateProfileResponse> getProfile(
            Authentication authentication) {

        String email = authentication.getName();

        CandidateProfile profile =
                profileService.getProfile(email);

        return ResponseEntity.ok(convertToResponse(profile));
    }

    private CandidateProfileResponse convertToResponse(
            CandidateProfile profile) {

        return new CandidateProfileResponse(
                profile.getId(),
                profile.getUser().getId(),
                profile.getUser().getName(),
                profile.getUser().getEmail(),
                profile.getPhone(),
                profile.getEducation(),
                profile.getSkills(),
                profile.getExperience(),
                profile.getResumeUrl()
        );
    }
}