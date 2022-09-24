//package com.javafxspring.config;
//
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
//import org.springframework.jdbc.datasource.DriverManagerDataSource;
//import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
//import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
//
//import javax.sql.DataSource;
//import java.util.Properties;
//
//@Configuration
//@EnableJpaRepositories(basePackages = "com.javafxspring.repository")
//public class DbConfig {
//
//    @Bean
//    public DataSource dataSource() {
//        final DriverManagerDataSource dataSource = new DriverManagerDataSource();
//        dataSource.setDriverClassName("org.sqlite.JDBC");
//        dataSource.setUrl("jdbc:sqlite:sqlite.db");
//        dataSource.setUsername("sa");
//        dataSource.setPassword("sa");
//        return dataSource;
//    }
//
//    @Bean
//    public LocalContainerEntityManagerFactoryBean entityManagerFactory() {
//        final LocalContainerEntityManagerFactoryBean em = new LocalContainerEntityManagerFactoryBean();
//        em.setDataSource(dataSource());
//        em.setPackagesToScan("com.javafxspring.model");
//        em.setJpaVendorAdapter(new HibernateJpaVendorAdapter());
//        em.setJpaProperties(additionalProperties());
//        return em;
//    }
//
//    final Properties additionalProperties() {
//        final Properties hibernateProperties = new Properties();
//
//
//        hibernateProperties.setProperty("hibernate.dialect", "com.javafxspring.dialect.SQLiteDialect");
//
//        hibernateProperties.setProperty("spring.jpa.database-platform", "com.javafxspring.dialect.SQLiteDialect");
//        hibernateProperties.setProperty("spring.jpa.hibernate.ddl-auto", "create-drop");
//
//        return hibernateProperties;
//    }
//
//}