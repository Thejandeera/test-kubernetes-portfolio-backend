package com.web.portfolio.ExtraActivityManagement.Service;

import com.web.portfolio.ExtraActivityManagement.DTO.ExtraActivityDTO;
import com.web.portfolio.ExtraActivityManagement.Entity.ExtraActivity;
import com.web.portfolio.ExtraActivityManagement.Exception.*;
import com.web.portfolio.ExtraActivityManagement.Repository.ExtraActivityRepository;
import com.web.portfolio.UserManagement.Repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class ExtraActivityService {

    @Autowired
    private ExtraActivityRepository extraActivityRepository;

    @Autowired
    private UserRepository userRepository;

    public List<ExtraActivityDTO> getAllExtraActivities() {
        return extraActivityRepository.findAll().stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    public List<ExtraActivityDTO> getExtraActivitiesByUserId(String userId) {
        validateUserExists(userId);
        return extraActivityRepository.findByUserId(userId).stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    public Optional<ExtraActivityDTO> getExtraActivityById(String id) {
        return extraActivityRepository.findById(id)
                .map(this::convertToDTO);
    }

    public ExtraActivityDTO createExtraActivity(ExtraActivityDTO extraActivityDTO) {
        validateExtraActivity(extraActivityDTO);

        ExtraActivity extraActivity = convertToEntity(extraActivityDTO);
        ExtraActivity savedActivity = extraActivityRepository.save(extraActivity);
        return convertToDTO(savedActivity);
    }

    public ExtraActivityDTO updateExtraActivity(String id, ExtraActivityDTO extraActivityDTO) {
        ExtraActivity existingActivity = extraActivityRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Activity not found with id: " + id));

        if (extraActivityDTO.getUserId() != null &&
                !extraActivityDTO.getUserId().equals(existingActivity.getUserId())) {
            validateUserExists(extraActivityDTO.getUserId());
        }

        updateActivityFields(existingActivity, extraActivityDTO);
        ExtraActivity updatedActivity = extraActivityRepository.save(existingActivity);
        return convertToDTO(updatedActivity);
    }

    public void deleteExtraActivity(String id) {
        if (!extraActivityRepository.existsById(id)) {
            throw new ResourceNotFoundException("Activity not found with id: " + id);
        }
        extraActivityRepository.deleteById(id);
    }

    private void validateExtraActivity(ExtraActivityDTO dto) {
        if (dto.getUserId() == null || dto.getUserId().trim().isEmpty()) {
            throw new BadRequestException("User ID is required");
        }
        if (!userRepository.existsById(dto.getUserId())) {
            throw new UserNotFoundException(dto.getUserId());
        }
        if (dto.getName() == null || dto.getName().trim().isEmpty()) {
            throw new BadRequestException("Activity name is required");
        }
        if (dto.getInstitutionName() == null || dto.getInstitutionName().trim().isEmpty()) {
            throw new BadRequestException("Institution name is required");
        }
    }

    private void validateUserExists(String userId) {
        if (!userRepository.existsById(userId)) {
            throw new UserNotFoundException(userId);
        }
    }

    private ExtraActivityDTO convertToDTO(ExtraActivity activity) {
        ExtraActivityDTO dto = new ExtraActivityDTO();
        dto.setId(activity.getId());
        dto.setUserId(activity.getUserId());
        dto.setName(activity.getName());
        dto.setInstitutionName(activity.getInstitutionName());
        return dto;
    }

    private ExtraActivity convertToEntity(ExtraActivityDTO dto) {
        ExtraActivity activity = new ExtraActivity();
        activity.setUserId(dto.getUserId());
        activity.setName(dto.getName());
        activity.setInstitutionName(dto.getInstitutionName());
        return activity;
    }

    private void updateActivityFields(ExtraActivity activity, ExtraActivityDTO dto) {
        if (dto.getUserId() != null) {
            activity.setUserId(dto.getUserId());
        }
        if (dto.getName() != null) {
            activity.setName(dto.getName());
        }
        if (dto.getInstitutionName() != null) {
            activity.setInstitutionName(dto.getInstitutionName());
        }
    }
}