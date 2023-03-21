package org.example.model;


import org.bson.Document;
import org.bson.types.ObjectId;

import java.util.Objects;

public class User implements MongoModel {
    private final ObjectId id;
    private final Currency prefereCurrency;

    public User(ObjectId objectId, Currency prefereCurrency) {
        this.id = objectId;
        this.prefereCurrency = prefereCurrency;
    }

    public User(Document document) {
        this.id = document.getObjectId("_id");
        this.prefereCurrency = parseCurrency((String) document.get("prefereCurrency"));
    }

    public Currency getPrefereCurrency() {
        return prefereCurrency;
    }

    public ObjectId getId() {
        return id;
    }

    @Override
    public Document asDocument() {
        return new Document("_id", id).append("prefereCurrency", prefereCurrency.toString());
    }

    private Currency parseCurrency(String currency) {
        if (Objects.equals(currency, "RUB")) {
            return Currency.RUB;
        }
        if (Objects.equals(currency, "DOLLAR")) {
            return Currency.DOLLAR;
        }
        if (Objects.equals(currency, "EURO")) {
            return Currency.EURO;
        }
        throw new IllegalStateException("unknown currency in database");
    }
}
