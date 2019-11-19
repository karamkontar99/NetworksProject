package main.models;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;


public class Document {

    private UUID id;
    private String name;
    private byte[] content;
    private List<UUID> users;

    public Document() {
        id = UUID.randomUUID();
        users = new ArrayList<>();
    }

    public Document(String name, byte[] content) {
        id = UUID.randomUUID();
        this.name = name;
        this.content = content;
        users = new ArrayList<>();
    }

    public UUID getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public byte[] getContent() {
        return content;
    }

    public void setContent(byte[] content) {
        this.content = content;
    }

    public List<UUID> getUsers() {
        return users;
    }

    public void setUsers(List<UUID> users) {
        this.users = users;
    }

    public void addUser(UUID userId) {
        this.users.add(userId);
    }

    public void removeUser(UUID userId) {
        this.users.remove(userId);
    }

    @Override
    public String toString() {
        return "Document{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", content='" + Arrays.toString(content) + '\'' +
                ", users=" + users +
                '}';
    }
}
