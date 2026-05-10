package com.example.myfirstapp.config;

import jakarta.persistence.EntityManagerFactory;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.boot.jpa.EntityManagerFactoryBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.transaction.PlatformTransactionManager;

import javax.sql.DataSource;

@Configuration
@EnableJpaRepositories(
        basePackages = "com.example.myfirstapp.repositories.test2",
        entityManagerFactoryRef = "test2EntityManagerFactory",
        transactionManagerRef = "test2TransactionManager"
)
public class Test2DbConfig {

    @Bean
    @ConfigurationProperties(prefix = "spring.datasource.test2")
    public DataSource test2DataSource() {
        return DataSourceBuilder.create().build();
    }

    @Bean
    public LocalContainerEntityManagerFactoryBean test2EntityManagerFactory(
            EntityManagerFactoryBuilder builder) {

        return builder
                .dataSource(test2DataSource())
                .packages("com.example.myfirstapp.models")
                .persistenceUnit("test2db")
                .build();
    }

    @Bean
    public PlatformTransactionManager test2TransactionManager(
            @Qualifier("test2EntityManagerFactory")
            EntityManagerFactory emf) {

        return new JpaTransactionManager(emf);
    }
}