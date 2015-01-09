package by.abelski;

import by.abelski.domain.spring.Test;
import com.mongodb.*;
import org.mongodb.morphia.Datastore;
import org.mongodb.morphia.Morphia;
import org.openjdk.jmh.annotations.*;
import org.springframework.data.mongodb.core.MongoTemplate;

import java.net.UnknownHostException;
import java.util.concurrent.TimeUnit;

/**
 * @author Artur Belski
 */
@State(Scope.Thread)
public class MongoInsertTest {
    public static final String DB_NAME = "test";
    public static final String TEST_COLLECTION = "test";
    private static DB db;
    private static DBCollection collection;
    private static MongoTemplate mongoTemplate;
    private static Datastore morphia;


    @Setup
    public static void setup() throws UnknownHostException {

        MongoClient mongoClient = new MongoClient();
        db = mongoClient.getDB(DB_NAME);
        collection = db.getCollection(TEST_COLLECTION);
        collection.drop();
        mongoTemplate = new MongoTemplate(new Mongo(), DB_NAME);
        morphia = new Morphia().createDatastore(mongoClient, DB_NAME);
    }

    @TearDown
    public static void tearDown() {
        //Nothing do
    }

    @Benchmark
    @BenchmarkMode(Mode.SampleTime)
    @OutputTimeUnit(TimeUnit.NANOSECONDS)
    public void insertUseDriver() {
        collection.insert(new BasicDBObject().append("name", "TEST").append("name1", "TEST1").append("name2", "TEST2").append("name3", "TEST3"));
    }

    @Benchmark
    @BenchmarkMode(Mode.SampleTime)
    @OutputTimeUnit(TimeUnit.NANOSECONDS)
    public void insertUseSpringData() {
        mongoTemplate.insert(new Test());
    }

    @Benchmark
    @BenchmarkMode(Mode.SampleTime)
    @OutputTimeUnit(TimeUnit.NANOSECONDS)
    public void insertUseMorphia() {
        morphia.save(new by.abelski.domain.morphia.Test());
    }
}
