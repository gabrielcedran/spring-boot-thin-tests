package br.com.cedran.gateway.mongo;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.data.mongodb.core.aggregation.Aggregation;
import org.springframework.stereotype.Component;

import com.mongodb.AggregationOutput;
import com.mongodb.BasicDBObject;
import com.mongodb.DBCollection;
import com.mongodb.DBObject;

import br.com.cedran.domains.Car;
import br.com.cedran.domains.CountByBrand;

@Component
public class CarsRepositoryCustom {

    @Autowired
    private MongoOperations mongoOperations;

    public List<CountByBrand> countByBrandCriteria() {
        //@formatter:off
        Aggregation aggregation = Aggregation.newAggregation(
                Aggregation.group("brand").first("brand").as("brand").count().as("count"),
                Aggregation.project("brand", "count"));
        //@formatter:on
        return mongoOperations.aggregate(aggregation, mongoOperations.getCollectionName(Car.class), CountByBrand.class).getMappedResults();

    }

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
}
