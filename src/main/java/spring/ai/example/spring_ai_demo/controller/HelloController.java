package spring.ai.example.spring_ai_demo.controller;

import org.springframework.web.bind.annotation.*;
import org.springframework.beans.factory.annotation.Autowired;
import java.util.UUID;
import java.util.List;
import spring.ai.example.spring_ai_demo.repository.UserRepository;
import spring.ai.example.spring_ai_demo.model.User;

@RestController
@RequestMapping("/api")
public class HelloController {

    @Autowired
    private UserRepository userRepository;

    // Create a new user
    @PostMapping("user")
    @ResponseBody
    public User addUser(@RequestParam String name) {
        User user = new User();
        user.setId(UUID.randomUUID().getMostSignificantBits() & Long.MAX_VALUE);
        user.setName(name);
        return userRepository.save(user);
    }

    // Get all users
    @GetMapping("users")
    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    // Get user by ID
    @GetMapping("user/{id}")
    public User getUserById(@PathVariable Long id) {
        return userRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("User not found"));
    }

    // Delete user
    @DeleteMapping("user/{id}")
    public void deleteUser(@PathVariable Long id) {
        userRepository.deleteById(id);
    }
}
