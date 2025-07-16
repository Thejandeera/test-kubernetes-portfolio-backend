package com.web.portfolio.ExtraActivityManagement.Entity;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "extra_activities")
public class ExtraActivity {
    @Id
    private String id;
    private String userId;
    private String name;
    private String institutionName;

    // Constructors
    public ExtraActivity() {}

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