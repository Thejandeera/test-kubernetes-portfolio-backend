package com.web.portfolio.AllForOneUser.Controller;

import com.web.portfolio.AllForOneUser.Entity.UserProfileAggregate;
import com.web.portfolio.AllForOneUser.DTO.UserProfileDTO;
import com.web.portfolio.AllForOneUser.Service.UserProfileService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/api/user-profile")
@CrossOrigin(origins = "*")
public class UserProfileController {

    @Autowired
    private UserProfileService userProfileService;

    // ========================================================================================
    // USER ID BASED ENDPOINTS
    // ========================================================================================

    /**
     * Get all user data by user ID (returns full entities)
     *
     * @param userId The user ID
     * @return ResponseEntity containing UserProfileAggregate or error message
     */
    @GetMapping("/full/{userId}")
    public ResponseEntity<?> getAllUserData(@PathVariable String userId) {
        try {
            if (userId == null || userId.trim().isEmpty()) {
                return ResponseEntity.badRequest()
                        .body(new ErrorResponse("User ID cannot be null or empty"));
            }

            Optional<UserProfileAggregate> userProfile = userProfileService.getAllUserDataById(userId);

            if (userProfile.isPresent()) {
                return ResponseEntity.ok(new SuccessResponse<>("User profile retrieved successfully", userProfile.get()));
            } else {
                return ResponseEntity.status(HttpStatus.NOT_FOUND)
                        .body(new ErrorResponse("User not found with ID: " + userId));
            }
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ErrorResponse("Error retrieving user profile: " + e.getMessage()));
        }
    }

    /**
     * Get all user data by user ID (returns DTO)
     *
     * @param userId The user ID
     * @return ResponseEntity containing UserProfileDTO or error message
     */
    @GetMapping("/{userId}")
    public ResponseEntity<?> getAllUserDataDTO(@PathVariable String userId) {
        try {
            if (userId == null || userId.trim().isEmpty()) {
                return ResponseEntity.badRequest()
                        .body(new ErrorResponse("User ID cannot be null or empty"));
            }

            Optional<UserProfileDTO> userProfileDTO = userProfileService.getAllUserDataDTOById(userId);

            if (userProfileDTO.isPresent()) {
                return ResponseEntity.ok(new SuccessResponse<>("User profile retrieved successfully", userProfileDTO.get()));
            } else {
                return ResponseEntity.status(HttpStatus.NOT_FOUND)
                        .body(new ErrorResponse("User not found with ID: " + userId));
            }
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ErrorResponse("Error retrieving user profile: " + e.getMessage()));
        }
    }

    /**
     * Check if user exists by user ID
     *
     * @param userId The user ID
     * @return ResponseEntity indicating if user exists
     */
    @GetMapping("/exists/{userId}")
    public ResponseEntity<?> checkUserExists(@PathVariable String userId) {
        try {
            if (userId == null || userId.trim().isEmpty()) {
                return ResponseEntity.badRequest()
                        .body(new ErrorResponse("User ID cannot be null or empty"));
            }

            boolean exists = userProfileService.userExists(userId);
            return ResponseEntity.ok(new SuccessResponse<>("User existence check completed", exists));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ErrorResponse("Error checking user existence: " + e.getMessage()));
        }
    }

    // ========================================================================================
    // USERNAME BASED ENDPOINTS
    // ========================================================================================

    /**
     * Get all user data by username (returns full entities)
     *
     * @param username The username
     * @return ResponseEntity containing UserProfileAggregate or error message
     */
    @GetMapping("/full/by-username/{username}")
    public ResponseEntity<?> getAllUserDataByUsername(@PathVariable String username) {
        try {
            if (username == null || username.trim().isEmpty()) {
                return ResponseEntity.badRequest()
                        .body(new ErrorResponse("Username cannot be null or empty"));
            }

            Optional<UserProfileAggregate> userProfile = userProfileService.getAllUserDataByUsername(username);

            if (userProfile.isPresent()) {
                return ResponseEntity.ok(new SuccessResponse<>("User profile retrieved successfully", userProfile.get()));
            } else {
                return ResponseEntity.status(HttpStatus.NOT_FOUND)
                        .body(new ErrorResponse("User not found with username: " + username));
            }
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ErrorResponse("Error retrieving user profile: " + e.getMessage()));
        }
    }

    /**
     * Get all user data by username (returns DTO)
     *
     * @param username The username
     * @return ResponseEntity containing UserProfileDTO or error message
     */
    @GetMapping("/by-username/{username}")
    public ResponseEntity<?> getAllUserDataDTOByUsername(@PathVariable String username) {
        try {
            if (username == null || username.trim().isEmpty()) {
                return ResponseEntity.badRequest()
                        .body(new ErrorResponse("Username cannot be null or empty"));
            }

            Optional<UserProfileDTO> userProfileDTO = userProfileService.getAllUserDataDTOByUsername(username);

            if (userProfileDTO.isPresent()) {
                return ResponseEntity.ok(new SuccessResponse<>("User profile retrieved successfully", userProfileDTO.get()));
            } else {
                return ResponseEntity.status(HttpStatus.NOT_FOUND)
                        .body(new ErrorResponse("User not found with username: " + username));
            }
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ErrorResponse("Error retrieving user profile: " + e.getMessage()));
        }
    }

    /**
     * Check if user exists by username
     *
     * @param username The username
     * @return ResponseEntity indicating if user exists
     */
    @GetMapping("/exists/by-username/{username}")
    public ResponseEntity<?> checkUserExistsByUsername(@PathVariable String username) {
        try {
            if (username == null || username.trim().isEmpty()) {
                return ResponseEntity.badRequest()
                        .body(new ErrorResponse("Username cannot be null or empty"));
            }

            boolean exists = userProfileService.userExistsByUsername(username);
            return ResponseEntity.ok(new SuccessResponse<>("User existence check completed", exists));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ErrorResponse("Error checking user existence: " + e.getMessage()));
        }
    }

    // ========================================================================================
    // RESPONSE WRAPPER CLASSES
    // ========================================================================================

    public static class SuccessResponse<T> {
        private String message;
        private T data;
        private boolean success = true;

        public SuccessResponse(String message, T data) {
            this.message = message;
            this.data = data;
        }

        // Getters and Setters
        public String getMessage() { return message; }
        public void setMessage(String message) { this.message = message; }
        public T getData() { return data; }
        public void setData(T data) { this.data = data; }
        public boolean isSuccess() { return success; }
        public void setSuccess(boolean success) { this.success = success; }
    }

    public static class ErrorResponse {
        private String message;
        private boolean success = false;

        public ErrorResponse(String message) {
            this.message = message;
        }

        // Getters and Setters
        public String getMessage() { return message; }
        public void setMessage(String message) { this.message = message; }
        public boolean isSuccess() { return success; }
        public void setSuccess(boolean success) { this.success = success; }
    }
}