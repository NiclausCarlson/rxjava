package org.example.db;

import com.mongodb.client.model.Filters;
import com.mongodb.rx.client.MongoClient;
import com.mongodb.rx.client.MongoClients;
import com.mongodb.rx.client.Success;
import org.example.model.Admin;
import org.example.model.Product;
import org.example.model.User;
import rx.Observable;

public class MongoDriver {
    private static final String database = "catalog_db";
    private static final String users = "users";
    private static final String products = "products";
    private static final String admins = "admins";

    private static final MongoClient client = createMongoClient();

    public Observable<Success> addUser(User user) {
        return client.getDatabase(database)
                .getCollection(users)
                .insertOne(user.asDocument());
    }

    public Observable<Success> addProduct(Product product) {
        return client.getDatabase(database)
                .getCollection(products)
                .insertOne(product.asDocument());
    }

    public Observable<Success> addAdmin(Admin admin) {
        return client.getDatabase(database)
                .getCollection(admins)
                .insertOne(admin.asDocument());
    }

    public Observable<User> getUser(long userId) {
        return client.getDatabase(database)
                .getCollection(users)
                .find(Filters.eq("id", userId))
                .toObservable()
                .map(User::new);
    }

    public Observable<Admin> getAdmin(long adminId) {
        return client.getDatabase(database)
                .getCollection(admins)
                .find(Filters.eq("id", adminId))
                .toObservable()
                .map(Admin::new);
    }

    public Observable<Product> getProduct(){
        return client.getDatabase(database)
                .getCollection(products)
                .find()
                .toObservable()
                .map(Product::new);
    }
    private static MongoClient createMongoClient() {
        return MongoClients.create("mongodb://localhost:27017");
    }
}
