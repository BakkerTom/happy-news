package nl.fhict.happynews.api;

import com.mongodb.Mongo;
import com.mongodb.MongoClient;
import nl.fhict.happynews.api.auth.oauth2.AuthenticationReadConverter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.core.convert.converter.Converter;
import org.springframework.data.mongodb.config.AbstractMongoConfiguration;
import org.springframework.data.mongodb.core.convert.CustomConversions;

import java.util.ArrayList;
import java.util.List;

/**
 * Configuration of the MongoDB connection.
 */
@Profile("!test")
@Configuration
public class MongoConfig extends AbstractMongoConfiguration {

    @Value("${spring.data.mongodb.database}")
    private String database;

    @Value("${spring.data.mongodb.host}")
    private String host;

    @Value("${spring.data.mongodb.port}")
    private int port;

    @Override
    protected String getDatabaseName() {
        return database;
    }

    @Override
    @Bean
    public Mongo mongo() throws Exception {
        return new MongoClient(host, port);
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
