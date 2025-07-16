package com.web.portfolio.AllForOneUser.Service;

import com.web.portfolio.AllForOneUser.Entity.UserProfileAggregate;
import com.web.portfolio.AllForOneUser.DTO.UserProfileDTO;
import com.web.portfolio.AllForOneUser.Repository.UserProfileRepository;
import com.web.portfolio.UserManagement.Entity.User;
import com.web.portfolio.EducationManagement.Entity.Education;
import com.web.portfolio.ProjectManagement.Entity.Project;
import com.web.portfolio.ExtraActivityManagement.Entity.ExtraActivity;
import com.web.portfolio.SocialMediaManagement.Entity.SocialMedia;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class UserProfileService {

    @Autowired
    private UserProfileRepository userProfileRepository;

    public Optional<UserProfileAggregate> getAllUserDataById(String userId) {
        // Check if user exists
        Optional<User> userOpt = userProfileRepository.findUserById(userId);
        if (!userOpt.isPresent()) {
            return Optional.empty();
        }

        User user = userOpt.get();

        // Fetch all related data
        List<Education> educations = userProfileRepository.findEducationsByUserId(userId);
        List<Project> projects = userProfileRepository.findProjectsByUserId(userId);
        List<ExtraActivity> extraActivities = userProfileRepository.findExtraActivitiesByUserId(userId);
        Optional<SocialMedia> socialMediaOpt = userProfileRepository.findSocialMediaByUserId(userId);

        // Create aggregate
        UserProfileAggregate aggregate = new UserProfileAggregate(
                user,
                educations,
                projects,
                extraActivities,
                socialMediaOpt.orElse(null)
        );

        return Optional.of(aggregate);
    }

    public Optional<UserProfileDTO> getAllUserDataDTOById(String userId) {
        Optional<UserProfileAggregate> aggregateOpt = getAllUserDataById(userId);

        if (!aggregateOpt.isPresent()) {
            return Optional.empty();
        }

        UserProfileAggregate aggregate = aggregateOpt.get();
        return Optional.of(convertToDTO(aggregate));
    }

    public boolean userExists(String userId) {
        return userProfileRepository.existsUserById(userId);
    }

    private UserProfileDTO convertToDTO(UserProfileAggregate aggregate) {
        UserProfileDTO dto = new UserProfileDTO();

        User user = aggregate.getUser();
        if (user != null) {
            dto.setUserId(user.getId());
            dto.setFullName(user.getFullName());
            dto.setUserName(user.getUserName());
            dto.setEmail(user.getEmail());
            dto.setRole(user.getRole());
            dto.setInstitution(user.getInstitution());
            dto.setProfileImage(user.getImage());
        }

        // Convert educations
        if (aggregate.getEducations() != null) {
            dto.setEducations(aggregate.getEducations().stream()
                    .map(UserProfileDTO.EducationDTO::new)
                    .collect(Collectors.toList()));
        }

        // Convert projects
        if (aggregate.getProjects() != null) {
            dto.setProjects(aggregate.getProjects().stream()
                    .map(UserProfileDTO.ProjectDTO::new)
                    .collect(Collectors.toList()));
        }

        // Convert extra activities
        if (aggregate.getExtraActivities() != null) {
            dto.setExtraActivities(aggregate.getExtraActivities().stream()
                    .map(UserProfileDTO.ExtraActivityDTO::new)
                    .collect(Collectors.toList()));
        }

        // Convert social media
        dto.setSocialMedia(new UserProfileDTO.SocialMediaDTO(aggregate.getSocialMedia()));

        dto.setRetrievedAt(aggregate.getRetrievedAt());

        return dto;
    }

    /**
     * Get all user data by username
     *
     * @param username The username to search for
     * @return Optional<UserProfileAggregate> containing user profile data
     */
    public Optional<UserProfileAggregate> getAllUserDataByUsername(String username) {
        // Find user by username first
        Optional<User> userOpt = userProfileRepository.findUserByUsername(username);
        if (!userOpt.isPresent()) {
            return Optional.empty();
        }

        User user = userOpt.get();
        String userId = user.getId();

        // Fetch all related data using the user ID
        List<Education> educations = userProfileRepository.findEducationsByUserId(userId);
        List<Project> projects = userProfileRepository.findProjectsByUserId(userId);
        List<ExtraActivity> extraActivities = userProfileRepository.findExtraActivitiesByUserId(userId);
        Optional<SocialMedia> socialMediaOpt = userProfileRepository.findSocialMediaByUserId(userId);

        // Create aggregate
        UserProfileAggregate aggregate = new UserProfileAggregate(
                user,
                educations,
                projects,
                extraActivities,
                socialMediaOpt.orElse(null)
        );

        return Optional.of(aggregate);
    }

    /**
     * Get all user data DTO by username
     *
     * @param username The username to search for
     * @return Optional<UserProfileDTO> containing user profile DTO
     */
    public Optional<UserProfileDTO> getAllUserDataDTOByUsername(String username) {
        Optional<UserProfileAggregate> aggregateOpt = getAllUserDataByUsername(username);

        if (!aggregateOpt.isPresent()) {
            return Optional.empty();
        }

        UserProfileAggregate aggregate = aggregateOpt.get();
        return Optional.of(convertToDTO(aggregate));
    }

    /**
     * Check if user exists by username
     *
     * @param username The username to check
     * @return boolean indicating if user exists
     */
    public boolean userExistsByUsername(String username) {
        return userProfileRepository.existsUserByUsername(username);
    }
}