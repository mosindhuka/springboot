package com.example.myfirstapp.config;

import com.zaxxer.hikari.HikariDataSource;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class PostgreSQLDataSourceConfig {

    @Bean(name = "pgDataSource")
    @ConfigurationProperties(prefix = "spring.datasource.pg.hikari")
    public HikariDataSource pgDataSource(
            @Value("${spring.datasource.pg.url}") String url,
            @Value("${spring.datasource.pg.username}") String username,
            @Value("${spring.datasource.pg.password}") String password) {

        HikariDataSource dataSource = new HikariDataSource();

        dataSource.setJdbcUrl(url);
        dataSource.setUsername(username);
        dataSource.setPassword(password);
        dataSource.setDriverClassName("org.postgresql.Driver");

        return dataSource;
    }
}
