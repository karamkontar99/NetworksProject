package main.repos;

import com.mongodb.client.MongoCollection;
import com.mongodb.client.model.Filters;
import main.models.Document;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;

public class DocumentRepository implements Repository<Document> {
    private MongoCollection<Document> documentCollection;

    public DocumentRepository(DBConfig dbConfig) {
        documentCollection = dbConfig.getDatabase().getCollection("documents", Document.class);
    }

    public void insert(Document document) {
        documentCollection.insertOne(document);
    }

    public void delete(String documentId) {
        documentCollection.deleteOne(Filters.eq("_id", documentId));
    }

    public void update(Document document) {
        documentCollection.replaceOne(Filters.eq("_id", document.getId()), document);
    }

    public List<Document> findAll() {
        final List<Document> documents = new ArrayList<>();
        documentCollection.find().forEach((Consumer<? super Document>) documents::add);
        return documents;
    }

    public Document find(String documentId) {
        return documentCollection.find(Filters.eq("_id", documentId)).first();
    }

    public List<Document> findAllOfUser(String userId) {
        final List<Document> documents = new ArrayList<>();
        documentCollection.find(Filters.eq("users", userId)).forEach((Consumer<? super Document>) documents::add);
        return documents;
    }
}
