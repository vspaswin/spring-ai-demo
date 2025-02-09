package spring.ai.example.spring_ai_demo.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import spring.ai.example.spring_ai_demo.dto.UserDTO;
import spring.ai.example.spring_ai_demo.model.User;
import spring.ai.example.spring_ai_demo.repository.UserRepository;

import java.util.List;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(UserController.class)
class UserControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private UserRepository userRepository;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void shouldCreateUser() throws Exception {
        UserDTO userDTO = new UserDTO();
        userDTO.setName("Test User");
        userDTO.setEmail("test@example.com");
        userDTO.setPhoneNumber("+1234567890");

        User savedUser = new User();
        savedUser.setId(1L);
        savedUser.setName(userDTO.getName());
        savedUser.setEmail(userDTO.getEmail());
        savedUser.setPhoneNumber(userDTO.getPhoneNumber());

        when(userRepository.existsByEmail(any())).thenReturn(false);
        when(userRepository.save(any())).thenReturn(savedUser);

        mockMvc.perform(post("/api/v1/users")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(userDTO)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.name").value("Test User"));
    }

    @Test
    void shouldNotCreateUserWithExistingEmail() throws Exception {
        UserDTO userDTO = new UserDTO();
        userDTO.setName("Test User");
        userDTO.setEmail("existing@example.com");
        userDTO.setPhoneNumber("+1234567890");

        when(userRepository.existsByEmail("existing@example.com")).thenReturn(true);

        mockMvc.perform(post("/api/v1/users")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(userDTO)))
                .andExpect(status().isConflict());
    }

    @Test
    void shouldNotCreateUserWithInvalidData() throws Exception {
        UserDTO userDTO = new UserDTO();
        userDTO.setName("");  // Invalid name
        userDTO.setEmail("invalid-email");  // Invalid email
        userDTO.setPhoneNumber("123");  // Invalid phone

        mockMvc.perform(post("/api/v1/users")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(userDTO)))
                .andExpect(status().isBadRequest());
    }

    @Test
    void shouldGetAllUsers() throws Exception {
        User user = new User();
        user.setId(1L);
        user.setName("Test User");
        user.setEmail("test@example.com");
        user.setStatus(User.UserStatus.ACTIVE);

        when(userRepository.findAll(any(Pageable.class)))
                .thenReturn(new PageImpl<>(List.of(user)));

        mockMvc.perform(get("/api/v1/users"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$._embedded.userDTOList[0].id").value(1))
                .andExpect(jsonPath("$._embedded.userDTOList[0].name").value("Test User"));
    }

    @Test
    void shouldSearchUsersByName() throws Exception {
        User user = new User();
        user.setId(1L);
        user.setName("John Doe");
        user.setEmail("john@example.com");

        when(userRepository.findByNameContainingIgnoreCase(eq("John"), any(Pageable.class)))
                .thenReturn(new PageImpl<>(List.of(user)));

        mockMvc.perform(get("/api/v1/users?name=John"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$._embedded.userDTOList[0].name").value("John Doe"));
    }

    @Test
    void shouldFilterUsersByStatus() throws Exception {
        User user = new User();
        user.setId(1L);
        user.setName("Active User");
        user.setStatus(User.UserStatus.ACTIVE);

        when(userRepository.findByStatus(eq(User.UserStatus.ACTIVE), any(Pageable.class)))
                .thenReturn(new PageImpl<>(List.of(user)));

        mockMvc.perform(get("/api/v1/users?status=ACTIVE"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$._embedded.userDTOList[0].status").value("ACTIVE"));
    }

    @Test
    void shouldGetUserById() throws Exception {
        User user = new User();
        user.setId(1L);
        user.setName("Test User");
        user.setEmail("test@example.com");

        when(userRepository.findById(1L)).thenReturn(Optional.of(user));

        mockMvc.perform(get("/api/v1/users/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("Test User"));
    }

    @Test
    void shouldReturn404WhenUserNotFound() throws Exception {
        when(userRepository.findById(1L)).thenReturn(Optional.empty());

        mockMvc.perform(get("/api/v1/users/1"))
                .andExpect(status().isNotFound());
    }

    @Test
    void shouldUpdateUser() throws Exception {
        UserDTO userDTO = new UserDTO();
        userDTO.setName("Updated User");
        userDTO.setEmail("updated@example.com");
        userDTO.setPhoneNumber("+1987654321");

        User existingUser = new User();
        existingUser.setId(1L);
        existingUser.setName("Original User");
        existingUser.setEmail("original@example.com");

        User updatedUser = new User();
        updatedUser.setId(1L);
        updatedUser.setName(userDTO.getName());
        updatedUser.setEmail(userDTO.getEmail());
        updatedUser.setPhoneNumber(userDTO.getPhoneNumber());

        when(userRepository.findById(1L)).thenReturn(Optional.of(existingUser));
        when(userRepository.save(any())).thenReturn(updatedUser);

        mockMvc.perform(put("/api/v1/users/1")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(userDTO)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("Updated User"))
                .andExpect(jsonPath("$.email").value("updated@example.com"));
    }

    @Test
    void shouldNotUpdateNonExistentUser() throws Exception {
        UserDTO userDTO = new UserDTO();
        userDTO.setName("Updated User");
        userDTO.setEmail("updated@example.com");

        when(userRepository.findById(1L)).thenReturn(Optional.empty());

        mockMvc.perform(put("/api/v1/users/1")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(userDTO)))
                .andExpect(status().isNotFound());
    }

    @Test
    void shouldDeleteUser() throws Exception {
        User user = new User();
        user.setId(1L);
        when(userRepository.findById(1L)).thenReturn(Optional.of(user));

        mockMvc.perform(delete("/api/v1/users/1"))
                .andExpect(status().isNoContent());

        verify(userRepository).delete(user);
    }

    @Test
    void shouldReturn404WhenDeletingNonExistentUser() throws Exception {
        when(userRepository.findById(1L)).thenReturn(Optional.empty());

        mockMvc.perform(delete("/api/v1/users/1"))
                .andExpect(status().isNotFound());

        verify(userRepository, never()).delete(any());
    }
} 