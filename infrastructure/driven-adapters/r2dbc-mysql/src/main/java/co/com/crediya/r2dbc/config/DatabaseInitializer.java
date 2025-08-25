package co.com.crediya.r2dbc.config;

import jakarta.annotation.PostConstruct;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.Resource;
import org.springframework.r2dbc.core.DatabaseClient;

import java.nio.file.Files;
import java.nio.file.Paths;

@Configuration
public class DatabaseInitializer {
    private Resource schemaScript;
    private final DatabaseClient databaseClient;

    public DatabaseInitializer(DatabaseClient databaseClient, MySqlConnectionProperties mySqlConnectionProperties) {
        this.databaseClient = databaseClient;
        this.schemaScript = new org.springframework.core.io.ClassPathResource(mySqlConnectionProperties.schema());
    }

    @PostConstruct
    public void initializeDatabase() throws Exception {
        String sql = new String(Files.readAllBytes(Paths.get(schemaScript.getURI())));
        for (String statement : sql.split(";")) {
            if (!statement.trim().isEmpty()) {
                databaseClient.sql(statement).then().subscribe();
            }
        }
    }
}
