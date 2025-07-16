package com.web.portfolio.EducationManagement.DTO;

import java.util.Date;

public class EducationDTO {
    private String id;
    private String userId;
    private String institutionName;
    private Date endDate;

    // Constructors
    public EducationDTO() {}

    public EducationDTO(String userId, String institutionName, Date endDate) {
        this.userId = userId;
        this.institutionName = institutionName;
        this.endDate = endDate;
    }

    // Getters and Setters
    public String getId() { return id; }
    public void setId(String id) { this.id = id; }

    public String getUserId() { return userId; }
    public void setUserId(String userId) { this.userId = userId; }

    public String getInstitutionName() { return institutionName; }
    public void setInstitutionName(String institutionName) { this.institutionName = institutionName; }

    public Date getEndDate() { return endDate; }
    public void setEndDate(Date endDate) { this.endDate = endDate; }

    @Override
    public String toString() {
        return "EducationDTO{" +
                "id='" + id + '\'' +
                ", userId='" + userId + '\'' +
                ", institutionName='" + institutionName + '\'' +
                ", endDate=" + endDate +
                '}';
    }
}