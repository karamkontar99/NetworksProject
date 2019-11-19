package main.repos;

import com.mongodb.MongoClient;
import com.mongodb.client.MongoDatabase;
import org.bson.codecs.configuration.CodecRegistries;
import org.bson.codecs.configuration.CodecRegistry;
import org.bson.codecs.pojo.PojoCodecProvider;

import javax.inject.Singleton;

@Singleton
public class DBConfig {

    private final MongoDatabase database;

    public DBConfig() {
        this.database = configureDatabase();
        System.out.println("DB CREATED");
    }

    public MongoDatabase getDatabase() {
        return database;
    }

    private MongoDatabase configureDatabase() {
        CodecRegistry pojoCodecRegistry =
                CodecRegistries.fromRegistries(MongoClient.getDefaultCodecRegistry(),
                        CodecRegistries.fromProviders(PojoCodecProvider.builder().automatic(true).build()));

        MongoClient mongoClient = new MongoClient("192.168.99.100", 27017);
        MongoDatabase database = mongoClient
                .getDatabase("database")
                .withCodecRegistry(pojoCodecRegistry);

        return database;
    }
}
