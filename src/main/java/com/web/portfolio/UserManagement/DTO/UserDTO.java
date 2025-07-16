package com.web.portfolio.UserManagement.DTO;

import com.fasterxml.jackson.annotation.JsonInclude;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class UserDTO {
    private String id;
    private String image; // This will contain the Cloudinary URL
    private String fullName;
    private String userName;
    private String email;
    private String role;
    private String institution;
    // Password excluded for security

    // Constructors
    public UserDTO() {}

    public UserDTO(String id, String image, String fullName, String userName,
                   String email, String role, String institution) {
        this.id = id;
        this.image = image;
        this.fullName = fullName;
        this.userName = userName;
        this.email = email;
        this.role = role;
        this.institution = institution;
    }

    // Getters and Setters
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public String getInstitution() {
        return institution;
    }

    public void setInstitution(String institution) {
        this.institution = institution;
    }

    @Override
    public String toString() {
        return "UserDTO{" +
                "id='" + id + '\'' +
                ", image='" + image + '\'' +
                ", fullName='" + fullName + '\'' +
                ", userName='" + userName + '\'' +
                ", email='" + email + '\'' +
                ", role='" + role + '\'' +
                ", institution='" + institution + '\'' +
                '}';
    }
}