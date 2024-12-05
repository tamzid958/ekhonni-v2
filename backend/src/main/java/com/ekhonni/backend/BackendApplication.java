package com.ekhonni.backend;

import com.ekhonni.backend.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
@EnableJpaAuditing
public class BackendApplication {

    @Autowired
    private UserRepository userRepository;

    public static void main(String[] args) {
        SpringApplication.run(BackendApplication.class, args);
    }

//    @Bean
//    public CommandLineRunner commandLineRunner() {
//        return args -> {
//            var list = userRepository.findAllUser();
//            list.forEach(u -> System.out.println(u.getName() + ", " + u.getEmail()));
//        };
//    }
}
