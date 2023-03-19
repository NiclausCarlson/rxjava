package org.example.model;


import org.bson.Document;

public class User implements MongoModel {
    private final long id;
    private final Currency prefereCurrency;

    public User(long id, Currency prefereCurrency) {
        this.id = id;
        this.prefereCurrency = prefereCurrency;
    }

    public User(Document document) {
        this.id = document.getLong("id");
        this.prefereCurrency = (Currency) document.get("prefereCurrency");
    }

    public Currency getPrefereCurrency() {
        return prefereCurrency;
    }

    public long getId() {
        return id;
    }

    @Override
    public Document asDocument() {
        return new Document("id", id).append("prefereCurrency", prefereCurrency.toString());
    }
}
