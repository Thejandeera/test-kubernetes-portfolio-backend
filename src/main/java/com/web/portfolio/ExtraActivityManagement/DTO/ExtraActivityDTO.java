package com.web.portfolio.ExtraActivityManagement.DTO;

public class ExtraActivityDTO {
    private String id;
    private String userId;
    private String name;
    private String institutionName; // Only this field for institution

    // Constructors
    public ExtraActivityDTO() {}

    public ExtraActivityDTO(String userId, String name, String institutionName) {
        this.userId = userId;
        this.name = name;
        this.institutionName = institutionName;
    }

    // Getters and Setters
    public String getId() { return id; }
    public void setId(String id) { this.id = id; }

    public String getUserId() { return userId; }
    public void setUserId(String userId) { this.userId = userId; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public String getInstitutionName() { return institutionName; }
    public void setInstitutionName(String institutionName) {
        this.institutionName = institutionName;
    }
}