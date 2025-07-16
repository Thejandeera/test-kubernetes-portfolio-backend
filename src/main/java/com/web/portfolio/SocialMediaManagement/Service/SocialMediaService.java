package com.web.portfolio.SocialMediaManagement.Service;

import com.web.portfolio.SocialMediaManagement.DTO.SocialMediaDTO;
import com.web.portfolio.SocialMediaManagement.Entity.SocialMedia;
import com.web.portfolio.SocialMediaManagement.Exception.UserNotFoundException;
import com.web.portfolio.SocialMediaManagement.Repository.SocialMediaRepository;
import com.web.portfolio.UserManagement.Repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class SocialMediaService {

    @Autowired
    private SocialMediaRepository socialMediaRepository;

    @Autowired
    private UserRepository userRepository;

    public List<SocialMediaDTO> getAllSocialMedia() {
        return socialMediaRepository.findAll().stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    public Optional<SocialMediaDTO> getSocialMediaByUserId(String userId) {
        return socialMediaRepository.findByUserId(userId)
                .map(this::convertToDTO);
    }

    public Optional<SocialMediaDTO> getSocialMediaById(String id) {
        return socialMediaRepository.findById(id)
                .map(this::convertToDTO);
    }

    public SocialMediaDTO createSocialMedia(SocialMediaDTO socialMediaDTO) {
        validateUserExists(socialMediaDTO.getUserId());

        SocialMedia socialMedia = convertToEntity(socialMediaDTO);
        SocialMedia savedSocialMedia = socialMediaRepository.save(socialMedia);
        return convertToDTO(savedSocialMedia);
    }

    public SocialMediaDTO updateSocialMedia(String id, SocialMediaDTO socialMediaDTO) {
        SocialMedia socialMedia = socialMediaRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Social Media not found"));

        // If userId is being updated, validate the new user exists
        if (socialMediaDTO.getUserId() != null &&
                !socialMediaDTO.getUserId().equals(socialMedia.getUserId())) {
            validateUserExists(socialMediaDTO.getUserId());
        }

        updateSocialMediaFields(socialMedia, socialMediaDTO);
        SocialMedia savedSocialMedia = socialMediaRepository.save(socialMedia);
        return convertToDTO(savedSocialMedia);
    }

    public SocialMediaDTO updateSocialMediaByUserId(String userId, SocialMediaDTO socialMediaDTO) {
        validateUserExists(userId);

        Optional<SocialMedia> existingSocialMedia = socialMediaRepository.findByUserId(userId);

        if (existingSocialMedia.isPresent()) {
            SocialMedia socialMedia = existingSocialMedia.get();
            updateSocialMediaFields(socialMedia, socialMediaDTO);
            SocialMedia savedSocialMedia = socialMediaRepository.save(socialMedia);
            return convertToDTO(savedSocialMedia);
        } else {
            socialMediaDTO.setUserId(userId);
            return createSocialMedia(socialMediaDTO);
        }
    }

    public void deleteSocialMedia(String id) {
        socialMediaRepository.deleteById(id);
    }

    private void validateUserExists(String userId) {
        if (!userRepository.existsById(userId)) {
            throw new UserNotFoundException(userId);
        }
    }

    private SocialMediaDTO convertToDTO(SocialMedia socialMedia) {
        SocialMediaDTO dto = new SocialMediaDTO();
        dto.setId(socialMedia.getId());
        dto.setUserId(socialMedia.getUserId());
        dto.setGitHubLink(socialMedia.getGitHubLink());
        dto.setLinkedInLink(socialMedia.getLinkedInLink());
        dto.setOtherLink(socialMedia.getOtherLink());
        return dto;
    }

    private SocialMedia convertToEntity(SocialMediaDTO dto) {
        SocialMedia socialMedia = new SocialMedia();
        socialMedia.setUserId(dto.getUserId());
        socialMedia.setGitHubLink(dto.getGitHubLink());
        socialMedia.setLinkedInLink(dto.getLinkedInLink());
        socialMedia.setOtherLink(dto.getOtherLink());
        return socialMedia;
    }

    private void updateSocialMediaFields(SocialMedia socialMedia, SocialMediaDTO dto) {
        if (dto.getGitHubLink() != null) {
            socialMedia.setGitHubLink(dto.getGitHubLink());
        }
        if (dto.getLinkedInLink() != null) {
            socialMedia.setLinkedInLink(dto.getLinkedInLink());
        }
        if (dto.getOtherLink() != null) {
            socialMedia.setOtherLink(dto.getOtherLink());
        }
        if (dto.getUserId() != null) {
            socialMedia.setUserId(dto.getUserId());
        }
    }
}