package org.example.model;

import org.bson.Document;

public class Product implements MongoModel {
    private long id;
    private String name;
    private String description;
    private double cost;

    public Product(long id, String name, String description, double cost) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.cost = cost;
    }

    public Product(Document document) {
        this.id = document.getLong("id");
        this.name = document.getString("name");
        this.description = document.getString("description");
        this.cost = document.getDouble("cost");
    }

    public long getId() {
        return id;
    }

    public double getCost() {
        return cost;
    }

    public String getDescription() {
        return description;
    }

    public String getName() {
        return name;
    }

    @Override
    public Document asDocument() {
        return new Document("id", this.id)
                .append("name", this.name)
                .append("description", this.description)
                .append("cost", this.cost);
    }
}
