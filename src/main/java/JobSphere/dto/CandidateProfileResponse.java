package JobSphere.dto;

public class CandidateProfileResponse {

    private Long id;
    private Long userId;
    private String name;
    private String email;
    private String phone;
    private String education;
    private String skills;
    private String experience;
    private String resumeUrl;

    public CandidateProfileResponse() {
    }

    public CandidateProfileResponse(
            Long id,
            Long userId,
            String name,
            String email,
            String phone,
            String education,
            String skills,
            String experience,
            String resumeUrl) {

        this.id = id;
        this.userId = userId;
        this.name = name;
        this.email = email;
        this.phone = phone;
        this.education = education;
        this.skills = skills;
        this.experience = experience;
        this.resumeUrl = resumeUrl;
    }

    public Long getId() {
        return id;
    }

    public Long getUserId() {
        return userId;
    }

    public String getName() {
        return name;
    }

    public String getEmail() {
        return email;
    }

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
}