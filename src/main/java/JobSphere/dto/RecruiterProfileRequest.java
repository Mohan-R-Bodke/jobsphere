package JobSphere.dto;

import jakarta.validation.constraints.NotBlank;

public class RecruiterProfileRequest {

    @NotBlank(message = "Company name is required")
    private String companyName;

    @NotBlank(message = "Company description is required")
    private String companyDescription;

    private String website;

    @NotBlank(message = "Location is required")
    private String location;

    @NotBlank(message = "Industry is required")
    private String industry;

    private String companySize;

    public RecruiterProfileRequest() {
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

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    public void setCompanyDescription(String companyDescription) {
        this.companyDescription = companyDescription;
    }

    public void setWebsite(String website) {
        this.website = website;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public void setIndustry(String industry) {
        this.industry = industry;
    }

    public void setCompanySize(String companySize) {
        this.companySize = companySize;
    }
}