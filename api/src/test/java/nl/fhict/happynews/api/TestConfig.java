package nl.fhict.happynews.api;

import com.mongodb.Mongo;
import cz.jirutka.spring.embedmongo.EmbeddedMongoBuilder;
import nl.fhict.happynews.api.auth.oauth2.AuthenticationReadConverter;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.data.mongo.MongoDataAutoConfiguration;
import org.springframework.boot.autoconfigure.mongo.MongoAutoConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.convert.converter.Converter;
import org.springframework.data.mongodb.config.AbstractMongoConfiguration;
import org.springframework.data.mongodb.core.convert.CustomConversions;

import java.util.ArrayList;
import java.util.List;

@SpringBootApplication(exclude = {MongoAutoConfiguration.class, MongoDataAutoConfiguration.class})
public class TestConfig {

    @Configuration
    public class MongoConfig extends AbstractMongoConfiguration {

        @Override
        protected String getDatabaseName() {
            return "test";
        }

        @Override
        @Bean(destroyMethod = "close")
        public Mongo mongo() throws Exception {
            return new EmbeddedMongoBuilder()
                .version("2.4.5")
                .bindIp("127.0.0.1")
                .build();
        }

        @Override
        @Bean
        public CustomConversions customConversions() {
            List<Converter<?, ?>> converterList = new ArrayList<>();
            AuthenticationReadConverter authenticationReadConverter = new AuthenticationReadConverter();
            converterList.add(authenticationReadConverter);
            return new CustomConversions(converterList);
        }
    }
}
