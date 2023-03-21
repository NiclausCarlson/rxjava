package org.example.handlers_params;

import org.bson.types.ObjectId;
import org.example.model.Currency;

import java.util.List;
import java.util.Map;

public class AddUser {
    private Currency prefereCurrency;

    public AddUser(final Map<String, List<String>> queryParameters) {
        this.prefereCurrency = parseCurrency(queryParameters);
    }

    private Currency parseCurrency(final Map<String, List<String>> queryParameters) {
        if (queryParameters.containsKey("currency")) {
            var val = queryParameters.get("currency");
            if (val.size() != 1) {
                throw new IllegalArgumentException("invalid currency argument");
            }
            var currency = val.get(0);
            if (currency.equals("rub") || currency.equals("RUB")) {
                return Currency.RUB;
            }
            if (currency.equals("dollar") || currency.equals("DOLLAR")) {
                return Currency.DOLLAR;
            }
            if (currency.equals("euro") || currency.equals("EURO")) {
                return Currency.EURO;
            }
            throw new IllegalArgumentException("Unknown currency");
        }
        throw new IllegalArgumentException("currency not found");
    }

    public Currency getPrefereCurrency() {
        return prefereCurrency;
    }
}
