package com.web.portfolio.SocialMediaManagement.Repository;

import com.web.portfolio.SocialMediaManagement.Entity.SocialMedia;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;
import java.util.Optional;

@Repository
public interface SocialMediaRepository extends MongoRepository<SocialMedia, String> {
    Optional<SocialMedia> findByUserId(String userId);
}
