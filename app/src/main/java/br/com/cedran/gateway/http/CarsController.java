package br.com.cedran.gateway.http;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import br.com.cedran.domains.Car;
import br.com.cedran.domains.CountByBrand;
import br.com.cedran.gateway.mongo.CarsRepository;
import br.com.cedran.gateway.mongo.CarsRepositoryCustom;

@RestController
@RequestMapping("/api/v1/cars")
public class CarsController {

    @Autowired
    private CarsRepository carsRepository;

    @Autowired
    private CarsRepositoryCustom carsRepositoryCustom;

    @RequestMapping(method = RequestMethod.GET)
    @ResponseStatus(HttpStatus.OK)
    public List<Car> getCars() {
        return carsRepository.findAll();
    }

    @RequestMapping(value = "/countByBrandNative", method = RequestMethod.GET)
    @ResponseStatus(HttpStatus.OK)
    public List<CountByBrand> countByModelNative() {
        return carsRepositoryCustom.countByBrandNative();
    }

    @RequestMapping(value = "/countByBrandCriteria", method = RequestMethod.GET)
    @ResponseStatus(HttpStatus.OK)
    public List<CountByBrand> countByModelCriteria() {
        return carsRepositoryCustom.countByBrandCriteria();
    }
}
