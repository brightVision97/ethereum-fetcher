package com.rachev.ethereumfetcher.config;

import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.core.io.ClassPathResource;
import org.springframework.jdbc.datasource.init.DatabasePopulator;
import org.springframework.jdbc.datasource.init.DatabasePopulatorUtils;
import org.springframework.jdbc.datasource.init.ResourceDatabasePopulator;
import org.springframework.stereotype.Component;

import javax.sql.DataSource;

@Component
@RequiredArgsConstructor
public class UsersDatabaseTablePopulator implements CommandLineRunner {

    private final DataSource dataSource;

    @Override
    public void run(String... args) {
        DatabasePopulatorUtils.execute(createDatabasePopulator(), dataSource);
    }

    private DatabasePopulator createDatabasePopulator() {
        var databasePopulator = new ResourceDatabasePopulator();
        databasePopulator.setContinueOnError(true);
        databasePopulator.addScript(new ClassPathResource("insertUsers.sql"));
        return databasePopulator;
    }
}
