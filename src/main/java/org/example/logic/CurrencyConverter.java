package org.example.logic;

import org.example.model.Currency;

public class CurrencyConverter {
    private static double rubCoeff = 1;
    private static double usdCoeff = 0.8;
    private static double euroCoeff = 0.5;

    public CurrencyConverter(double rubCoeff, double usdCoeff, double euroCoeff) {
        CurrencyConverter.rubCoeff = rubCoeff;
        CurrencyConverter.usdCoeff = usdCoeff;
        CurrencyConverter.euroCoeff = euroCoeff;
    }

    public static double convertFromRub(Currency to, double val){
        if (to == Currency.RUB) {
            return val;
        }
        if (to == Currency.DOLLAR) {
            return val*usdCoeff;
        }
        return val *euroCoeff;
    }
    public static double convertToRub(Currency from, double val) {
        if (from == Currency.RUB) {
            return val;
        }
        if (from == Currency.DOLLAR) {
            return val / usdCoeff;
        }
        return val / euroCoeff;
    }

    public static String getCurrencySign(Currency currency){
        if(currency == Currency.RUB){
            return "rub";
        }
        if (currency == Currency.DOLLAR){
            return "usd";
        }
        return "eur";
    }
}
