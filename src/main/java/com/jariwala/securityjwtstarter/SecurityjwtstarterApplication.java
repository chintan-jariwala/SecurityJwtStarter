package com.jariwala.securityjwtstarter;

import lombok.extern.slf4j.Slf4j;
import org.flywaydb.core.Flyway;
import org.flywaydb.core.api.FlywayException;
import org.flywaydb.core.internal.sqlscript.FlywaySqlScriptException;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.flyway.FlywayMigrationStrategy;

@SpringBootApplication
@Slf4j
public class SecurityjwtstarterApplication implements FlywayMigrationStrategy {

    private static final Integer MAX_FLYWAY_RETRY_ATTEMPTS = 500;

    private Integer flywayRetryCount = 0;

    public static void main(String[] args) {
        SpringApplication.run(SecurityjwtstarterApplication.class, args);
    }

    @Override
    public void migrate(Flyway flyway) {
        try {
            flyway.migrate();
        } catch (FlywaySqlScriptException flywaySqlScriptException) {
            throw flywaySqlScriptException;
        } catch (FlywayException flywayException) {
            flywayRetryCount++;
            if (flywayRetryCount >= MAX_FLYWAY_RETRY_ATTEMPTS) {
                log.error("Max retry attempts had been reached.");
                throw flywayException;
            }
            log.error("Flyway Exception", flywayException);
            flyway.repair();
            migrate(flyway);
        }
    }
}
