package org.example;

import io.netty.handler.codec.http.HttpMethod;
import io.reactivex.netty.protocol.http.server.HttpServer;
import io.reactivex.netty.protocol.http.server.HttpServerRequest;
import org.example.db.MongoDriver;
import rx.Observable;

public class App {
    private static final MongoDriver mongoDriver = new MongoDriver();

    public static void main(String[] args) {
        HttpServer
                .newServer(8080)
                .start((req, resp) -> {
                    Observable<String> response;
                    if (req.getHttpMethod() == HttpMethod.GET) {
                        response = getMapping(req);
                    } else if (req.getHttpMethod() == HttpMethod.POST) {
                        response = postMapping(req);
                    } else {
                        throw new IllegalStateException();
                    }
                    return resp.writeString(response);
                })
                .awaitShutdown();
    }

    private static <T> Observable<String> getMapping(final
                                                     HttpServerRequest<T> request) {
        var path = request.getUri();
        if (path.equals("/get-products")) {
            return Observable
                    .just(path);
        }
        throw new IllegalStateException();
    }

    private static <T> Observable<String> postMapping(final
                                                      HttpServerRequest<T> request) {
        var path = request.getUri();
        if (path.equals("/add-product")) {
            return Observable
                    .just(path);
        }
        if (path.equals("/add-user")) {
            return Observable
                    .just(path);
        }
        if (path.equals("/add-admin")) {
            return Observable
                    .just(path);
        }
        throw new IllegalStateException();
    }
}
