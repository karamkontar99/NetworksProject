package edu.networks.project.models;

public class Document {
    private String name;
    private int size;
    private boolean downloaded;

    public Document(String name, int size, boolean downloaded) {
        this.name = name;
        this.size = size;
        this.downloaded = downloaded;
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

    public boolean isDownloaded() {
        return downloaded;
    }

    public void setDownloaded(boolean downloaded) {
        this.downloaded = downloaded;
    }
}