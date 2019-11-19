package main.repos;

import com.mongodb.client.MongoCollection;
import com.mongodb.client.model.Filters;
import main.models.User;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.function.Consumer;

public class UserRepository implements Repository<User> {
    private MongoCollection<User> userCollection;

    public UserRepository(DBConfig dbConfig) {
        userCollection = dbConfig.getDatabase().getCollection("users", User.class);
    }

    public void insert(User user) {
        userCollection.insertOne(user);
    }

    public void delete(UUID userId) {
        userCollection.deleteOne(Filters.eq("_id", userId));
    }

    public void update(User user) {
        userCollection.replaceOne(Filters.eq("_id", user.getId()), user);
    }

    public List<User> findAll() {
        final List<User> users = new ArrayList<>();
        userCollection.find().forEach((Consumer<? super User>) users::add);
        return users;
    }

    public User find(UUID userId) {
        return userCollection.find(Filters.eq("_id", userId)).first();
    }

    public List<User> findAllOfDocument(UUID documentId) {
        final List<User> users = new ArrayList<>();
        userCollection.find(Filters.eq("documents", documentId)).forEach((Consumer<? super User>) users::add);
        return users;
    }

    public boolean uniqueUsername(String username) {
        return userCollection.countDocuments(Filters.eq("username", username)) == 0;
    }

    public User login(String username, String password) {
        return userCollection.find(
                Filters.and(
                        Filters.eq("username", username),
                        Filters.eq("password", password)
                )).first();
    }

}
