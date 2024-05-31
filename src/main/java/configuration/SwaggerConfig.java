package configuration;

import entity.User;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import org.springdoc.core.models.GroupedOpenApi;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import static constants.PathConstants.API_V1;

@Configuration
public class SwaggerConfig {
    @Bean
    public OpenAPI customOpenAPI() {
        return new OpenAPI()
                .info(new Info()
                        .title("Open API Specification")
                        .version("1.0")
                        .description("API description goes here"));
    }

    /**
     * Endpoints we want to expose in our api
     * */
    @Bean
    public GroupedOpenApi endpoints() {
        return GroupedOpenApi.builder()
                .group("endpoints")
                .pathsToMatch(API_V1 + "/**")
                .pathsToExclude()
                .packagesToScan("controller")
                .build();
    }


}
