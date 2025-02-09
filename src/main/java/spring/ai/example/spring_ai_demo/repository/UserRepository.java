package spring.ai.example.spring_ai_demo.repository;

import org.springframework.stereotype.Repository;
import spring.ai.example.spring_ai_demo.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
}

