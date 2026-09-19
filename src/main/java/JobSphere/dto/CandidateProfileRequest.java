package JobSphere.dto;

import jakarta.validation.constraints.NotBlank;

public class CandidateProfileRequest {

    @NotBlank(message = "Phone is required")
    private String phone;

    @NotBlank(message = "Education is required")
    private String education;

    @NotBlank(message = "Skills are required")
    private String skills;

    @NotBlank(message = "Experience is required")
    private String experience;

    private String resumeUrl;

    public CandidateProfileRequest() {}

    public String getPhone() {
        return phone;
    }

    public String getEducation() {
        return education;
    }

    public String getSkills() {
        return skills;
    }

    public String getExperience() {
        return experience;
    }

    public String getResumeUrl() {
        return resumeUrl;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public void setEducation(String education) {
        this.education = education;
    }

    public void setSkills(String skills) {
        this.skills = skills;
    }

    public void setExperience(String experience) {
        this.experience = experience;
    }

    public void setResumeUrl(String resumeUrl) {
        this.resumeUrl = resumeUrl;
    }
}