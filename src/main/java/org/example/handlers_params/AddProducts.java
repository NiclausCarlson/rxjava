package org.example.handlers_params;

import org.bson.types.ObjectId;

import java.util.List;
import java.util.Map;

public class AddProducts implements NeedAuthorization{
    private final ObjectId userId;
    private String name;
    private String description;
    private double cost;

    public AddProducts(final Map<String, List<String>> queryParameters) {
        this.userId = parseUserId(queryParameters);
        this.name = parseString(queryParameters, "name");
        this.description = parseString(queryParameters, "description");
        this.cost = parseCost(queryParameters);
    }

    public String getDescription() {
        return description;
    }

    public String getName() {
        return name;
    }

    public double getCost() {
        return cost;
    }

    public ObjectId getUserId() {
        return userId;
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

    private String parseString(final Map<String, List<String>> queryParameters, final String key) {
        if (queryParameters.containsKey(key)) {
            var val = queryParameters.get(key);
            if (val.size() != 1) {
                throw new IllegalArgumentException("invalid argument: " + key);
            }
            return val.get(0);
        }
        throw new IllegalArgumentException(key + " not found");
    }

    private double parseCost(final Map<String, List<String>> queryParameters) {
        if (queryParameters.containsKey("cost")) {
            var val = queryParameters.get("cost");
            if (val.size() != 1) {
                throw new IllegalArgumentException("invalid cost argument");
            }
            return Double.parseDouble(val.get(0));
        }
        throw new IllegalArgumentException("cost not found");
    }
}
