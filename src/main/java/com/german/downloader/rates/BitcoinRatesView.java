package com.german.downloader.rates;

import com.german.downloader.rates.CurrencyData.CoinDeskPrices;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collections;

@RestController
public class BitcoinRatesView {

    @GetMapping("bitcoin")
    CoinDeskPrices bitcoinRate() {
        return new CurrencyData(
                Collections.singletonList("BTC"),
                "https://api.coindesk.com/v1/bpi/currentprice.json")
                .btc().orElseThrow(() -> new RuntimeException("not available"));
    }

}
