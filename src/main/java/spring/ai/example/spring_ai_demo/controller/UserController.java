package spring.ai.example.spring_ai_demo.controller;

import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.PagedModel;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder;
import spring.ai.example.spring_ai_demo.dto.UserDTO;
import spring.ai.example.spring_ai_demo.model.User;
import spring.ai.example.spring_ai_demo.repository.UserRepository;
import org.springframework.http.HttpStatus;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;

@RestController
@RequestMapping("/api/v1/users")
@Tag(name = "User Management", description = "APIs for managing users")
public class UserController {

    private final UserRepository userRepository;

    public UserController(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Operation(summary = "Get all users")
    @ApiResponse(responseCode = "200", description = "List of users retrieved")
    @GetMapping
    public ResponseEntity<PagedModel<EntityModel<UserDTO>>> getAllUsers(
            @RequestParam(required = false) String name,
            @RequestParam(required = false) User.UserStatus status,
            @PageableDefault(size = 20) Pageable pageable) {
        
        Page<User> users;
        if (name != null) {
            users = userRepository.findByNameContainingIgnoreCase(name, pageable);
        } else if (status != null) {
            users = userRepository.findByStatus(status, pageable);
        } else {
            users = userRepository.findAll(pageable);
        }

        Page<EntityModel<UserDTO>> userModels = users.map(user -> EntityModel.of(convertToDTO(user))
            .add(WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(UserController.class)
                .getUserById(user.getId())).withSelfRel())
            .add(WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(UserController.class)
                .updateUser(user.getId(), null)).withRel("update"))
            .add(WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(UserController.class)
                .deleteUser(user.getId())).withRel("delete")));

        return ResponseEntity.ok(PagedModel.of(
            userModels.getContent(),
            new PagedModel.PageMetadata(
                users.getSize(),
                users.getNumber(),
                users.getTotalElements(),
                users.getTotalPages()
            )
        ));
    }

    @Operation(summary = "Create a new user")
    @ApiResponse(responseCode = "201", description = "User created successfully")
    @ApiResponse(responseCode = "400", description = "Invalid input")
    @PostMapping
    public ResponseEntity<EntityModel<UserDTO>> createUser(@Valid @RequestBody UserDTO userDTO) {
        if (userRepository.existsByEmail(userDTO.getEmail())) {
            return ResponseEntity.status(HttpStatus.CONFLICT).build();
        }

        User user = convertToEntity(userDTO);
        User savedUser = userRepository.save(user);
        
        EntityModel<UserDTO> resource = EntityModel.of(convertToDTO(savedUser))
            .add(WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(UserController.class)
                .getUserById(savedUser.getId())).withSelfRel())
            .add(WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(UserController.class)
                .getAllUsers(null, null, null)).withRel("all-users"));

        return ResponseEntity
            .created(WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(UserController.class)
                .getUserById(savedUser.getId())).toUri())
            .body(resource);
    }

    @Operation(summary = "Get user by ID")
    @ApiResponse(responseCode = "200", description = "User found")
    @ApiResponse(responseCode = "404", description = "User not found")
    @GetMapping("/{id}")
    public ResponseEntity<EntityModel<UserDTO>> getUserById(@PathVariable Long id) {
        return userRepository.findById(id)
            .map(user -> EntityModel.of(convertToDTO(user))
                .add(WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(UserController.class)
                    .getUserById(id)).withSelfRel())
                .add(WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(UserController.class)
                    .getAllUsers(null, null, null)).withRel("all-users")))
            .map(ResponseEntity::ok)
            .orElse(ResponseEntity.notFound().build());
    }

    @Operation(summary = "Update user")
    @ApiResponse(responseCode = "200", description = "User updated successfully")
    @ApiResponse(responseCode = "404", description = "User not found")
    @PutMapping("/{id}")
    public ResponseEntity<EntityModel<UserDTO>> updateUser(
            @PathVariable Long id,
            @Valid @RequestBody UserDTO userDTO) {
        
        return userRepository.findById(id)
            .map(existingUser -> {
                // Update only non-null fields
                if (userDTO.getName() != null) {
                    existingUser.setName(userDTO.getName());
                }
                if (userDTO.getEmail() != null) {
                    existingUser.setEmail(userDTO.getEmail());
                }
                if (userDTO.getPhoneNumber() != null) {
                    existingUser.setPhoneNumber(userDTO.getPhoneNumber());
                }
                if (userDTO.getStatus() != null) {
                    existingUser.setStatus(userDTO.getStatus());
                }
                
                User updatedUser = userRepository.save(existingUser);
                EntityModel<UserDTO> resource = EntityModel.of(convertToDTO(updatedUser))
                    .add(WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(UserController.class)
                        .getUserById(updatedUser.getId())).withSelfRel());
                
                return ResponseEntity.ok(resource);
            })
            .orElse(ResponseEntity.notFound().build());
    }

    @Operation(summary = "Delete user")
    @ApiResponse(responseCode = "204", description = "User deleted successfully")
    @ApiResponse(responseCode = "404", description = "User not found")
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteUser(@PathVariable Long id) {
        return userRepository.findById(id)
            .map(user -> {
                userRepository.delete(user);
                return ResponseEntity.noContent().build();
            })
            .orElse(ResponseEntity.notFound().build());
    }

    // Helper methods for DTO conversion
    private UserDTO convertToDTO(User user) {
        UserDTO dto = new UserDTO();
        dto.setId(user.getId());
        dto.setName(user.getName());
        dto.setEmail(user.getEmail());
        dto.setPhoneNumber(user.getPhoneNumber());
        dto.setStatus(user.getStatus());
        return dto;
    }

    private User convertToEntity(UserDTO dto) {
        User user = new User();
        user.setName(dto.getName());
        user.setEmail(dto.getEmail());
        user.setPhoneNumber(dto.getPhoneNumber());
        user.setStatus(dto.getStatus() != null ? dto.getStatus() : User.UserStatus.ACTIVE);
        return user;
    }
} 