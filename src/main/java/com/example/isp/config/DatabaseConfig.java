package com.example.isp.config;

import org.springframework.boot.autoconfigure.orm.jpa.HibernatePropertiesCustomizer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.SQLException;

@Configuration
@EnableTransactionManagement
public class DatabaseConfig {

    /**
     * Customize Hibernate properties to ensure connections are not read-only
     */
    @Bean
    public HibernatePropertiesCustomizer hibernatePropertiesCustomizer() {
        return hibernateProperties -> {
            hibernateProperties.put("hibernate.connection.autocommit", false);
        };
    }

    /**
     * Post-process DataSource to ensure connections are not read-only
     */
    @Bean
    public DataSourceReadOnlyFixer dataSourceReadOnlyFixer(DataSource dataSource) {
        return new DataSourceReadOnlyFixer(dataSource);
    }

    /**
     * Helper class to fix read-only connections
     */
    public static class DataSourceReadOnlyFixer {
        private final DataSource dataSource;

        public DataSourceReadOnlyFixer(DataSource dataSource) {
            this.dataSource = dataSource;
            try {
                // Test connection and set read-only to false
                try (Connection connection = dataSource.getConnection()) {
                    if (connection.isReadOnly()) {
                        connection.setReadOnly(false);
                    }
                }
            } catch (SQLException e) {
                // Log but don't fail startup
                System.err.println("Warning: Could not configure datasource read-only mode: " + e.getMessage());
            }
        }
    }
}
