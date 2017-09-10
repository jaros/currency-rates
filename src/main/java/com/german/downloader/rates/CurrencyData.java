package com.german.downloader.rates;

import java.time.LocalDate;
import java.util.*;
import java.util.stream.Collectors;

public class CurrencyData {

    private final String forDateUrl = "http://api.fixer.io/%s?base=%s";

    private Map<LocalDate, List<ExchangeRate>> ratesCache = new HashMap<>();
    private final List<String> fetchedCurrencies;


    public CurrencyData(List<String> fetchedCurrencies) {
        this.fetchedCurrencies = fetchedCurrencies;
    }

    public List<ExchangeRate> lastExchangeRates() {
        return exchangeRatesForDay(LocalDate.now());
    }

    public List<ExchangeRate> exchangeRatesForDay(LocalDate date) {
        return ratesCache.computeIfAbsent(date, this::externalExchangeRates);
    }

    private List<ExchangeRate> externalExchangeRates(LocalDate forDate) {
        return fetchedCurrencies.stream()
                .map(currency -> exchangeRateForCurrency(currency, forDate))
                .filter(Optional::isPresent)
                .map(Optional::get)
                .collect(Collectors.toList());
    }

    private Optional<ExchangeRate> exchangeRateForCurrency(String currency, LocalDate date) {
        return RemoteJson.fetch(String.format(forDateUrl, date, currency), ExchangeRate.class);
    }

    public static class ExchangeRate {
        public String base;
        public Date date;
        public Map<String, Double> rates = new HashMap<>();
    }
}
