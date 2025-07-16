package com.web.portfolio.ExtraActivityManagement.Controller;

import com.web.portfolio.ExtraActivityManagement.DTO.ExtraActivityDTO;
import com.web.portfolio.ExtraActivityManagement.Exception.*;
import com.web.portfolio.ExtraActivityManagement.Service.ExtraActivityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/extra-activities")
@CrossOrigin(origins = "*")
public class ExtraActivityController {

    @Autowired
    private ExtraActivityService extraActivityService;

    @PostMapping
    public ResponseEntity<?> createExtraActivity(@RequestBody ExtraActivityDTO extraActivityDTO) {
        try {
            ExtraActivityDTO createdActivity = extraActivityService.createExtraActivity(extraActivityDTO);
            return ResponseEntity.status(HttpStatus.CREATED).body(createdActivity);
        } catch (UserNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(
                    new ErrorResponse(HttpStatus.NOT_FOUND.value(), "User Not Found", e.getMessage()));
        } catch (BadRequestException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(
                    new ErrorResponse(HttpStatus.BAD_REQUEST.value(), "Validation Error", e.getMessage()));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(
                    new ErrorResponse(HttpStatus.INTERNAL_SERVER_ERROR.value(),
                            "Internal Server Error", "Failed to create activity"));
        }
    }

    @GetMapping
    public ResponseEntity<?> getAllExtraActivities() {
        try {
            List<ExtraActivityDTO> extraActivities = extraActivityService.getAllExtraActivities();
            return ResponseEntity.ok(extraActivities);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(
                    new ErrorResponse(
                            HttpStatus.INTERNAL_SERVER_ERROR.value(),
                            "Internal Server Error",
                            "Failed to retrieve activities"
                    )
            );
        }
    }

    @GetMapping("/user/{userId}")
    public ResponseEntity<?> getExtraActivitiesByUserId(@PathVariable String userId) {
        try {
            List<ExtraActivityDTO> extraActivities = extraActivityService.getExtraActivitiesByUserId(userId);
            if (extraActivities.isEmpty()) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(
                        new ErrorResponse(
                                HttpStatus.NOT_FOUND.value(),
                                "Not Found",
                                "No activities found for user " + userId
                        )
                );
            }
            return ResponseEntity.ok(extraActivities);
        } catch (UserNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(
                    new ErrorResponse(
                            HttpStatus.NOT_FOUND.value(),
                            "User Not Found",
                            e.getMessage()
                    )
            );
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(
                    new ErrorResponse(
                            HttpStatus.INTERNAL_SERVER_ERROR.value(),
                            "Internal Server Error",
                            "Failed to retrieve user activities"
                    )
            );
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getExtraActivityById(@PathVariable String id) {
        try {
            Optional<ExtraActivityDTO> activity = extraActivityService.getExtraActivityById(id);

            if (activity.isPresent()) {
                return ResponseEntity.ok(activity.get());
            } else {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(
                        new ErrorResponse(
                                HttpStatus.NOT_FOUND.value(),
                                "Not Found",
                                "Activity not found with id " + id
                        )
                );
            }
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(
                    new ErrorResponse(
                            HttpStatus.INTERNAL_SERVER_ERROR.value(),
                            "Internal Server Error",
                            "Failed to retrieve activity"
                    )
            );
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updateExtraActivity(@PathVariable String id, @RequestBody ExtraActivityDTO extraActivityDTO) {
        try {
            ExtraActivityDTO updatedExtraActivity = extraActivityService.updateExtraActivity(id, extraActivityDTO);
            return ResponseEntity.ok(updatedExtraActivity);
        } catch (UserNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(
                    new ErrorResponse(
                            HttpStatus.NOT_FOUND.value(),
                            "User Not Found",
                            e.getMessage()
                    )
            );
        } catch (ResourceNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(
                    new ErrorResponse(
                            HttpStatus.NOT_FOUND.value(),
                            "Activity Not Found",
                            e.getMessage()
                    )
            );
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(
                    new ErrorResponse(
                            HttpStatus.BAD_REQUEST.value(),
                            "Validation Error",
                            e.getMessage()
                    )
            );
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(
                    new ErrorResponse(
                            HttpStatus.INTERNAL_SERVER_ERROR.value(),
                            "Internal Server Error",
                            "Failed to update activity"
                    )
            );
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteExtraActivity(@PathVariable String id) {
        try {
            extraActivityService.deleteExtraActivity(id);
            return ResponseEntity.noContent().build();
        } catch (ResourceNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(
                    new ErrorResponse(
                            HttpStatus.NOT_FOUND.value(),
                            "Activity Not Found",
                            e.getMessage()
                    )
            );
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(
                    new ErrorResponse(
                            HttpStatus.INTERNAL_SERVER_ERROR.value(),
                            "Internal Server Error",
                            "Failed to delete activity"
                    )
            );
        }
    }

    private static class ErrorResponse {
        private final int status;
        private final String error;
        private final String message;

        public ErrorResponse(int status, String error, String message) {
            this.status = status;
            this.error = error;
            this.message = message;
        }

        public int getStatus() { return status; }
        public String getError() { return error; }
        public String getMessage() { return message; }
    }
}