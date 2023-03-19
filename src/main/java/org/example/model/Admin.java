package org.example.model;

import org.bson.Document;

public class Admin implements MongoModel {
    private long id;

    public Admin(long id) {
        this.id = id;
    }

    public Admin(Document admin) {
        this.id = admin.getLong("id");
    }

    @Override
    public Document asDocument() {
        return new Document("id", this.id);
    }
}
