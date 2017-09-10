package com.german.downloader.rates;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.util.Map;
import java.util.Optional;

public class BitcoinData {

    private final String url = "https://api.coindesk.com/v1/bpi/currentprice.json";

    public Optional<CoinDeskPrices> btc() {
        return RemoteJson.fetch(url, CoinDeskPrices.class);
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
