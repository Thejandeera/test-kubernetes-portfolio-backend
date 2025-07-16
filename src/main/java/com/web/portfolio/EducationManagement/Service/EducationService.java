package com.web.portfolio.EducationManagement.Service;

import com.web.portfolio.EducationManagement.DTO.EducationDTO;
import com.web.portfolio.EducationManagement.Entity.Education;
import com.web.portfolio.EducationManagement.Repository.EducationRepository;
import com.web.portfolio.UserManagement.Repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class EducationService {

    @Autowired
    private EducationRepository educationRepository;

    @Autowired
    private UserRepository userRepository;

    public List<EducationDTO> getAllEducation() {
        return educationRepository.findAll().stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    public List<EducationDTO> getEducationByUserId(String userId) {
        // Validate user exists
        if (!userRepository.existsById(userId)) {
            throw new RuntimeException("User not found with ID: " + userId);
        }

        return educationRepository.findByUserId(userId).stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    public Optional<EducationDTO> getEducationById(String id) {
        return educationRepository.findById(id)
                .map(this::convertToDTO);
    }

    public EducationDTO createEducation(EducationDTO educationDTO) {
        // Validate required fields
        validateEducationDTO(educationDTO);

        // Validate user exists
        if (!userRepository.existsById(educationDTO.getUserId())) {
            throw new RuntimeException("Cannot create education record. User not found with ID: " + educationDTO.getUserId());
        }

        // Validate end date is not in the future
        if (educationDTO.getEndDate() != null && educationDTO.getEndDate().after(new Date())) {
            throw new RuntimeException("End date cannot be in the future");
        }

        Education education = convertToEntity(educationDTO);
        Education savedEducation = educationRepository.save(education);
        return convertToDTO(savedEducation);
    }

    public EducationDTO updateEducation(String id, EducationDTO educationDTO) {
        // Find existing education record
        Education education = educationRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Education record not found with ID: " + id));

        // Validate required fields
        validateEducationDTO(educationDTO);

        // If userId is being changed, validate new user exists
        if (!education.getUserId().equals(educationDTO.getUserId())) {
            if (!userRepository.existsById(educationDTO.getUserId())) {
                throw new RuntimeException("Cannot update education record. User not found with ID: " + educationDTO.getUserId());
            }
        }

        // Validate end date is not in the future
        if (educationDTO.getEndDate() != null && educationDTO.getEndDate().after(new Date())) {
            throw new RuntimeException("End date cannot be in the future");
        }

        updateEducationFields(education, educationDTO);
        Education savedEducation = educationRepository.save(education);
        return convertToDTO(savedEducation);
    }

    public void deleteEducation(String id) {
        if (!educationRepository.existsById(id)) {
            throw new RuntimeException("Education record not found with ID: " + id);
        }
        educationRepository.deleteById(id);
    }

    public void deleteEducationByUserId(String userId) {
        if (!userRepository.existsById(userId)) {
            throw new RuntimeException("User not found with ID: " + userId);
        }
        educationRepository.deleteByUserId(userId);
    }

    public long getEducationCountByUserId(String userId) {
        if (!userRepository.existsById(userId)) {
            throw new RuntimeException("User not found with ID: " + userId);
        }
        return educationRepository.countByUserId(userId);
    }

    private void validateEducationDTO(EducationDTO educationDTO) {
        if (educationDTO.getUserId() == null || educationDTO.getUserId().trim().isEmpty()) {
            throw new RuntimeException("User ID is required");
        }

        if (educationDTO.getInstitutionName() == null || educationDTO.getInstitutionName().trim().isEmpty()) {
            throw new RuntimeException("Institution name is required");
        }
    }

    private EducationDTO convertToDTO(Education education) {
        EducationDTO dto = new EducationDTO();
        dto.setId(education.getId());
        dto.setUserId(education.getUserId());
        dto.setInstitutionName(education.getInstitutionName());
        dto.setEndDate(education.getEndDate());
        return dto;
    }

    private Education convertToEntity(EducationDTO dto) {
        Education education = new Education();
        education.setUserId(dto.getUserId());
        education.setInstitutionName(dto.getInstitutionName());
        education.setEndDate(dto.getEndDate());
        return education;
    }

    private void updateEducationFields(Education education, EducationDTO dto) {
        education.setUserId(dto.getUserId());
        education.setInstitutionName(dto.getInstitutionName());
        education.setEndDate(dto.getEndDate());
    }

    // In EducationService.java
    public List<String> getInstitutionNamesByUserId(String userId) {
        // Validate user exists
        if (!userRepository.existsById(userId)) {
            throw new RuntimeException("User not found with ID: " + userId);
        }

        return educationRepository.findDistinctInstitutionNameByUserId(userId);
    }
}