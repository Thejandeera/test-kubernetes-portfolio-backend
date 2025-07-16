package com.web.portfolio.ExtraActivityManagement.Repository;

import com.web.portfolio.ExtraActivityManagement.Entity.ExtraActivity;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface ExtraActivityRepository extends MongoRepository<ExtraActivity, String> {
    List<ExtraActivity> findByUserId(String userId);

}
