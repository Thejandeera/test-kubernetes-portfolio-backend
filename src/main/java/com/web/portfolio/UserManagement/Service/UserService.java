package com.web.portfolio.UserManagement.Service;

import com.web.portfolio.UserManagement.DTO.LoginDTO;
import com.web.portfolio.UserManagement.DTO.LoginResponseDTO;
import com.web.portfolio.UserManagement.DTO.UserDTO;
import com.web.portfolio.UserManagement.DTO.UserCreateDTO;
import com.web.portfolio.UserManagement.Entity.User;
import com.web.portfolio.UserManagement.Repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
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
public class UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private Cloudinary cloudinary;

    @Autowired
    private PasswordEncoder passwordEncoder;

    public List<UserDTO> getAllUsers() {
        return userRepository.findAll().stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    public Optional<UserDTO> getUserById(String id) {
        return userRepository.findById(id)
                .map(this::convertToDTO);
    }

    public Optional<UserDTO> getUserByUserName(String userName) {
        return userRepository.findByUserName(userName)
                .map(this::convertToDTO);
    }

    public Optional<UserDTO> getUserByEmail(String email) {
        return userRepository.findByEmail(email)
                .map(this::convertToDTO);
    }

    public LoginResponseDTO login(LoginDTO loginDTO) {
        try {
            Optional<User> userOptional = userRepository.findByEmail(loginDTO.getEmail());

            if (userOptional.isEmpty()) {
                return new LoginResponseDTO(false, "User not found with this email", null);
            }

            User user = userOptional.get();

            // Check if password matches
            if (passwordEncoder.matches(loginDTO.getPassword(), user.getPassword())) {
                UserDTO userDTO = convertToDTO(user);
                return new LoginResponseDTO(true, "Login successful", userDTO);
            } else {
                return new LoginResponseDTO(false, "Invalid password", null);
            }

        } catch (Exception e) {
            return new LoginResponseDTO(false, "Login failed: " + e.getMessage(), null);
        }
    }

    public UserDTO createUser(UserCreateDTO userCreateDTO, MultipartFile imageFile) throws IOException {
        if (userRepository.existsByUserName(userCreateDTO.getUserName())) {
            throw new RuntimeException("Username already exists");
        }
        if (userRepository.existsByEmail(userCreateDTO.getEmail())) {
            throw new RuntimeException("Email already exists");
        }

        // Upload image to Cloudinary if provided
        String imageUrl = null;
        if (imageFile != null && !imageFile.isEmpty()) {
            imageUrl = uploadImageToCloudinary(imageFile);
        }

        User user = convertToEntity(userCreateDTO, imageUrl);
        // Encrypt password before saving
        user.setPassword(passwordEncoder.encode(userCreateDTO.getPassword()));

        User savedUser = userRepository.save(user);
        return convertToDTO(savedUser);
    }

    public UserDTO updateUser(String id, UserCreateDTO userCreateDTO, MultipartFile imageFile) throws IOException {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("User not found"));

        // Check if username or email already exists for other users
        if (!user.getUserName().equals(userCreateDTO.getUserName()) &&
                userRepository.existsByUserName(userCreateDTO.getUserName())) {
            throw new RuntimeException("Username already exists");
        }
        if (!user.getEmail().equals(userCreateDTO.getEmail()) &&
                userRepository.existsByEmail(userCreateDTO.getEmail())) {
            throw new RuntimeException("Email already exists");
        }

        // Upload new image to Cloudinary if provided
        String imageUrl = user.getImage(); // Keep existing image URL
        if (imageFile != null && !imageFile.isEmpty()) {
            imageUrl = uploadImageToCloudinary(imageFile);
        }

        updateUserFields(user, userCreateDTO, imageUrl);
        User savedUser = userRepository.save(user);
        return convertToDTO(savedUser);
    }

    public void deleteUser(String id) {
        userRepository.deleteById(id);
    }

    public String uploadImage(MultipartFile file) throws IOException {
        return uploadImageToCloudinary(file);
    }

    private String uploadImageToCloudinary(MultipartFile file) throws IOException {
        try {
            Map<String, Object> uploadOptions = ObjectUtils.asMap(
                    "folder", "user_images",
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

    private UserDTO convertToDTO(User user) {
        return new UserDTO(
                user.getId(),
                user.getImage(),
                user.getFullName(),
                user.getUserName(),
                user.getEmail(),
                user.getRole(),
                user.getInstitution()
        );
    }

    private User convertToEntity(UserCreateDTO dto, String imageUrl) {
        return new User(
                imageUrl,
                dto.getFullName(),
                dto.getUserName(),
                dto.getEmail(),
                dto.getRole(),
                dto.getInstitution(),
                dto.getPassword() // Password will be encoded in createUser method
        );
    }

    private void updateUserFields(User user, UserCreateDTO dto, String imageUrl) {
        if (imageUrl != null) {
            user.setImage(imageUrl);
        }
        user.setFullName(dto.getFullName());
        user.setUserName(dto.getUserName());
        user.setEmail(dto.getEmail());
        user.setRole(dto.getRole());
        user.setInstitution(dto.getInstitution());

        // Only update password if provided and encrypt it
        if (dto.getPassword() != null && !dto.getPassword().isEmpty()) {
            user.setPassword(passwordEncoder.encode(dto.getPassword()));
        }
    }
}