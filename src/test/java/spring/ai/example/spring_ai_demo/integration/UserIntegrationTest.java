package spring.ai.example.spring_ai_demo.integration;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import spring.ai.example.spring_ai_demo.dto.UserDTO;
import spring.ai.example.spring_ai_demo.config.TestContainersConfig;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class UserIntegrationTest extends TestContainersConfig {

    @LocalServerPort
    private int port;

    @Autowired
    private TestRestTemplate restTemplate;

    @Test
    void shouldCreateAndRetrieveUser() {
        // Given
        UserDTO userDTO = new UserDTO();
        userDTO.setName("Integration Test User");
        userDTO.setEmail("integration@test.com");
        userDTO.setPhoneNumber("+1234567890");

        // When
        ResponseEntity<UserDTO> createResponse = restTemplate.postForEntity(
            "/api/v1/users",
            userDTO,
            UserDTO.class
        );

        // Then
        assertThat(createResponse.getStatusCode()).isEqualTo(HttpStatus.CREATED);
        assertThat(createResponse.getBody().getId()).isNotNull();

        // When retrieving
        ResponseEntity<UserDTO> getResponse = restTemplate.getForEntity(
            "/api/v1/users/" + createResponse.getBody().getId(),
            UserDTO.class
        );

        // Then
        assertThat(getResponse.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(getResponse.getBody().getName()).isEqualTo("Integration Test User");
    }
} 