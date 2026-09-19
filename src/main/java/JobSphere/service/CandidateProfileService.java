package JobSphere.service;

import JobSphere.dto.CandidateProfileRequest;
import JobSphere.entity.CandidateProfile;
import JobSphere.entity.Role;
import JobSphere.entity.User;
import JobSphere.repository.CandidateProfileRepository;
import JobSphere.repository.UserRepository;

import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class CandidateProfileService {

    private final CandidateProfileRepository profileRepository;
    private final UserRepository userRepository;

    public CandidateProfileService(
            CandidateProfileRepository profileRepository,
            UserRepository userRepository) {

        this.profileRepository = profileRepository;
        this.userRepository = userRepository;
    }

    // Create or update candidate profile
    public CandidateProfile saveProfile(
            String email,
            CandidateProfileRequest request) {

        User candidate = userRepository.findByEmail(email)
                .orElseThrow(() ->
                        new RuntimeException("Candidate not found"));

        if (candidate.getRole() != Role.CANDIDATE) {
            throw new RuntimeException(
                    "Only candidates can create a profile");
        }

        Optional<CandidateProfile> existingProfile =
                profileRepository.findByUser(candidate);

        CandidateProfile profileToSave;

        if (existingProfile.isPresent()) {
            profileToSave = existingProfile.get();
        } else {
            profileToSave = new CandidateProfile();
            profileToSave.setUser(candidate);
        }

        profileToSave.setPhone(request.getPhone());
        profileToSave.setEducation(request.getEducation());
        profileToSave.setSkills(request.getSkills());
        profileToSave.setExperience(request.getExperience());
        profileToSave.setResumeUrl(request.getResumeUrl());

        return profileRepository.save(profileToSave);
    }

    // Get logged-in candidate profile
    public CandidateProfile getProfile(String email) {

        User candidate = userRepository.findByEmail(email)
                .orElseThrow(() ->
                        new RuntimeException("Candidate not found"));

        if (candidate.getRole() != Role.CANDIDATE) {
            throw new RuntimeException(
                    "Only candidates can view a profile");
        }

        return profileRepository.findByUser(candidate)
                .orElseThrow(() ->
                        new RuntimeException(
                                "Candidate profile not found"));
    }
}