package com.web.portfolio.EducationManagement.Entity;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import java.util.Date;

@Document(collection = "education")
public class Education {
    @Id
    private String id;

    private String userId;
    private String institutionName;
    private Date endDate;

    // Constructors
    public Education() {}

    public Education(String userId, String institutionName, Date endDate) {
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
}