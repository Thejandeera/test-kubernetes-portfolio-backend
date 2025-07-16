package com.web.portfolio.UserManagement.Entity;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.index.Indexed;

@Document(collection = "users")
public class User {
    @Id
    private String id;

    private String image; // This will store the Cloudinary URL
    private String fullName;

    @Indexed(unique = true)
    private String userName;

    @Indexed(unique = true)
    private String email;

    private String role; // Student, Professional, freelancer, other
    private String institution;
    private String password;

    // Constructors
    public User() {}

    public User(String image, String fullName, String userName, String email,
                String role, String institution, String password) {
        this.image = image;
        this.fullName = fullName;
        this.userName = userName;
        this.email = email;
        this.role = role;
        this.institution = institution;
        this.password = password;
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

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @Override
    public String toString() {
        return "User{" +
                "id='" + id + '\'' +
                ", image='" + image + '\'' +
                ", fullName='" + fullName + '\'' +
                ", userName='" + userName + '\'' +
                ", email='" + email + '\'' +
                ", role='" + role + '\'' +
                ", institution='" + institution + '\'' +
                ", password='[PROTECTED]'" +
                '}';
    }
}