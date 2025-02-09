package spring.ai.example.spring_ai_demo.repository;

import org.springframework.stereotype.Repository;
import spring.ai.example.spring_ai_demo.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    Page<User> findByNameContainingIgnoreCase(String name, Pageable pageable);
    Page<User> findByStatus(User.UserStatus status, Pageable pageable);
    boolean existsByEmail(String email);
}

