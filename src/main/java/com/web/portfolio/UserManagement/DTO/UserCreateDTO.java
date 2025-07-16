package com.web.portfolio.UserManagement.DTO;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class UserCreateDTO {
    private String fullName;
    private String userName;
    private String email;
    private String role;
    private String institution;
    private String password;

    // Constructors
    public UserCreateDTO() {}

    public UserCreateDTO(String fullName, String userName, String email,
                         String role, String institution, String password) {
        this.fullName = fullName;
        this.userName = userName;
        this.email = email;
        this.role = role;
        this.institution = institution;
        this.password = password;
    }

    // Getters and Setters
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

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @Override
    public String toString() {
        return "UserCreateDTO{" +
                "fullName='" + fullName + '\'' +
                ", userName='" + userName + '\'' +
                ", email='" + email + '\'' +
                ", role='" + role + '\'' +
                ", institution='" + institution + '\'' +
                ", password='[PROTECTED]'" +
                '}';
    }
}