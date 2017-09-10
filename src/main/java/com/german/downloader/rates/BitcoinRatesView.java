package com.german.downloader.rates;

import com.german.downloader.rates.BitcoinData.CoinDeskPrices;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class BitcoinRatesView {

    @GetMapping("bitcoin")
    CoinDeskPrices bitcoinRate() {
        return new BitcoinData().btc().orElseThrow(() -> new RuntimeException("not available"));
    }

}
