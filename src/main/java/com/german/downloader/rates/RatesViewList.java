package com.german.downloader.rates;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import java.io.IOException;

import static java.util.Arrays.asList;


@Controller
@RequestMapping("/rates")
public class RatesViewList {

    private CurrencyData currencyData = new CurrencyData(
            asList("EUR", "USD", "CHF", "RUB"),
            "http://api.fixer.io/%s?base=%s");

    @GetMapping
    ModelAndView rates() throws IOException {
        return new ModelAndView("rates/list", "rates", currencyData.lastExchangeRates());
    }
}
