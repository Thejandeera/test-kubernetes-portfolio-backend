package com.web.portfolio.ProjectManagement.Service;

import com.web.portfolio.ProjectManagement.DTO.ProjectDTO;
import com.web.portfolio.ProjectManagement.Entity.Project;
import com.web.portfolio.ProjectManagement.Repository.ProjectRepository;
import com.web.portfolio.UserManagement.Repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;

import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class ProjectService {

    @Autowired
    private ProjectRepository projectRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private Cloudinary cloudinary;

    public List<ProjectDTO> getAllProjects() {
        return projectRepository.findAll().stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    public List<ProjectDTO> getProjectsByUserId(String userId) {
        return projectRepository.findByUserId(userId).stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    public Optional<ProjectDTO> getProjectById(String id) {
        return projectRepository.findById(id)
                .map(this::convertToDTO);
    }

    public ProjectDTO createProject(ProjectDTO projectDTO, MultipartFile image1, MultipartFile image2,
                                    MultipartFile image3, MultipartFile image4) throws IOException {
        // Validate user exists
        if (!userRepository.existsById(projectDTO.getUserId())) {
            throw new RuntimeException("User with ID " + projectDTO.getUserId() + " not found");
        }

        Project project = convertToEntity(projectDTO);

        // Upload images to Cloudinary if provided
        if (image1 != null && !image1.isEmpty()) {
            project.setImage1(uploadImageToCloudinary(image1));
        }
        if (image2 != null && !image2.isEmpty()) {
            project.setImage2(uploadImageToCloudinary(image2));
        }
        if (image3 != null && !image3.isEmpty()) {
            project.setImage3(uploadImageToCloudinary(image3));
        }
        if (image4 != null && !image4.isEmpty()) {
            project.setImage4(uploadImageToCloudinary(image4));
        }

        Project savedProject = projectRepository.save(project);
        return convertToDTO(savedProject);
    }

    public ProjectDTO updateProject(String id, ProjectDTO projectDTO, MultipartFile image1,
                                    MultipartFile image2, MultipartFile image3, MultipartFile image4) throws IOException {
        // Validate user exists
        if (!userRepository.existsById(projectDTO.getUserId())) {
            throw new RuntimeException("User with ID " + projectDTO.getUserId() + " not found");
        }

        Project project = projectRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Project not found"));

        // Store existing images for reference
        String[] existingImages = {
                project.getImage1(),
                project.getImage2(),
                project.getImage3(),
                project.getImage4()
        };

        // Process new images first
        if (image1 != null && !image1.isEmpty()) {
            project.setImage1(uploadImageToCloudinary(image1));
        } else {
            project.setImage1("KEEP".equals(projectDTO.getImage1()) ? existingImages[0] : null);
        }

        if (image2 != null && !image2.isEmpty()) {
            project.setImage2(uploadImageToCloudinary(image2));
        } else {
            project.setImage2("KEEP".equals(projectDTO.getImage2()) ? existingImages[1] : null);
        }

        if (image3 != null && !image3.isEmpty()) {
            project.setImage3(uploadImageToCloudinary(image3));
        } else {
            project.setImage3("KEEP".equals(projectDTO.getImage3()) ? existingImages[2] : null);
        }

        if (image4 != null && !image4.isEmpty()) {
            project.setImage4(uploadImageToCloudinary(image4));
        } else {
            project.setImage4("KEEP".equals(projectDTO.getImage4()) ? existingImages[3] : null);
        }

        // Compact the images (remove nulls and shift remaining images forward)
        String[] compactedImages = new String[4];
        int index = 0;
        for (String img : new String[]{project.getImage1(), project.getImage2(), project.getImage3(), project.getImage4()}) {
            if (img != null) {
                compactedImages[index++] = img;
            }
        }

        // Update project with compacted images
        project.setImage1(index > 0 ? compactedImages[0] : null);
        project.setImage2(index > 1 ? compactedImages[1] : null);
        project.setImage3(index > 2 ? compactedImages[2] : null);
        project.setImage4(index > 3 ? compactedImages[3] : null);

        updateProjectFields(project, projectDTO);
        Project savedProject = projectRepository.save(project);
        return convertToDTO(savedProject);
    }

    public void deleteProject(String id) {
        projectRepository.deleteById(id);
    }

    public String uploadImage(MultipartFile file) throws IOException {
        return uploadImageToCloudinary(file);
    }

    private String uploadImageToCloudinary(MultipartFile file) throws IOException {
        try {
            Map<String, Object> uploadOptions = ObjectUtils.asMap(
                    "folder", "project_images",
                    "resource_type", "image",
                    "quality", "auto",
                    "fetch_format", "auto"
            );

            Map uploadResult = cloudinary.uploader().upload(file.getBytes(), uploadOptions);
            return uploadResult.get("secure_url").toString();
        } catch (IOException e) {
            throw new IOException("Failed to upload image to Cloudinary: " + e.getMessage());
        }
    }

    private ProjectDTO convertToDTO(Project project) {
        ProjectDTO dto = new ProjectDTO();
        dto.setId(project.getId());
        dto.setUserId(project.getUserId());
        dto.setImage1(project.getImage1());
        dto.setImage2(project.getImage2());
        dto.setImage3(project.getImage3());
        dto.setImage4(project.getImage4());
        dto.setProjectName(project.getProjectName());
        dto.setStartDate(project.getStartDate());
        dto.setEndDate(project.getEndDate());
        dto.setProjectLink(project.getProjectLink());
        dto.setDescription(project.getDescription());
        dto.setSkills(project.getSkills());
        return dto;
    }

    private Project convertToEntity(ProjectDTO dto) {
        Project project = new Project();
        project.setUserId(dto.getUserId());
        project.setImage1(dto.getImage1());
        project.setImage2(dto.getImage2());
        project.setImage3(dto.getImage3());
        project.setImage4(dto.getImage4());
        project.setProjectName(dto.getProjectName());
        project.setStartDate(dto.getStartDate());
        project.setEndDate(dto.getEndDate());
        project.setProjectLink(dto.getProjectLink());
        project.setDescription(dto.getDescription());
        project.setSkills(dto.getSkills());
        return project;
    }

    private void updateProjectFields(Project project, ProjectDTO dto) {
        if (dto.getProjectName() != null) {
            project.setProjectName(dto.getProjectName());
        }
        if (dto.getStartDate() != null) {
            project.setStartDate(dto.getStartDate());
        }
        if (dto.getEndDate() != null) {
            project.setEndDate(dto.getEndDate());
        }
        if (dto.getProjectLink() != null) {
            project.setProjectLink(dto.getProjectLink());
        }
        if (dto.getDescription() != null) {
            project.setDescription(dto.getDescription());
        }
        if (dto.getSkills() != null) {
            project.setSkills(dto.getSkills());
        }
    }
}