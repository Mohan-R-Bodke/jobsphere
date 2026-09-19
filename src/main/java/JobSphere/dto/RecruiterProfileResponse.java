package JobSphere.dto;

public class RecruiterProfileResponse {

    private Long id;
    private Long userId;
    private String name;
    private String email;
    private String companyName;
    private String companyDescription;
    private String website;
    private String location;
    private String industry;
    private String companySize;

    public RecruiterProfileResponse() {
    }

    public RecruiterProfileResponse(
            Long id,
            Long userId,
            String name,
            String email,
            String companyName,
            String companyDescription,
            String website,
            String location,
            String industry,
            String companySize) {

        this.id = id;
        this.userId = userId;
        this.name = name;
        this.email = email;
        this.companyName = companyName;
        this.companyDescription = companyDescription;
        this.website = website;
        this.location = location;
        this.industry = industry;
        this.companySize = companySize;
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

    public String getCompanyName() {
        return companyName;
    }

    public String getCompanyDescription() {
        return companyDescription;
    }

    public String getWebsite() {
        return website;
    }

    public String getLocation() {
        return location;
    }

    public String getIndustry() {
        return industry;
    }

    public String getCompanySize() {
        return companySize;
    }
}