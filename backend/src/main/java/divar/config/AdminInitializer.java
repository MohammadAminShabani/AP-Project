package divar.config;

import divar.entity.User;
import divar.enums.UserRole;
import divar.enums.UserStatus;
import divar.repository.UserRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class AdminInitializer implements CommandLineRunner {

    private final UserRepository userRepository;

    public AdminInitializer(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public void run(String... args) {

        System.out.println("=== AdminInitializer Started ===");

        userRepository.findAll().forEach(user -> {
            System.out.println(
                    user.getUsername() + " | " +
                            user.getPassword() + " | " +
                            user.getRole()
            );
        });

        if (userRepository.findByUsername("admin").isPresent()) {
            System.out.println("Admin already exists");
            return;
        }

        User admin = new User();

        admin.setFullName("System Admin");
        admin.setUsername("admin");
        admin.setPassword("001122");
        admin.setPhoneNumber("09000000000");
        admin.setEmail("admin@divar.ir");
        admin.setRole(UserRole.ADMIN);
        admin.setStatus(UserStatus.ACTIVE);
        admin.setAverageRating(0);
        admin.setRatingCount(0);

        userRepository.save(admin);

        System.out.println("Admin created");
    }
}