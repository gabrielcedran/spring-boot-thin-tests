package br.com.cedran.gateway.mongo.conf;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.MongoDbFactory;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.SimpleMongoDbFactory;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;

import com.github.fakemongo.Fongo;

@Configuration
@EnableMongoRepositories(basePackages = "br.com.cedran.gateway.mongo")
public class MongoTestConfig {

    private static final String DATABASE_NAME = "tests";

    @Bean
    public MongoTemplate mongoTemplate() {
        final MongoTemplate mongoTemplate = new MongoTemplate(mongoDbFactory());
        return mongoTemplate;
    }

    public MongoDbFactory mongoDbFactory() {
        final Fongo fongo = new Fongo("mongodb server in memory");
        return new SimpleMongoDbFactory(fongo.getMongo(), DATABASE_NAME);
    }
}
