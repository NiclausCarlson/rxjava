package org.example.db;

import com.mongodb.client.model.Filters;
import com.mongodb.rx.client.MongoClient;
import com.mongodb.rx.client.MongoClients;
import com.mongodb.rx.client.Success;
import org.bson.types.ObjectId;
import org.example.model.Currency;
import org.example.model.Product;
import org.example.model.User;
import rx.Observable;

public class MongoDriver {
    private static final String database = "catalog_db";
    private static final String users = "users";
    private static final String products = "products";

    private static final MongoClient client = createMongoClient();

    public Observable<Success> addUser(User user) {
        return client
                .getDatabase(database)
                .getCollection(users)
                .insertOne(user.asDocument());
    }

    public Observable<Success> addProduct(Product product) {
        return client
                .getDatabase(database)
                .getCollection(products)
                .insertOne(product.asDocument());
    }

    public Observable<User> getUser(ObjectId userId) {
        return client
                .getDatabase(database)
                .getCollection(users)
                .find(Filters.eq("_id", userId))
                .toObservable()
                .map(User::new);
    }


    public Observable<Product> getProduct(Currency currency) {
        return client
                .getDatabase(database)
                .getCollection(products)
                .find()
                .toObservable()
                .map(p -> new Product(p, currency));
    }

    private static MongoClient createMongoClient() {
        return MongoClients.create("mongodb://localhost:27017");
    }
}
