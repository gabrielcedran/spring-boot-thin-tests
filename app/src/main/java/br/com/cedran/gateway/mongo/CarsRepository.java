package br.com.cedran.gateway.mongo;

import org.springframework.data.mongodb.repository.MongoRepository;

import br.com.cedran.domains.Car;

public interface CarsRepository extends MongoRepository<Car, String> {
}
