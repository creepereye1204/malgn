package com.malgn;

import com.malgn.domain.member.Member;
import com.malgn.domain.member.MemberRepository;
import com.malgn.domain.member.Role;
import io.github.cdimascio.dotenv.Dotenv;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.data.web.config.EnableSpringDataWebSupport;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.security.crypto.password.PasswordEncoder;

import static org.springframework.data.web.config.EnableSpringDataWebSupport.PageSerializationMode.VIA_DTO;

@EnableScheduling
@SpringBootApplication
@EnableSpringDataWebSupport(pageSerializationMode = VIA_DTO)
public class Application {

    public static void main(String[] args) {
        Dotenv dotenv = Dotenv.configure().ignoreIfMissing().load();
        dotenv.entries().forEach(entry -> System.setProperty(entry.getKey(), entry.getValue()));
        
        SpringApplication.run(Application.class, args);
    }

    @Bean
    public CommandLineRunner initData(MemberRepository memberRepository, PasswordEncoder passwordEncoder) {
        return args -> {
            if (memberRepository.findByUsername("admin").isEmpty()) {
                memberRepository.save(Member.builder()
                        .username("admin")
                        .password(passwordEncoder.encode("password"))
                        .role(Role.ADMIN)
                        .build());
            }
            if (memberRepository.findByUsername("user1").isEmpty()) {
                memberRepository.save(Member.builder()
                        .username("user1")
                        .password(passwordEncoder.encode("password"))
                        .role(Role.USER)
                        .build());
            }
            if (memberRepository.findByUsername("user2").isEmpty()) {
                memberRepository.save(Member.builder()
                        .username("user2")
                        .password(passwordEncoder.encode("password"))
                        .role(Role.USER)
                        .build());
            }
        };
    }
}
