package com.web.portfolio.AllForOneUser.Repository;

import com.web.portfolio.UserManagement.Entity.User;
import com.web.portfolio.EducationManagement.Entity.Education;
import com.web.portfolio.ProjectManagement.Entity.Project;
import com.web.portfolio.ExtraActivityManagement.Entity.ExtraActivity;
import com.web.portfolio.SocialMediaManagement.Entity.SocialMedia;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public class UserProfileRepository {

    @Autowired
    private MongoTemplate mongoTemplate;

    public Optional<User> findUserById(String userId) {
        return Optional.ofNullable(mongoTemplate.findById(userId, User.class));
    }

    /**
     * Find user by username (since username is unique)
     *
     * @param username The username to search for
     * @return Optional<User> containing the user if found
     */
    public Optional<User> findUserByUsername(String username) {
        Query query = new Query(Criteria.where("userName").is(username));
        return Optional.ofNullable(mongoTemplate.findOne(query, User.class));
    }

    /**
     * Check if user exists by username
     *
     * @param username The username to check
     * @return boolean indicating if user exists
     */
    public boolean existsUserByUsername(String username) {
        Query query = new Query(Criteria.where("userName").is(username));
        return mongoTemplate.exists(query, User.class);
    }

    public List<Education> findEducationsByUserId(String userId) {
        Query query = new Query(Criteria.where("userId").is(userId));
        return mongoTemplate.find(query, Education.class);
    }

    public List<Project> findProjectsByUserId(String userId) {
        Query query = new Query(Criteria.where("userId").is(userId));
        return mongoTemplate.find(query, Project.class);
    }

    public List<ExtraActivity> findExtraActivitiesByUserId(String userId) {
        Query query = new Query(Criteria.where("userId").is(userId));
        return mongoTemplate.find(query, ExtraActivity.class);
    }

    public Optional<SocialMedia> findSocialMediaByUserId(String userId) {
        Query query = new Query(Criteria.where("userId").is(userId));
        return Optional.ofNullable(mongoTemplate.findOne(query, SocialMedia.class));
    }

    public boolean existsUserById(String userId) {
        Query query = new Query(Criteria.where("id").is(userId));
        return mongoTemplate.exists(query, User.class);
    }
}
