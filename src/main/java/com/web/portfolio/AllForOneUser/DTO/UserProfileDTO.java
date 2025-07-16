package com.web.portfolio.AllForOneUser.DTO;

import com.web.portfolio.UserManagement.Entity.User;
import com.web.portfolio.EducationManagement.Entity.Education;
import com.web.portfolio.ProjectManagement.Entity.Project;
import com.web.portfolio.ExtraActivityManagement.Entity.ExtraActivity;
import com.web.portfolio.SocialMediaManagement.Entity.SocialMedia;

import java.util.List;
import java.util.Date;

public class UserProfileDTO {
    private String userId;
    private String fullName;
    private String userName;
    private String email;
    private String role;
    private String institution;
    private String profileImage;

    private List<EducationDTO> educations;
    private List<ProjectDTO> projects;
    private List<ExtraActivityDTO> extraActivities;
    private SocialMediaDTO socialMedia;

    private Date retrievedAt;

    // Inner DTOs
    public static class EducationDTO {
        private String id;
        private String institutionName;
        private Date endDate;

        // Constructors
        public EducationDTO() {}

        public EducationDTO(Education education) {
            this.id = education.getId();
            this.institutionName = education.getInstitutionName();
            this.endDate = education.getEndDate();
        }

        // Getters and Setters
        public String getId() { return id; }
        public void setId(String id) { this.id = id; }
        public String getInstitutionName() { return institutionName; }
        public void setInstitutionName(String institutionName) { this.institutionName = institutionName; }
        public Date getEndDate() { return endDate; }
        public void setEndDate(Date endDate) { this.endDate = endDate; }
    }

    public static class ProjectDTO {
        private String id;
        private String projectName;
        private Date startDate;
        private Date endDate;
        private String projectLink;
        private String description;
        private String skills;
        private String image1;
        private String image2;
        private String image3;
        private String image4;

        // Constructors
        public ProjectDTO() {}

        public ProjectDTO(Project project) {
            this.id = project.getId();
            this.projectName = project.getProjectName();
            this.startDate = project.getStartDate();
            this.endDate = project.getEndDate();
            this.projectLink = project.getProjectLink();
            this.description = project.getDescription();
            this.skills = project.getSkills();
            this.image1 = project.getImage1();
            this.image2 = project.getImage2();
            this.image3 = project.getImage3();
            this.image4 = project.getImage4();
        }

        // Getters and Setters
        public String getId() { return id; }
        public void setId(String id) { this.id = id; }
        public String getProjectName() { return projectName; }
        public void setProjectName(String projectName) { this.projectName = projectName; }
        public Date getStartDate() { return startDate; }
        public void setStartDate(Date startDate) { this.startDate = startDate; }
        public Date getEndDate() { return endDate; }
        public void setEndDate(Date endDate) { this.endDate = endDate; }
        public String getProjectLink() { return projectLink; }
        public void setProjectLink(String projectLink) { this.projectLink = projectLink; }
        public String getDescription() { return description; }
        public void setDescription(String description) { this.description = description; }
        public String getSkills() { return skills; }
        public void setSkills(String skills) { this.skills = skills; }
        public String getImage1() { return image1; }
        public void setImage1(String image1) { this.image1 = image1; }
        public String getImage2() { return image2; }
        public void setImage2(String image2) { this.image2 = image2; }
        public String getImage3() { return image3; }
        public void setImage3(String image3) { this.image3 = image3; }
        public String getImage4() { return image4; }
        public void setImage4(String image4) { this.image4 = image4; }
    }

    public static class ExtraActivityDTO {
        private String id;
        private String name;
        private String institutionName;

        // Constructors
        public ExtraActivityDTO() {}

        public ExtraActivityDTO(ExtraActivity extraActivity) {
            this.id = extraActivity.getId();
            this.name = extraActivity.getName();
            this.institutionName = extraActivity.getInstitutionName();
        }

        // Getters and Setters
        public String getId() { return id; }
        public void setId(String id) { this.id = id; }
        public String getName() { return name; }
        public void setName(String name) { this.name = name; }
        public String getInstitutionName() { return institutionName; }
        public void setEducationId(String educationId) { this.institutionName = institutionName; }
    }

    public static class SocialMediaDTO {
        private String id;
        private String gitHubLink;
        private String linkedInLink;
        private String otherLink;

        // Constructors
        public SocialMediaDTO() {}

        public SocialMediaDTO(SocialMedia socialMedia) {
            if (socialMedia != null) {
                this.id = socialMedia.getId();
                this.gitHubLink = socialMedia.getGitHubLink();
                this.linkedInLink = socialMedia.getLinkedInLink();
                this.otherLink = socialMedia.getOtherLink();
            }
        }

        // Getters and Setters
        public String getId() { return id; }
        public void setId(String id) { this.id = id; }
        public String getGitHubLink() { return gitHubLink; }
        public void setGitHubLink(String gitHubLink) { this.gitHubLink = gitHubLink; }
        public String getLinkedInLink() { return linkedInLink; }
        public void setLinkedInLink(String linkedInLink) { this.linkedInLink = linkedInLink; }
        public String getOtherLink() { return otherLink; }
        public void setOtherLink(String otherLink) { this.otherLink = otherLink; }
    }

    // Main DTO Constructors
    public UserProfileDTO() {
        this.retrievedAt = new Date();
    }

    // Getters and Setters for main DTO
    public String getUserId() { return userId; }
    public void setUserId(String userId) { this.userId = userId; }
    public String getFullName() { return fullName; }
    public void setFullName(String fullName) { this.fullName = fullName; }
    public String getUserName() { return userName; }
    public void setUserName(String userName) { this.userName = userName; }
    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }
    public String getRole() { return role; }
    public void setRole(String role) { this.role = role; }
    public String getInstitution() { return institution; }
    public void setInstitution(String institution) { this.institution = institution; }
    public String getProfileImage() { return profileImage; }
    public void setProfileImage(String profileImage) { this.profileImage = profileImage; }
    public List<EducationDTO> getEducations() { return educations; }
    public void setEducations(List<EducationDTO> educations) { this.educations = educations; }
    public List<ProjectDTO> getProjects() { return projects; }
    public void setProjects(List<ProjectDTO> projects) { this.projects = projects; }
    public List<ExtraActivityDTO> getExtraActivities() { return extraActivities; }
    public void setExtraActivities(List<ExtraActivityDTO> extraActivities) { this.extraActivities = extraActivities; }
    public SocialMediaDTO getSocialMedia() { return socialMedia; }
    public void setSocialMedia(SocialMediaDTO socialMedia) { this.socialMedia = socialMedia; }
    public Date getRetrievedAt() { return retrievedAt; }
    public void setRetrievedAt(Date retrievedAt) { this.retrievedAt = retrievedAt; }
}