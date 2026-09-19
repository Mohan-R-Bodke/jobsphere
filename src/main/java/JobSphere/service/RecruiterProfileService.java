package JobSphere.service;

import java.util.Optional;

import org.springframework.stereotype.Service;

import JobSphere.dto.RecruiterProfileRequest;
import JobSphere.entity.RecruiterProfile;
import JobSphere.entity.Role;
import JobSphere.entity.User;
import JobSphere.repository.RecruiterProfileRepository;
import JobSphere.repository.UserRepository;

@Service
public class RecruiterProfileService {

    private final RecruiterProfileRepository profileRepository;
    private final UserRepository userRepository;

    public RecruiterProfileService(
            RecruiterProfileRepository profileRepository,
            UserRepository userRepository) {

        this.profileRepository = profileRepository;
        this.userRepository = userRepository;
    }

    // Create or update recruiter profile
    public RecruiterProfile saveProfile(
            String email,
            RecruiterProfileRequest request) {

        User recruiter = userRepository.findByEmail(email)
                .orElseThrow(() ->
                        new RuntimeException("Recruiter not found"));

        if (recruiter.getRole() != Role.RECRUITER) {
            throw new RuntimeException(
                    "Only recruiters can create a profile");
        }

        Optional<RecruiterProfile> existingProfile =
                profileRepository.findByUser(recruiter);

        RecruiterProfile profileToSave;

        if (existingProfile.isPresent()) {
            profileToSave = existingProfile.get();
        } else {
            profileToSave = new RecruiterProfile();
            profileToSave.setUser(recruiter);
        }

        profileToSave.setCompanyName(request.getCompanyName());
        profileToSave.setCompanyDescription(
                request.getCompanyDescription());
        profileToSave.setWebsite(request.getWebsite());
        profileToSave.setLocation(request.getLocation());
        profileToSave.setIndustry(request.getIndustry());
        profileToSave.setCompanySize(request.getCompanySize());

        return profileRepository.save(profileToSave);
    }

    // Get logged-in recruiter profile
    public RecruiterProfile getProfile(String email) {

        User recruiter = userRepository.findByEmail(email)
                .orElseThrow(() ->
                        new RuntimeException("Recruiter not found"));

        if (recruiter.getRole() != Role.RECRUITER) {
            throw new RuntimeException(
                    "Only recruiters can view a profile");
        }

        return profileRepository.findByUser(recruiter)
                .orElseThrow(() ->
                        new RuntimeException(
                                "Recruiter profile not found"));
    }
}