package nl.fhict.happynews.api;

import com.google.common.base.Predicate;
import org.joda.time.DateTime;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.ApiKey;
import springfox.documentation.service.AuthorizationScope;
import springfox.documentation.service.Contact;
import springfox.documentation.service.SecurityReference;
import springfox.documentation.service.SecurityScheme;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spi.service.contexts.SecurityContext;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger.web.ApiKeyVehicle;
import springfox.documentation.swagger.web.SecurityConfiguration;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import java.util.Collections;
import java.util.List;

import static com.google.common.base.Predicates.or;
import static springfox.documentation.builders.PathSelectors.ant;

@Configuration
@EnableSwagger2
public class SwaggerConfig {

    @Bean
    Docket api() {
        return new Docket(DocumentationType.SWAGGER_2)
            .apiInfo(apiInfo())
            .select()
            .paths(paths())
            .build()
            .securitySchemes(apiKeys())
            .securityContexts(securityContext())
            .directModelSubstitute(DateTime.class, Integer.class);

    }

    private ApiInfo apiInfo() {
        return new ApiInfoBuilder()
            .title("Happy News API")
            .description("Happy News REST API")
            .version("1.0")
            .contact(new Contact("Happy News", "https://github.com/BakkerTom/happy-news", "fhictwo@gmail.com"))
            .license("MIT License")
            .build();
    }

    private Predicate<String> paths() {
        return or(
            ant("/post/**"),
            securePaths()
        );
    }

    private Predicate<String> securePaths() {
        return or(
            ant("/admin/**")
        );
    }

    private List<SecurityContext> securityContext() {
        SecurityContext context = SecurityContext.builder()
            .securityReferences(defaultAuth())
            .forPaths(securePaths())
            .build();

        return Collections.singletonList(context);
    }

    private List<SecurityReference> defaultAuth() {
        AuthorizationScope[] authorizationScopes = new AuthorizationScope[]{
            new AuthorizationScope("read", "read only"),
            new AuthorizationScope("write", "read and write")
        };

        return Collections.singletonList(new SecurityReference("Authorization", authorizationScopes));
    }

    @Bean
    SecurityConfiguration securityInfo() {
        return new SecurityConfiguration(
            "client_id",
            "client_secret",
            "realm",
            "XXX",
            "Bearer access_token",
            ApiKeyVehicle.HEADER,
            "Authorization",
            ",");
    }

    private List<SecurityScheme> apiKeys() {
        return Collections.singletonList(new ApiKey("Authorization", "Authorization", "header"));
    }
}
