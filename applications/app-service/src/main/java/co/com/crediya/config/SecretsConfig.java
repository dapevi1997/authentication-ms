package co.com.crediya.config;

import co.com.bancolombia.secretsmanager.api.GenericManagerAsync;
import co.com.bancolombia.secretsmanager.api.exceptions.SecretException;
import co.com.bancolombia.secretsmanager.config.AWSSecretsManagerConfig;
import co.com.bancolombia.secretsmanager.connector.AWSSecretManagerConnectorAsync;
import co.com.crediya.api.security.util.JwtProperties;
import co.com.crediya.api.security.util.JwtSecret;
import co.com.crediya.r2dbc.config.MySqlConnectionProperties;
import co.com.crediya.r2dbc.config.MySqlSecretDto;
import software.amazon.awssdk.regions.Region;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SecretsConfig {
    @Value("${jwt.expiration}")
    private Long expiration;

    @Value("${adapters.r2dbc.host}")
    private String host;

    @Value("${adapters.r2dbc.port}")
    private Integer port;

    @Value("${adapters.r2dbc.database}")
    private String database;
    
    @Bean
    public JwtProperties jwtProperties(GenericManagerAsync secretManager) throws SecretException {
        JwtSecret secret = secretManager.getSecret("jwt", JwtSecret.class).block();
        assert secret != null;
        return new JwtProperties(secret.getJwtSecretValue(), expiration);
    }

    @Bean
    public MySqlConnectionProperties mySqlConnectionProperties(GenericManagerAsync secretManager) throws SecretException {
        MySqlSecretDto secretDto = secretManager.getSecret("rds!db-6ad58c42-8de1-430f-82a2-11d215f5c460", MySqlSecretDto.class)
                .block();
        assert secretDto != null;
        return new MySqlConnectionProperties(host, port, database, "", secretDto.getUsername(), secretDto.getPassword());

    }

    @Bean
    public GenericManagerAsync getSecretManager(@Value("${aws.region}") String region) {
        return new AWSSecretManagerConnectorAsync(getConfig(region));
    }

    private AWSSecretsManagerConfig getConfig(String region) {
        return AWSSecretsManagerConfig.builder()
                .region(Region.of(region))
                .cacheSize(5)
                .cacheSeconds(3600)
                .build();
    }
}
