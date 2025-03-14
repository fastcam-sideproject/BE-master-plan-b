package com.example.masterplanbbe.common.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.transaction.PlatformTransactionManager;

import javax.sql.DataSource;
import java.util.HashMap;
import java.util.Map;

@Configuration
@EnableJpaRepositories(
        basePackages = {
                "com.example.masterplanbbe.domain.comment.repository",
                "com.example.masterplanbbe.domain.exam.repository",
                "com.example.masterplanbbe.domain.specBookmark.repository",
                "com.example.masterplanbbe.domain.spec.repository",
                "com.example.masterplanbbe.domain.member.repository",
                "com.example.masterplanbbe.domain.post.repository",
                "com.example.masterplanbbe.domain.studyLog.repository",
                "com.example.masterplanbbe.domain.userExamSession.repository",
                "com.example.masterplanbbe.domain.category.repository",
                "com.example.masterplanbbe.domain.jobRole.repository",
                "com.example.masterplanbbe.domain.chat.repository",
                "com.example.masterplanbbe.domain.recommendation.repository"
        },
        entityManagerFactoryRef = "dataEntityManager",
        transactionManagerRef = "dataTransactionManager"
)
public class DataDBConfig {

    @Bean
    @ConfigurationProperties(prefix = "spring.datasource-service")
    public DataSource dataSource() {
        return DataSourceBuilder.create().build();
    }

    @Bean
    public LocalContainerEntityManagerFactoryBean dataEntityManager() {

        LocalContainerEntityManagerFactoryBean factoryBean = new LocalContainerEntityManagerFactoryBean();

        factoryBean.setDataSource(dataSource());
        factoryBean.setPackagesToScan(
                "com.example.masterplanbbe.domain.comment.entity",
                "com.example.masterplanbbe.domain.exam.entity",
                "com.example.masterplanbbe.domain.examBookmark.entity",
                "com.example.masterplanbbe.domain.examSession.entity",
                "com.example.masterplanbbe.domain.member.entity",
                "com.example.masterplanbbe.domain.post.entity",
                "com.example.masterplanbbe.domain.studyLog.entity",
                "com.example.masterplanbbe.domain.userExamSession.entity",
                "com.example.masterplanbbe.domain.spec.entity",
                "com.example.masterplanbbe.domain.specBookmark.entity",
                "com.example.masterplanbbe.domain.category.entity",
                "com.example.masterplanbbe.domain.jobRole.entity",
                "com.example.masterplanbbe.domain.chat.entity",
                "com.example.masterplanbbe.domain.recommendation.entity"
        );
        factoryBean.setJpaVendorAdapter(new HibernateJpaVendorAdapter());

        Map<String, Object> properties = new HashMap<>();
        properties.put("hibernate.hbm2ddl.auto", "update");
//        properties.put("hibernate.show_sql", "true");
        properties.put("hibernate.dialect", "org.hibernate.dialect.MySQLDialect");


        factoryBean.setJpaPropertyMap(properties);
        return factoryBean;
    }

    @Bean
    public PlatformTransactionManager dataTransactionManager() {
        JpaTransactionManager transactionManager = new JpaTransactionManager();
        transactionManager.setEntityManagerFactory(dataEntityManager().getObject());

        return transactionManager;
    }

    @Bean
    public PlatformTransactionManager jdbcTransactionManager() {
        return new DataSourceTransactionManager(dataSource());
    }

    @Bean
    public JdbcTemplate jdbcTemplate() {
        return new JdbcTemplate(dataSource());
    }
}
