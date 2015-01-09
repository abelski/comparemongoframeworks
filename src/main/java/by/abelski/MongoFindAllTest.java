package by.abelski;

import by.abelski.domain.spring.Test;
import by.abelski.domain.spring.TestFind;
import com.mongodb.*;
import org.mongodb.morphia.Datastore;
import org.mongodb.morphia.Morphia;
import org.openjdk.jmh.annotations.*;
import org.springframework.data.mongodb.core.MongoTemplate;

import java.net.UnknownHostException;
import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * @author Artur Belski
 */
@State(Scope.Thread)
public class MongoFindAllTest {
    public static final String DB_NAME = "test1";
    public static final String TEST_COLLECTION = "test1";
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

        for (int i = 0; i < 1000; i++) {
            mongoTemplate.insert(new TestFind());
        }
    }

    @Benchmark
    @BenchmarkMode(Mode.SampleTime)
    @OutputTimeUnit(TimeUnit.NANOSECONDS)
    public void findUseDriver() {
        collection.find();
    }

    @Benchmark
    @BenchmarkMode(Mode.SampleTime)
    @OutputTimeUnit(TimeUnit.NANOSECONDS)
    public void findUseSpringData() {
        mongoTemplate.findAll(TestFind.class);
    }

    @Benchmark
    @BenchmarkMode(Mode.SampleTime)
    @OutputTimeUnit(TimeUnit.NANOSECONDS)
    public void findUseMorphia() {
        morphia.find(by.abelski.domain.morphia.TestFind.class);
    }


    @TearDown
    public static void tearDown() {
        //Nothing do
    }
}
