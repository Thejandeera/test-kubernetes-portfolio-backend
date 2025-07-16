package com.web.portfolio.EducationManagement.Repository;

import com.web.portfolio.EducationManagement.Entity.Education;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface EducationRepository extends MongoRepository<Education, String> {

    /**
     * Find all education records for a specific user
     * @param userId the user ID to search for
     * @return List of education records for the user
     */
    List<Education> findByUserId(String userId);

    /**
     * Find education records by user ID and institution name
     * @param userId the user ID
     * @param institutionName the institution name
     * @return List of education records matching the criteria
     */
    List<Education> findByUserIdAndInstitutionName(String userId, String institutionName);

    /**
     * Count education records for a specific user
     * @param userId the user ID
     * @return count of education records
     */
    long countByUserId(String userId);

    /**
     * Delete all education records for a specific user
     * @param userId the user ID
     */
    void deleteByUserId(String userId);

    // In EducationRepository.java
    /**
     * Find distinct institution names for a specific user
     * @param userId the user ID to search for
     * @return List of distinct institution names for the user
     */
    List<String> findDistinctInstitutionNameByUserId(String userId);
}