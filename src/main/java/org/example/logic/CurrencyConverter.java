package org.example.logic;

import org.example.model.Currency;

public class CurrencyConverter {
    private double rubCoeff = 1;
    private double usdCoeff = 0.8;
    private double euroCoeff = 0.5;

    public CurrencyConverter(double rubCoeff, double usdCoeff, double euroCoeff) {
        this.rubCoeff = rubCoeff;
        this.usdCoeff = usdCoeff;
        this.euroCoeff = euroCoeff;
    }

    public double convert(Currency from, Currency to, double val) {
        if (from.equals(to)) {
            return val;
        }
        return convert(to, convert(Currency.RUB, val));
    }

    private double convert(Currency to, double val) {
        if (to == Currency.DOLLAR) {
            return val * usdCoeff;
        }
        if (to == Currency.RUB) {
            return val * rubCoeff;
        }
        return val * euroCoeff;
    }
}
