package org.example.handlers_params;

import org.bson.types.ObjectId;

import java.util.List;
import java.util.Map;

public class GetProducts implements NeedAuthorization {
    private final ObjectId userId;

    public GetProducts(final Map<String, List<String>> queryParameters) {
        this.userId = parseUserId(queryParameters);
    }

    private ObjectId parseUserId(final Map<String, List<String>> queryParameters) {
        if (queryParameters.containsKey("userId")) {
            var val = queryParameters.get("userId");
            if (val.size() != 1) {
                throw new IllegalArgumentException("invalid userId argument");
            }
            return new ObjectId(val.get(0));
        }
        throw new IllegalArgumentException("userId not found");
    }

    public ObjectId getUserId() {
        return userId;
    }
}
