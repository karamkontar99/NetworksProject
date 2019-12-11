package edu.networks.project.models;

import java.util.UUID;

public class Document {
    private UUID id;
    private String name;
    private int size;

    public Document() {
        id = UUID.randomUUID();
    }

    public Document(String name, int size) {
        id = UUID.randomUUID();
        this.name = name;
        this.size = size;
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

    public int getSize() {
        return size;
    }

    public void setSize(int size) {
        this.size = size;
    }

}