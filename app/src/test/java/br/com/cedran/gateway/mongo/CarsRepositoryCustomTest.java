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
import br.com.cedran.domains.CountByBrand;
import br.com.cedran.gateway.mongo.conf.MongoTestConfig;
import br.com.six2six.fixturefactory.loader.FixtureFactoryLoader;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = { MongoTestConfig.class, CarsRepositoryCustom.class })
public class CarsRepositoryCustomTest {

    @Autowired
    private MongoTemplate mongoTemplate;

    @Autowired
    private CarsRepositoryCustom carsRepositoryCustom;

    @Before
    public void setup() {
        mongoTemplate.dropCollection("cars");
        FixtureFactoryLoader.loadTemplates("br.com.cedran.gateway.templates");
    }

    @Test
    public void countByBrandNative() {
        // GIVEN two volkswagens cars
        List<Car> cars = from(Car.class).gimme(2, "valid Volkswagen car");
        // AND one fiat car
        cars.add(from(Car.class).gimme("valid Fiat car"));
        // AND they exist in the database
        mongoTemplate.insertAll(cars);

        // WHEN I call the method find all of car repository
        List<CountByBrand> countByBrands = carsRepositoryCustom.countByBrandNative();

        // THEN all cars are returned
        Assert.assertThat("brand", countByBrands.get(1).getBrand(), equalTo("Volkswagen"));
        Assert.assertThat("quantity", countByBrands.get(1).getCount(), equalTo(2));
        Assert.assertThat("brand", countByBrands.get(0).getBrand(), equalTo("Fiat"));
        Assert.assertThat("quantity", countByBrands.get(0).getCount(), equalTo(1));
    }

    @Test
    public void countByBrandCriteria() {
        // GIVEN two volkswagens cars
        List<Car> cars = from(Car.class).gimme(2, "valid Volkswagen car");
        // AND one fiat car
        cars.add(from(Car.class).gimme("valid Fiat car"));
        // AND they exist in the database
        mongoTemplate.insertAll(cars);

        // WHEN I call the method find all of car repository
        List<CountByBrand> countByBrands = carsRepositoryCustom.countByBrandCriteria();

        // THEN all cars are returned
        Assert.assertThat("brand", countByBrands.get(1).getBrand(), equalTo("Volkswagen"));
        Assert.assertThat("quantity", countByBrands.get(1).getCount(), equalTo(2));
        Assert.assertThat("brand", countByBrands.get(0).getBrand(), equalTo("Fiat"));
        Assert.assertThat("quantity", countByBrands.get(0).getCount(), equalTo(1));
    }
}
