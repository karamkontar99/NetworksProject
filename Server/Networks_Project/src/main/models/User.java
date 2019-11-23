package main.models;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class User {
    private String id;
    private String name;
    private String address;
    private String email;
    private String username;
    private String password;
    private List<UUID> documents;

    public User() {
        id = UUID.randomUUID().toString();
        documents = new ArrayList<>();
    }

    public User(String name, String address, String email, String username, String password) {
        id = UUID.randomUUID().toString();
        this.name = name;
        this.address = address;
        this.email = email;
        this.username = username;
        this.password = password;
        documents = new ArrayList<>();
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public List<UUID> getDocuments() {
        return documents;
    }

    public void setDocuments(List<UUID> documents) {
        this.documents = documents;
    }

    public void addDocument(UUID documentId) {
        this.documents.add(documentId);
    }

    public void removeDocument(UUID documentId) {
        this.documents.remove(documentId);
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", email='" + email + '\'' +
                ", address='" + address + '\'' +
                ", username='" + username + '\'' +
                ", password='" + password + '\'' +
                ", documents=" + documents +
                '}';
    }
}
