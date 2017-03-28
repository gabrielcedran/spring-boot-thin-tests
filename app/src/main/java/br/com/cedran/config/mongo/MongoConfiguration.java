package br.com.cedran.config.mongo;

import java.util.ArrayList;
import java.util.List;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.convert.converter.Converter;
import org.springframework.data.mongodb.core.convert.CustomConversions;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;

import br.com.cedran.config.mongo.converters.BigDecimalToDoubleConverter;
import br.com.cedran.config.mongo.converters.DoubleToBigDecimalConverter;

@Configuration
@EnableMongoRepositories(basePackages = "br.com.cedran.gateway.mongo")
public class MongoConfiguration {

    @Bean
    public CustomConversions customConversions() {
        List<Converter<?, ?>> converterList = new ArrayList<Converter<?, ?>>();
        converterList.add(new DoubleToBigDecimalConverter());
        converterList.add(new BigDecimalToDoubleConverter());
        return new CustomConversions(converterList);
    }

}
