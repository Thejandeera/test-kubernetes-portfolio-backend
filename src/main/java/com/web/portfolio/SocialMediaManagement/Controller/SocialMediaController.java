package com.web.portfolio.SocialMediaManagement.Controller;

import com.web.portfolio.SocialMediaManagement.DTO.SocialMediaDTO;
import com.web.portfolio.SocialMediaManagement.Exception.UserNotFoundException;
import com.web.portfolio.SocialMediaManagement.Service.SocialMediaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/social-media")
@CrossOrigin(origins = "*")
public class SocialMediaController {

    @Autowired
    private SocialMediaService socialMediaService;

    @GetMapping("/user/{userId}")
    public ResponseEntity<SocialMediaDTO> getSocialMediaByUserId(@PathVariable String userId) {
        return socialMediaService.getSocialMediaByUserId(userId)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updateSocialMedia(@PathVariable String id, @RequestBody SocialMediaDTO socialMediaDTO) {
        try {
            SocialMediaDTO updatedSocialMedia = socialMediaService.updateSocialMedia(id, socialMediaDTO);
            return ResponseEntity.ok(updatedSocialMedia);
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }


    @PostMapping
    public ResponseEntity<?> createOrUpdateSocialMedia(@RequestBody SocialMediaDTO socialMediaDTO) {
        try {
            // Check if social media already exists for this user
            if (socialMediaService.getSocialMediaByUserId(socialMediaDTO.getUserId()).isPresent()) {
                // Update existing record
                SocialMediaDTO updatedSocialMedia = socialMediaService.updateSocialMediaByUserId(
                        socialMediaDTO.getUserId(),
                        socialMediaDTO
                );
                return ResponseEntity.ok(updatedSocialMedia);
            } else {
                // Create new record
                SocialMediaDTO createdSocialMedia = socialMediaService.createSocialMedia(socialMediaDTO);
                return ResponseEntity.status(HttpStatus.CREATED).body(createdSocialMedia);
            }
        } catch (UserNotFoundException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteSocialMedia(@PathVariable String id) {
        socialMediaService.deleteSocialMedia(id);
        return ResponseEntity.noContent().build();
    }
}