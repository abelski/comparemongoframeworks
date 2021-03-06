package by.abelski;

import by.abelski.domain.spring.TestFind;
import com.mongodb.DB;
import com.mongodb.DBCollection;
import com.mongodb.Mongo;
import com.mongodb.MongoClient;
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
public class MongoFindAllTest {
    public static final String DB_NAME = "test";
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
        db.getCollection("test1m").drop();
        db.getCollection("test1s").drop();

        mongoTemplate = new MongoTemplate(new Mongo(), DB_NAME);
        morphia = new Morphia().createDatastore(mongoClient, DB_NAME);

        for (int i = 0; i < 10000; i++) {
            mongoTemplate.insert(new TestFind());
        }

        for (int i = 0; i < 10000; i++) {
            morphia.save(new by.abelski.domain.morphia.TestFind());
        }
    }

    @Benchmark
    public void findUseDriver() {
        collection.find();
    }

    @Benchmark
    public void findUseSpringData() {
        mongoTemplate.findAll(TestFind.class);
    }

    @Benchmark
    public void findUseMorphia() {
        morphia.find(by.abelski.domain.morphia.TestFind.class);
    }


    @TearDown
    public static void tearDown() {
        //Nothing do
    }
}
