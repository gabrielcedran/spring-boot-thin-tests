package br.com.cedran.gateway.http;

import static br.com.six2six.fixturefactory.Fixture.from;
import static org.hamcrest.Matchers.equalTo;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;

import java.util.Arrays;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import br.com.cedran.domains.Car;
import br.com.cedran.domains.CountByBrand;
import br.com.cedran.gateway.http.conf.AbstractHttpTest;
import br.com.cedran.gateway.mongo.CarsRepository;
import br.com.cedran.gateway.mongo.CarsRepositoryCustom;
import br.com.six2six.fixturefactory.loader.FixtureFactoryLoader;

@SpringBootTest(classes = CarsController.class)
public class CarsControllerTest extends AbstractHttpTest {

    @Autowired
    private CarsController carController;

    @MockBean
    private CarsRepository carsRepository;

    @MockBean
    private CarsRepositoryCustom carsRepositoryCustom;

    private MockMvc mockMvc;

    private ObjectMapper mapper;

    @Before
    public void setup() {
        mockMvc = loadController(carController);
        mapper = new ObjectMapper();
        FixtureFactoryLoader.loadTemplates("br.com.cedran.gateway.templates");
    }

    @Test
    public void getAllCars() throws Exception {
        // Given there is 2 existent cars
        List<Car> cars = from(Car.class).gimme(2, "valid Volkswagen car");
        when(carsRepository.findAll()).thenReturn(cars);

        // When I ask for all cars
        MvcResult mvcResult = mockMvc.perform(get("/api/v1/cars")).andReturn();

        // Then all existent cars are returned
        assertThat(mvcResult.getResponse().getStatus(), equalTo(200));
        List<Car> returnedCars = mapper.readValue(mvcResult.getResponse().getContentAsString(), new TypeReference<List<Car>>() {
        });
        assertThat(returnedCars.size(), equalTo(2));
    }

    @Test
    public void countByBrandNative() throws Exception {
        // GIVEN the total of Volkswagen
        CountByBrand volks = from(CountByBrand.class).gimme("count by brand Volkswagen");
        // AND the total of Fiat
        CountByBrand fiat = from(CountByBrand.class).gimme("count by brand Fiat");
        // AND they exist in the database
        when(carsRepositoryCustom.countByBrandNative()).thenReturn(Arrays.asList(volks, fiat));

        // When a summary by brand
        MvcResult mvcResult = mockMvc.perform(get("/api/v1/cars/countByBrandNative")).andReturn();

        // Then all existent cars are returned
        assertThat(mvcResult.getResponse().getStatus(), equalTo(200));
        List<CountByBrand> returnedCars = mapper.readValue(mvcResult.getResponse().getContentAsString(), new TypeReference<List<CountByBrand>>() {
        });
        assertThat(returnedCars.size(), equalTo(2));
    }

    @Test
    public void countByBrandCriteria() throws Exception {
        // GIVEN the total of Volkswagen
        CountByBrand volks = from(CountByBrand.class).gimme("count by brand Volkswagen");
        // AND the total of Fiat
        CountByBrand fiat = from(CountByBrand.class).gimme("count by brand Fiat");
        // AND they exist in the database
        when(carsRepositoryCustom.countByBrandCriteria()).thenReturn(Arrays.asList(volks, fiat));

        // When a summary by brand
        MvcResult mvcResult = mockMvc.perform(get("/api/v1/cars/countByBrandCriteria")).andReturn();

        // Then all existent cars are returned
        assertThat(mvcResult.getResponse().getStatus(), equalTo(200));
        List<CountByBrand> returnedCars = mapper.readValue(mvcResult.getResponse().getContentAsString(), new TypeReference<List<CountByBrand>>() {
        });
        assertThat(returnedCars.size(), equalTo(2));
    }

    @Test
    public void testUnexpectedException() throws Exception {
        // GIVEN the service throws unexpected exception
        when(carsRepository.findAll()).thenThrow(new RuntimeException("Exception"));

        // When a summary by brand
        MvcResult mvcResult = mockMvc.perform(get("/api/v1/cars")).andReturn();

        // Then all existent cars are returned
        assertThat(mvcResult.getResponse().getStatus(), equalTo(500));
        assertThat(mvcResult.getResponse().getContentAsString(), equalTo("Error"));
    }

}
