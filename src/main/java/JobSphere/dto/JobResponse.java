package JobSphere.dto;

import java.time.LocalDateTime;

public class JobResponse {

    private Long id;
    private String title;
    private String company;
    private String description;
    private String location;
    private String jobType;
    private String skills;
    private String salary;
    private LocalDateTime postedAt;

    private Long recruiterId;
    private String recruiterName;
    private String recruiterEmail;

    public JobResponse() {
    }

    public JobResponse(
            Long id,
            String title,
            String company,
            String description,
            String location,
            String jobType,
            String skills,
            String salary,
            LocalDateTime postedAt,
            Long recruiterId,
            String recruiterName,
            String recruiterEmail) {

        this.id = id;
        this.title = title;
        this.company = company;
        this.description = description;
        this.location = location;
        this.jobType = jobType;
        this.skills = skills;
        this.salary = salary;
        this.postedAt = postedAt;
        this.recruiterId = recruiterId;
        this.recruiterName = recruiterName;
        this.recruiterEmail = recruiterEmail;
    }

    public Long getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public String getCompany() {
        return company;
    }

    public String getDescription() {
        return description;
    }

    public String getLocation() {
        return location;
    }

    public String getJobType() {
        return jobType;
    }

    public String getSkills() {
        return skills;
    }

    public String getSalary() {
        return salary;
    }

    public LocalDateTime getPostedAt() {
        return postedAt;
    }

    public Long getRecruiterId() {
        return recruiterId;
    }

    public String getRecruiterName() {
        return recruiterName;
    }

    public String getRecruiterEmail() {
        return recruiterEmail;
    }
}