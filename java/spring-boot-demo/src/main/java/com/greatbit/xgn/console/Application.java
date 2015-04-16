package com.greatbit.xgn.console;

import com.greatbit.xgn.console.domain.LoginUser;
import com.greatbit.xgn.console.domain.LoginUserRepository;
import com.greatbit.xgn.console.domain.Role;
import com.greatbit.xgn.console.domain.RoleRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.listener.PatternTopic;
import org.springframework.data.redis.listener.RedisMessageListenerContainer;
import org.springframework.data.redis.listener.adapter.MessageListenerAdapter;

import javax.annotation.PostConstruct;
import java.util.concurrent.CountDownLatch;

@SpringBootApplication
public class Application {
    @Autowired
    private RoleRepository roleRepository;
    @Autowired
    private LoginUserRepository userRepository;
    
    @PostConstruct
    public void init() {
        if (roleRepository.count() <= 0) {
            Role adminRole = new Role();
            adminRole.setRoleName("ROLE_ADMIN");
            adminRole.setRoleDesc("ADMIN ROLE");

            Role userRole = new Role();
            userRole.setRoleName("ROLE_USER");
            userRole.setRoleDesc("USER ROLE");

            roleRepository.save(adminRole);
            roleRepository.save(userRole);

            LoginUser admin = new LoginUser();
            admin.setUsername("admin");
            admin.setPassword("admin");
            admin.setLoginIp("0.0.0.0");

            admin.getRoles().add(adminRole);

            userRepository.save(admin);
        }
    }


    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }
}
