package nl.fhict.happynews.api;

import org.springframework.boot.autoconfigure.web.DefaultErrorAttributes;
import org.springframework.boot.autoconfigure.web.ErrorAttributes;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.context.request.RequestAttributes;

import java.util.Map;

/**
 * Generic application configuration.
 */
@Configuration
public class AppConfig {

    /**
     * Get the attributes that will be shown in an error message. This hides the specific exception from the user,
     * but shows the message.
     *
     * @return The attributes that will be shown to the user in an error message.
     */
    @Bean
    public ErrorAttributes errorAttributes() {
        return new DefaultErrorAttributes() {

            @Override
            public Map<String, Object> getErrorAttributes(
                RequestAttributes requestAttributes,
                boolean includeStackTrace) {

                Map<String, Object> errorAttributes
                    = super.getErrorAttributes(requestAttributes, includeStackTrace);
                errorAttributes.remove("exception");
                return errorAttributes;
            }

        };
    }
}
