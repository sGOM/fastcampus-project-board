package com.fastcampus.projectboard.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.domain.AuditorAware;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

import java.util.Optional;

@EnableJpaAuditing
@Configuration
public class JpaConfig {

    @Bean
    public AuditorAware<String> auditorAware() {
        return () -> Optional.of("gom"); // TODO: 스프링 스큐리티로 인증 기능을 붙이게 될 때, 수정하기
    }   // 누가 로그인을 했을지를 통해 현재 사용자로 auditing
}
