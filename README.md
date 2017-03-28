# spring-boot-thin-tests
Example of an application using spring-boot-starter-web, spring-boot-starter-data-mongodb and spring-boot-starter-test that only loads the beans envolved in the unit tests being executed to speed up the execution of the suit.
It also uses [six2six framework](https://github.com/six2six/fixture-factory) to create fake objects to the unit tests, [fongodb](https://github.com/fakemongo/fongo) to fake a mongodb in memory during unit tests, lombok to avoid getters and setters boilerplates and provides example of mongo's native query and criteria queries. 

### Spring MVC Tests:
In order to load only the beans involved in the controller layers, annotate your class with @SpringBootTest and extend the AbstractHttpTest:
```java
@SpringBootTest(classes = CarsController.class)
public class CarsControllerTest extends AbstractHttpTest {

    @Autowired
    private CarsController carController;

    @MockBean
    private CarsRepository carsRepository;

    ... omitted code
}
```
AbstractHttpTest class avoids boilerplate code and annotations like @RunWith, @WebAppConfiguration and configuration setup like exception handlers (@ControllerAdvice).

### Spring MVC Tests:
In order to load only the beans involved in the database layer, annotate your class with @RunWith and @SpringBootTest:

```java
@RunWith(SpringRunner.class)
@SpringBootTest(classes = { MongoTestConfig.class, CarsRepositoryCustom.class })
public class CarsRepositoryCustomTest {

    @Autowired
    private MongoTemplate mongoTemplate;

    @Autowired
    private CarsRepositoryCustom carsRepositoryCustom;
    
    // omitted code
}
```

### Mongodb Native Query:
```java
public List<CountByBrand> countByBrandNative() {
        //@formatter:off
        DBObject group = new BasicDBObject("$group",
                new BasicDBObject("_id", new BasicDBObject("brand", "$brand"))
                          .append("count", new BasicDBObject("$sum", 1))
            );
        DBObject project = new BasicDBObject("$project",
                new BasicDBObject("brand", "$_id.brand")
                          .append("count", "$count"));

        // Original query:
        // db.getCollection('cars').aggregate([
        //  {$group: { _id: {'brand': '$brand'}, count: {$sum: 1}}},
        //  {$project: {'brand':'$_id.brand', 'count':'$count'}}
        // ])
        //@formatter:on

        List<DBObject> pipeline = new ArrayList<>(Arrays.asList(group, project));
        DBCollection collection = this.mongoOperations.getCollection(mongoOperations.getCollectionName(Car.class));
        AggregationOutput rawOutput = collection.aggregate(pipeline);

        List<CountByBrand> countByBrand = new ArrayList<>();

        for (DBObject dbObject : rawOutput.results()) {
            countByBrand.add(mongoOperations.getConverter().read(CountByBrand.class, dbObject));
        }

        return countByBrand;
    }
```

### Mongodb Criteria:
```java
    public List<CountByBrand> countByBrandCriteria() {
        //@formatter:off
        Aggregation aggregation = Aggregation.newAggregation(
                Aggregation.group("brand").first("brand").as("brand").count().as("count"),
                Aggregation.project("brand", "count"));
        //@formatter:on
        return mongoOperations.aggregate(aggregation, mongoOperations.getCollectionName(Car.class), CountByBrand.class).getMappedResults();

    }
```

### Running this project:
1. Inside docker folder run the command docker-compose up
2. Run main method of Application class

### Running tests:
1. Open app folder and run mvn test


