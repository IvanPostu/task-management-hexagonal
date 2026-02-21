package com.iv127.task.management.backend.entrypoint.adapters.out.repository.mysql.config;

import com.iv127.task.management.backend.entrypoint.adapters.out.repository.mysql.entity.TaskMySQLEntity;
import com.zaxxer.hikari.HikariDataSource;
import org.springframework.beans.factory.ObjectProvider;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.jdbc.autoconfigure.DataSourceProperties;
import org.springframework.boot.jpa.EntityManagerFactoryBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.JpaVendorAdapter;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.persistenceunit.PersistenceUnitManager;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.transaction.PlatformTransactionManager;

import javax.sql.DataSource;
import java.util.HashMap;
import java.util.Properties;

@Profile("repository-mysql")
@EnableJpaRepositories(basePackages = "com.iv127.task.management.backend.entrypoint.adapters.out.repository.mysql",
        entityManagerFactoryRef = "taskEntityManagerFactory", transactionManagerRef = "taskTransactionManager")
@Configuration
public class TaskDatabaseConfiguration {

    @Bean
    @ConfigurationProperties("spring.task.datasource")
    @ConditionalOnMissingBean(name = "taskDataSourceProperties")
    public DataSourceProperties taskDataSourceProperties() {
        return new DataSourceProperties();
    }

    @Bean
    @ConfigurationProperties("spring.task.datasource.hikari")
    public DataSource
    taskDataSource(@Qualifier("taskDataSourceProperties") DataSourceProperties taskDataSourceProperties) {
        return taskDataSourceProperties.initializeDataSourceBuilder()
                .type(HikariDataSource.class)
                .build();
    }

    @Bean
    public EntityManagerFactoryBuilder entityManagerFactoryBuilder(
            JpaVendorAdapter jpaVendorAdapter,
            ObjectProvider<PersistenceUnitManager> persistenceUnitManager) {
        return new EntityManagerFactoryBuilder(jpaVendorAdapter, dataSource -> new HashMap<>(), persistenceUnitManager.getIfAvailable());
    }

    @Bean
    public JpaVendorAdapter jpaVendorAdapter() {
        HibernateJpaVendorAdapter adapter = new HibernateJpaVendorAdapter();
        adapter.setGenerateDdl(false);
        adapter.setShowSql(true);
        adapter.setDatabasePlatform("org.hibernate.dialect.MySQLDialect");
        return adapter;
    }

    @Bean
    public LocalContainerEntityManagerFactoryBean taskEntityManagerFactory(
            @Qualifier("taskDataSource") DataSource taskDataSource,
            EntityManagerFactoryBuilder builder) {

        Properties props = new Properties();
        props.put("hibernate.hbm2ddl.auto", "create-drop");
        props.put("hibernate.physical_naming_strategy",
                "org.hibernate.boot.model.naming.CamelCaseToUnderscoresNamingStrategy");

        LocalContainerEntityManagerFactoryBean efb =
                builder.dataSource(taskDataSource)
                        .packages(TaskMySQLEntity.class)
                        .persistenceUnit("tasks")
                        .build();

        efb.setJpaProperties(props);

        return efb;
    }

    @Bean
    public PlatformTransactionManager taskTransactionManager(
            @Qualifier("taskEntityManagerFactory") LocalContainerEntityManagerFactoryBean taskEntityManagerFactory) {

        return new JpaTransactionManager(taskEntityManagerFactory.getObject());
    }
}
