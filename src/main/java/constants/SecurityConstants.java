package constants;

import factory.YamlPropertySourceFactory;
import lombok.Data;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

@Configuration
@PropertySource(value = "classpath:application-security.yml", factory = YamlPropertySourceFactory.class)
@ConfigurationProperties(prefix = "security.auth")
@Data
public class SecurityConstants {

    private String[] allowedResources;

    //@Value("${jwt.header}")
    public static final String AUTHORIZATION_HEADER = "Authorization";
}
