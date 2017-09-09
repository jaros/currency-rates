package com.german.downloader.rates;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.net.URL;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

public class CurrencyData {

    private static final Logger logger = LoggerFactory.getLogger(CurrencyData.class);
    private static final String forDateUrl = "http://api.fixer.io/%s?base=%s";
    private final ObjectMapper objectMapper = new ObjectMapper();

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
        try {
            URL url = new URL(String.format(forDateUrl, date, currency));
            return Optional.of(objectMapper.readValue(url, ExchangeRate.class));
        } catch (IOException e) {
            logger.error("cannot get exchange rate for {}", currency);
            return Optional.empty();
        }
    }
}
