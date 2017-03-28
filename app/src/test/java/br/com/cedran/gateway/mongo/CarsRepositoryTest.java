package br.com.cedran.gateway.mongo;

import static br.com.six2six.fixturefactory.Fixture.from;
import static org.hamcrest.Matchers.equalTo;

import java.util.List;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.test.context.junit4.SpringRunner;

import br.com.cedran.domains.Car;
import br.com.cedran.gateway.mongo.conf.MongoTestConfig;
import br.com.six2six.fixturefactory.loader.FixtureFactoryLoader;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = { MongoTestConfig.class, CarsRepository.class })
public class CarsRepositoryTest {

    @Autowired
    private MongoTemplate mongoTemplate;

    @Autowired
    private CarsRepository carsRepository;

    @Before
    public void setup() {
        mongoTemplate.dropCollection("cars");
        FixtureFactoryLoader.loadTemplates("br.com.cedran.gateway.templates");
    }

    @Test
    public void findAllCars() {
        // GIVEN two existent cars in the database
        List<Car> cars = from(Car.class).gimme(2, "valid Volkswagen car");
        mongoTemplate.insertAll(cars);

        // WHEN I call the method find all of car repository
        List<Car> carsReturned = carsRepository.findAll();

        // THEN all cars are returned
        Assert.assertThat("number of cars", carsReturned.size(), equalTo(2));
    }
}
