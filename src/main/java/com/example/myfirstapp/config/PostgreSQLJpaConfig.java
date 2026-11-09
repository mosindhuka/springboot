package com.example.myfirstapp.config;

import jakarta.persistence.EntityManagerFactory;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.jpa.EntityManagerFactoryBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.transaction.PlatformTransactionManager;

import javax.sql.DataSource;
import java.util.HashMap;
import java.util.Map;

@Configuration
@EnableJpaRepositories(
        basePackages = "com.example.myfirstapp.repositories.pg",
        entityManagerFactoryRef = "pgEntityManagerFactory",
        transactionManagerRef = "pgTransactionManager"
)
public class PostgreSQLJpaConfig {

    // 1. Explicitly define the EntityManagerFactoryBuilder for Spring Boot 4.x
    @Bean(name = "pgEntityManagerFactoryBuilder")
    public EntityManagerFactoryBuilder entityManagerFactoryBuilder() {
        HibernateJpaVendorAdapter vendorAdapter = new HibernateJpaVendorAdapter();
        // Spring Boot 4.x expects a Function mapping DataSource to properties Map
        return new EntityManagerFactoryBuilder(vendorAdapter, dataSource -> new HashMap<>(), null);
    }

    // 2. Build the EntityManagerFactory using the custom builder
    @Bean(name = "pgEntityManagerFactory")
    public LocalContainerEntityManagerFactoryBean pgEntityManagerFactory(
            @Qualifier("pgEntityManagerFactoryBuilder") EntityManagerFactoryBuilder builder,
            @Qualifier("pgDataSource") DataSource dataSource) {

        return builder
                .dataSource(dataSource)
                .packages("com.example.myfirstapp.models.pg")
                .persistenceUnit("pg")
                .properties(Map.of(
                        "hibernate.type.preferred_uuid_jdbc_type", "UUID",
                        "hibernate.dialect", "org.hibernate.dialect.PostgreSQLDialect",
                        // 👇 Use Hibernate's built-in CamelCaseToUnderscores strategy instead
                        "hibernate.physical_naming_strategy", "org.hibernate.boot.model.naming.CamelCaseToUnderscoresNamingStrategy"
                ))
                .build();
    }

    // 3. Define the Transaction Manager
    @Bean(name = "pgTransactionManager")
    public PlatformTransactionManager pgTransactionManager(
            @Qualifier("pgEntityManagerFactory")
            EntityManagerFactory entityManagerFactory) {

        return new JpaTransactionManager(entityManagerFactory);
    }

    // Added to satisfy Spring AI's PgVectorStore dependency requirement
    @Bean(name = "pgJdbcTemplate")
    public JdbcTemplate pgJdbcTemplate(@Qualifier("pgDataSource") DataSource dataSource) {
        return new JdbcTemplate(dataSource);
    }
}