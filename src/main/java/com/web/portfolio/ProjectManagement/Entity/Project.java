package com.web.portfolio.ProjectManagement.Entity;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import java.util.Date;

@Document(collection = "projects")
public class Project {
    @Id
    private String id;

    private String userId;
    private String image1;
    private String image2;
    private String image3;
    private String image4;
    private String projectName;
    private Date startDate;
    private Date endDate;
    private String projectLink;
    private String description;
    private String skills;
    private Date createdAt;
    private Date updatedAt;

    // Constructors
    public Project() {
        this.createdAt = new Date();
        this.updatedAt = new Date();
    }

    public Project(String userId, String image1, String image2, String image3, String image4,
                   String projectName, Date startDate, Date endDate, String projectLink,
                   String description, String skills) {
        this.userId = userId;
        this.image1 = image1;
        this.image2 = image2;
        this.image3 = image3;
        this.image4 = image4;
        this.projectName = projectName;
        this.startDate = startDate;
        this.endDate = endDate;
        this.projectLink = projectLink;
        this.description = description;
        this.skills = skills;
        this.createdAt = new Date();
        this.updatedAt = new Date();
    }

    // Method to update timestamp before saving
    public void updateTimestamp() {
        this.updatedAt = new Date();
    }

    // Getters and Setters
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getImage1() {
        return image1;
    }

    public void setImage1(String image1) {
        this.image1 = image1;
    }

    public String getImage2() {
        return image2;
    }

    public void setImage2(String image2) {
        this.image2 = image2;
    }

    public String getImage3() {
        return image3;
    }

    public void setImage3(String image3) {
        this.image3 = image3;
    }

    public String getImage4() {
        return image4;
    }

    public void setImage4(String image4) {
        this.image4 = image4;
    }

    public String getProjectName() {
        return projectName;
    }

    public void setProjectName(String projectName) {
        this.projectName = projectName;
        this.updateTimestamp();
    }

    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
        this.updateTimestamp();
    }

    public Date getEndDate() {
        return endDate;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
        this.updateTimestamp();
    }

    public String getProjectLink() {
        return projectLink;
    }

    public void setProjectLink(String projectLink) {
        this.projectLink = projectLink;
        this.updateTimestamp();
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
        this.updateTimestamp();
    }

    public String getSkills() {
        return skills;
    }

    public void setSkills(String skills) {
        this.skills = skills;
        this.updateTimestamp();
    }

    public Date getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }

    public Date getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(Date updatedAt) {
        this.updatedAt = updatedAt;
    }

    @Override
    public String toString() {
        return "Project{" +
                "id='" + id + '\'' +
                ", userId='" + userId + '\'' +
                ", projectName='" + projectName + '\'' +
                ", startDate=" + startDate +
                ", endDate=" + endDate +
                ", projectLink='" + projectLink + '\'' +
                ", description='" + description + '\'' +
                ", skills='" + skills + '\'' +
                ", createdAt=" + createdAt +
                ", updatedAt=" + updatedAt +
                '}';
    }
}