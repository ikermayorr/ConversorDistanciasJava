package com.monlau.conversordistanciasjava;

import java.util.LinkedHashMap;
import java.util.Map;

public class CurrencyConverter {

    private static class CurrencyUnit {
        double rateToEUR;

        CurrencyUnit(double rateToEUR) {
            this.rateToEUR = rateToEUR;
        }
    }

    private final Map<String, CurrencyUnit> currencies =
            new LinkedHashMap<>();

    public CurrencyConverter() {
        currencies.put("Euro (EUR)", new CurrencyUnit(1.0));
        currencies.put("US Dollar (USD)", new CurrencyUnit(0.92));
        currencies.put("British Pound (GBP)", new CurrencyUnit(1.17));
        currencies.put("Japanese Yen (JPY)", new CurrencyUnit(0.0062));
        currencies.put("Swiss Franc (CHF)", new CurrencyUnit(1.05));
    }

    public String[] getCurrencyNames() {
        return currencies.keySet().toArray(new String[0]);
    }

    public double convert(double value, String from, String to) {

        CurrencyUnit source = currencies.get(from);
        CurrencyUnit target = currencies.get(to);

        if (source == null || target == null) {
            throw new IllegalArgumentException("Invalid currency");
        }

        double valueInEUR = value * source.rateToEUR;

        return valueInEUR / target.rateToEUR;
    }
}
