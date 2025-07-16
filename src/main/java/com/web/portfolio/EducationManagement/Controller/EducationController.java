
// EducationController.java
package com.web.portfolio.EducationManagement.Controller;

import com.web.portfolio.EducationManagement.DTO.EducationDTO;
import com.web.portfolio.EducationManagement.Service.EducationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/education")
@CrossOrigin(origins = "*")
public class EducationController {

    @Autowired
    private EducationService educationService;

    @GetMapping
    public ResponseEntity<List<EducationDTO>> getAllEducation() {
        List<EducationDTO> education = educationService.getAllEducation();
        return ResponseEntity.ok(education);
    }

    @GetMapping("/user/{userId}")
    public ResponseEntity<?> getEducationByUserId(@PathVariable String userId) {
        try {
            List<EducationDTO> education = educationService.getEducationByUserId(userId);
            return ResponseEntity.ok(education);
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(createErrorResponse(e.getMessage()));
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<EducationDTO> getEducationById(@PathVariable String id) {
        return educationService.getEducationById(id)
                .map(education -> ResponseEntity.ok(education))
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<?> createEducation(@RequestBody EducationDTO educationDTO) {
        try {
            EducationDTO createdEducation = educationService.createEducation(educationDTO);
            return ResponseEntity.status(HttpStatus.CREATED).body(createdEducation);
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(createErrorResponse(e.getMessage()));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(createErrorResponse("Error creating education: " + e.getMessage()));
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updateEducation(@PathVariable String id, @RequestBody EducationDTO educationDTO) {
        try {
            EducationDTO updatedEducation = educationService.updateEducation(id, educationDTO);
            return ResponseEntity.ok(updatedEducation);
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(createErrorResponse(e.getMessage()));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(createErrorResponse("Error updating education: " + e.getMessage()));
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteEducation(@PathVariable String id) {
        try {
            educationService.deleteEducation(id);
            return ResponseEntity.noContent().build();
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(createErrorResponse(e.getMessage()));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(createErrorResponse("Error deleting education: " + e.getMessage()));
        }
    }

    private Map<String, String> createErrorResponse(String message) {
        Map<String, String> errorResponse = new HashMap<>();
        errorResponse.put("error", message);
        return errorResponse;
    }

    // In EducationController.java
    @GetMapping("/user/{userId}/institutions")
    public ResponseEntity<?> getInstitutionNamesByUserId(@PathVariable String userId) {
        try {
            List<String> institutions = educationService.getInstitutionNamesByUserId(userId);
            return ResponseEntity.ok(institutions);
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(createErrorResponse(e.getMessage()));
        }
    }
}