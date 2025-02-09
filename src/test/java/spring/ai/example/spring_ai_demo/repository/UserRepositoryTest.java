package spring.ai.example.spring_ai_demo.repository;

import java.util.List;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.test.context.ActiveProfiles;
import spring.ai.example.spring_ai_demo.config.TestContainersConfig;
import spring.ai.example.spring_ai_demo.config.TestJpaConfig;
import org.springframework.context.annotation.Import;
import spring.ai.example.spring_ai_demo.model.User;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
@ActiveProfiles("test")
@Import(TestJpaConfig.class)
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class UserRepositoryTest extends TestContainersConfig {

    @Autowired
    private UserRepository userRepository;

    @Test
    void shouldSaveUser() {
        // Given
        User user = new User();
        user.setName("Test User");
        user.setEmail("test@example.com");
        user.setPhoneNumber("+1234567890");

        // When
        User savedUser = userRepository.save(user);

        // Then
        assertThat(savedUser.getId()).isNotNull();
        assertThat(savedUser.getName()).isEqualTo("Test User");
        assertThat(savedUser.getEmail()).isEqualTo("test@example.com");
    }

    @Test
    void shouldFindByEmail() {
        // Given
        User user = new User();
        user.setName("Test User");
        user.setEmail("test@example.com");
        user.setPhoneNumber("+1234567890");
        userRepository.save(user);

        // When
        boolean exists = userRepository.existsByEmail("test@example.com");

        // Then
        assertThat(exists).isTrue();
    }

    @Test
    void shouldFindByNameContaining() {
        // Given
        User user1 = createUser("John Doe", "john@example.com");
        User user2 = createUser("Jane Doe", "jane@example.com");
        userRepository.saveAll(List.of(user1, user2));

        // When
        Page<User> users = userRepository.findByNameContainingIgnoreCase(
            "doe", 
            PageRequest.of(0, 10)
        );

        // Then
        assertThat(users.getContent()).hasSize(2);
        assertThat(users.getContent()).extracting("name")
            .allMatch(name -> ((String) name).contains("Doe"));
    }

    @Test
    void shouldFindByStatus() {
        // Given
        User user = new User();
        user.setName("Test User");
        user.setEmail("test@example.com");
        user.setStatus(User.UserStatus.ACTIVE);
        userRepository.save(user);

        // When
        Page<User> users = userRepository.findByStatus(
            User.UserStatus.ACTIVE,
            PageRequest.of(0, 10)
        );

        // Then
        assertThat(users.getContent()).hasSize(1);
        assertThat(users.getContent().get(0).getStatus())
            .isEqualTo(User.UserStatus.ACTIVE);
    }

    private User createUser(String name, String email) {
        User user = new User();
        user.setName(name);
        user.setEmail(email);
        user.setPhoneNumber("+1234567890");
        user.setStatus(User.UserStatus.ACTIVE);
        return user;
    }
} 