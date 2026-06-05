package com.gestionturnos.infrastructure.config;

import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@Configuration
@EntityScan(basePackages = "com.gestionturnos.infrastructure.persistence.entity")
@EnableJpaRepositories(basePackages = "com.gestionturnos.infrastructure.persistence.jpa")
public class PersistenceConfig {
}
