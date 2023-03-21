package org.example.model;

import org.bson.Document;
import org.bson.types.ObjectId;

import org.example.logic.CurrencyConverter;

public class Product implements MongoModel {
    private ObjectId id;
    private String name;
    private String description;
    private double cost;
    private String costSign;

    public Product(String name, String description, double cost) {
        this.id = new ObjectId();
        this.name = name;
        this.description = description;
        this.cost = cost;
    }

    public Product(Document document, Currency currency) {
        this.id = document.getObjectId("_id");
        this.name = document.getString("name");
        this.description = document.getString("description");
        var tmp_cost = document.getDouble("cost");
        this.cost = CurrencyConverter.convertFromRub(currency, tmp_cost);
        this.costSign = CurrencyConverter.getCurrencySign(currency);
    }

    public ObjectId getId() {
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
        return new Document("_id", this.id)
                .append("name", this.name)
                .append("description", this.description)
                .append("cost", this.cost);
    }

    public String getCostSign() {
        return costSign;
    }
}
