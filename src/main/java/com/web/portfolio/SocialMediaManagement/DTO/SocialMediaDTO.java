package com.web.portfolio.SocialMediaManagement.DTO;

public class SocialMediaDTO {
    private String id;
    private String userId;
    private String gitHubLink;
    private String linkedInLink;
    private String otherLink;

    // Constructors
    public SocialMediaDTO() {}

    // Getters and Setters
    public String getId() { return id; }
    public void setId(String id) { this.id = id; }

    public String getUserId() { return userId; }
    public void setUserId(String userId) { this.userId = userId; }

    public String getGitHubLink() { return gitHubLink; }
    public void setGitHubLink(String gitHubLink) { this.gitHubLink = gitHubLink; }

    public String getLinkedInLink() { return linkedInLink; }
    public void setLinkedInLink(String linkedInLink) { this.linkedInLink = linkedInLink; }

    public String getOtherLink() { return otherLink; }
    public void setOtherLink(String otherLink) { this.otherLink = otherLink; }
}
