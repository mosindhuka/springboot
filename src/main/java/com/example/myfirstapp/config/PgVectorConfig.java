//package com.example.myfirstapp.config;
//
//import javax.sql.DataSource;
//
//import org.springframework.ai.embedding.EmbeddingModel;
//import org.springframework.ai.vectorstore.VectorStore;
//import org.springframework.ai.vectorstore.pgvector.PgVectorStore;
//import org.springframework.beans.factory.annotation.Qualifier;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.jdbc.core.JdbcTemplate;
//
//@Configuration
//public class PgVectorConfig {
//
//    @Bean(name = "pgJdbcTemplate")
//    public JdbcTemplate pgJdbcTemplate(
//            @Qualifier("pgDataSource") DataSource dataSource) {
//
//        return new JdbcTemplate(dataSource);
//    }
//
//    @Bean
//    public VectorStore vectorStore(
//            @Qualifier("pgJdbcTemplate") JdbcTemplate jdbcTemplate,
//            EmbeddingModel embeddingModel) {
//
//        return PgVectorStore.builder(jdbcTemplate, embeddingModel)
//                .dimensions(768)
//                .distanceType(
//                        PgVectorStore.PgDistanceType.COSINE_DISTANCE
//                )
//                .indexType(
//                        PgVectorStore.PgIndexType.HNSW
//                )
//                .schemaName("public")
//                .vectorTableName("vector_store")
//                .initializeSchema(false)
//                .build();
//    }
//}