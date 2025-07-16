package com.web.portfolio.AllForOneUser.Entity;

import com.web.portfolio.UserManagement.Entity.User;
import com.web.portfolio.EducationManagement.Entity.Education;
import com.web.portfolio.ProjectManagement.Entity.Project;
import com.web.portfolio.ExtraActivityManagement.Entity.ExtraActivity;
import com.web.portfolio.SocialMediaManagement.Entity.SocialMedia;

import java.util.List;
import java.util.Date;

public class UserProfileAggregate {
    private User user;
    private List<Education> educations;
    private List<Project> projects;
    private List<ExtraActivity> extraActivities;
    private SocialMedia socialMedia;
    private Date retrievedAt;

    // Constructors
    public UserProfileAggregate() {
        this.retrievedAt = new Date();
    }

    public UserProfileAggregate(User user, List<Education> educations, List<Project> projects,
                                List<ExtraActivity> extraActivities, SocialMedia socialMedia) {
        this.user = user;
        this.educations = educations;
        this.projects = projects;
        this.extraActivities = extraActivities;
        this.socialMedia = socialMedia;
        this.retrievedAt = new Date();
    }

    // Getters and Setters
    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public List<Education> getEducations() {
        return educations;
    }

    public void setEducations(List<Education> educations) {
        this.educations = educations;
    }

    public List<Project> getProjects() {
        return projects;
    }

    public void setProjects(List<Project> projects) {
        this.projects = projects;
    }

    public List<ExtraActivity> getExtraActivities() {
        return extraActivities;
    }

    public void setExtraActivities(List<ExtraActivity> extraActivities) {
        this.extraActivities = extraActivities;
    }

    public SocialMedia getSocialMedia() {
        return socialMedia;
    }

    public void setSocialMedia(SocialMedia socialMedia) {
        this.socialMedia = socialMedia;
    }

    public Date getRetrievedAt() {
        return retrievedAt;
    }

    public void setRetrievedAt(Date retrievedAt) {
        this.retrievedAt = retrievedAt;
    }

    @Override
    public String toString() {
        return "UserProfileAggregate{" +
                "user=" + user +
                ", educations=" + educations +
                ", projects=" + projects +
                ", extraActivities=" + extraActivities +
                ", socialMedia=" + socialMedia +
                ", retrievedAt=" + retrievedAt +
                '}';
    }
}