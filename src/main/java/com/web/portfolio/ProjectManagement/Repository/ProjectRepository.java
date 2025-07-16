package com.web.portfolio.ProjectManagement.Repository;

import com.web.portfolio.ProjectManagement.Entity.Project;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;
import java.util.Optional;

@Repository
public interface ProjectRepository extends MongoRepository<Project, String> {

    // Basic CRUD operations are inherited from MongoRepository

    // Find projects by user ID
    List<Project> findByUserId(String userId);

    // Find projects by user ID with pagination
    @Query(value = "{'userId': ?0}", sort = "{'createdAt': -1}")
    List<Project> findByUserIdWithPagination(String userId, org.springframework.data.domain.Pageable pageable);

    // Find projects by user ID and order by creation date (newest first)
    List<Project> findByUserIdOrderByCreatedAtDesc(String userId);

    // Find projects by project name (case insensitive)
    @Query("{'projectName': {$regex: ?0, $options: 'i'}}")
    List<Project> findByProjectNameContainingIgnoreCase(String projectName);

    // Find projects by skills (case insensitive)
    @Query("{'skills': {$regex: ?0, $options: 'i'}}")
    List<Project> findBySkillsContainingIgnoreCase(String skill);

    // Find projects within date range
    List<Project> findByStartDateBetween(Date startDate, Date endDate);

    // Find projects by user ID and project name (exact match)
    Optional<Project> findByUserIdAndProjectName(String userId, String projectName);

    // Find projects by user ID and project name (case insensitive)
    @Query("{'userId': ?0, 'projectName': {$regex: ?1, $options: 'i'}}")
    Optional<Project> findByUserIdAndProjectNameIgnoreCase(String userId, String projectName);

    // Find all projects ordered by creation date (newest first)
    List<Project> findAllByOrderByCreatedAtDesc();

    // Find all projects with pagination support
    @Query(value = "{}", sort = "{'createdAt': -1}")
    List<Project> findAllWithPagination(org.springframework.data.domain.Pageable pageable);

    // Count projects by user ID
    long countByUserId(String userId);

    // Find recent projects (last N days)
    @Query("{'createdAt': {$gte: ?0}}")
    List<Project> findRecentProjects(Date dateAfter);

    // Find projects updated after a specific date
    @Query("{'updatedAt': {$gte: ?0}}")
    List<Project> findProjectsUpdatedAfter(Date dateAfter);

    // Delete projects by user ID
    void deleteByUserId(String userId);

    // Check if project exists for user
    boolean existsByUserIdAndId(String userId, String projectId);

    // Find projects containing multiple skills
    @Query("{'skills': {$all: ?0}}")
    List<Project> findBySkillsIn(List<String> skills);

    // Find projects by multiple criteria
    @Query("{$and: [" +
            "{'userId': ?0}, " +
            "{'startDate': {$gte: ?1}}, " +
            "{'endDate': {$lte: ?2}}, " +
            "{'skills': {$regex: ?3, $options: 'i'}}" +
            "]}")
    List<Project> findByAdvancedCriteria(String userId, Date startDate, Date endDate, String skill);

    // Find projects with description containing text (full-text search)
    @Query("{'$text': {'$search': ?0}}")
    List<Project> findByTextSearch(String text);
}