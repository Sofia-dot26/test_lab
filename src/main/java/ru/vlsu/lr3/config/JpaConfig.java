package ru.vlsu.lr3.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@Configuration
@EnableJpaRepositories(basePackages = "ru.vlsu.lr3.repository")
public class JpaConfig {
}
