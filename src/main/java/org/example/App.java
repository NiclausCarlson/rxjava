package org.example;

import io.netty.handler.codec.http.HttpResponseStatus;
import io.reactivex.netty.protocol.http.server.HttpServer;
import io.reactivex.netty.protocol.http.server.HttpServerRequest;
import org.bson.types.ObjectId;
import org.example.db.MongoDriver;
import org.example.handlers_params.AddProducts;
import org.example.handlers_params.AddUser;
import org.example.handlers_params.GetProducts;
import org.example.handlers_params.NeedAuthorization;
import org.example.logic.CurrencyConverter;
import org.example.model.Currency;
import org.example.model.Product;
import org.example.model.User;
import rx.Observable;

import java.util.concurrent.atomic.AtomicReference;

public class App {
    private static final MongoDriver mongoDriver = new MongoDriver();

    public static void main(String[] args) {
        HttpServer
                .newServer(8080)
                .start((req, resp) -> {
                    Observable<String> response;
                    try {
                        response = getMapping(req).onErrorReturn(Throwable::getMessage);
                    } catch (Exception ex) {
                        response = Observable.just(ex.getMessage());
                        resp.setStatus(HttpResponseStatus.BAD_REQUEST);
                    }
                    return resp.writeString(response);
                })
                .awaitShutdown();
    }

    private static class UserAndQuery<T extends NeedAuthorization> {
        T query;
        User user;

        public UserAndQuery(final T query, final User user) {
            this.query = query;
            this.user = user;
        }
    }

    private static <T extends NeedAuthorization> Observable<UserAndQuery<T>> checkAuthorization(T queryParams) {
        return mongoDriver
                .getUser(queryParams.getUserId())
                .map(user -> new UserAndQuery<>(queryParams, user))
                .switchIfEmpty(Observable.error(new IllegalStateException("Unauthorized\n")));
    }

    private static <T> Observable<String> getMapping(final
                                                     HttpServerRequest<T> request) {
        var decodedStr = request.getDecodedPath();
        var path = decodedStr.substring(decodedStr.lastIndexOf('/') + 1);
        var query = request.getQueryParameters();
        if (path.equals("get-products")) {
            return Observable
                    .just(new GetProducts(query))
                    .flatMap(App::checkAuthorization)
                    .flatMap(u -> mongoDriver.getProduct(u.user.getPrefereCurrency()))
                    .map(p -> p.getId() +
                                    " " + p.getName() +
                                    " " + p.getDescription() +
                                    " " + p.getCost() +
                                    " " + p.getCostSign() +
                            "\n"
                    );
        }
        if (path.equals("add-product")) {
            return Observable
                    .just(new AddProducts(query))
                    .flatMap(App::checkAuthorization)
                    .map(p -> {
                        var currency = CurrencyConverter.convertToRub(
                                p.user.getPrefereCurrency(),
                                p.query.getCost());
                        return new Product(p.query.getName(), p.query.getDescription(), currency);
                    })
                    .flatMap(mongoDriver::addProduct)
                    .map(s -> "Product successfully added\n");
        }
        if (path.equals("add-user")) {
            AtomicReference<String> str = new AtomicReference<>("");
            return Observable
                    .just(new AddUser(query))
                    .map(p -> {
                        var id = new ObjectId();
                        str.set(id.toString());
                        return new User(id, p.getPrefereCurrency());
                    })
                    .flatMap(mongoDriver::addUser)
                    .map(k -> "UserId: " + str.get() + "\n");
        }
        throw new IllegalStateException("Unknown handle");
    }
}
