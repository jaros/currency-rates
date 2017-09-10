package com.german.downloader.rates;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
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
    private final String forDateUrl;
    private final ObjectMapper objectMapper = new ObjectMapper();

    private Map<LocalDate, List<ExchangeRate>> ratesCache = new HashMap<>();
    private final List<String> fetchedCurrencies;


    public CurrencyData(List<String> fetchedCurrencies, String fetchUrl) {
        this.fetchedCurrencies = fetchedCurrencies;
        forDateUrl = fetchUrl;
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

    public Optional<CoinDeskPrices> btc() {
        try {
            CoinDeskPrices prices = objectMapper.readValue(new URL(forDateUrl), CoinDeskPrices.class);
            return Optional.of(prices);
        } catch (Exception e) {
            logger.error("cannot get exchange rate for BTC");
            return Optional.empty();
        }
    }

    @JsonIgnoreProperties(ignoreUnknown = true)
    static class CoinDeskPrices {
        public Map<String, CryptCurrency> bpi;
    }

    static class CryptCurrency {
        public String code;
        public String symbol;
        public String rate;
        public String description;
        public Double rate_float;
    }
}
