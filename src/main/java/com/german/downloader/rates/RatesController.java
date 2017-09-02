package com.german.downloader.rates;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import java.io.IOException;
import java.net.URL;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

import static java.util.Arrays.asList;


@Controller
@RequestMapping("/rates")
public class RatesController {

    private static final Logger logger = LoggerFactory.getLogger(RatesController.class);
    private static final String apiUrl = "http://api.fixer.io/latest?base=";
    private static final List<String> fetchedCurrencies = asList("EUR", "USD", "CHF", "RUB");

    private final ObjectMapper objectMapper = new ObjectMapper();

    private Map<LocalDate, List<ExchangeRate>> ratesHistory = new HashMap<>();


    @GetMapping
    ModelAndView rates() throws IOException {
        List<ExchangeRate> exchangeRates = ratesHistory.computeIfAbsent(LocalDate.now(), date -> lastExchangeRates());
        return new ModelAndView("rates/list", "rates", exchangeRates);
    }

    private List<ExchangeRate> lastExchangeRates() {
        return fetchedCurrencies.stream()
                .map(this::requestExchangeRate)
                .filter(Optional::isPresent)
                .map(Optional::get)
                .collect(Collectors.toList());
    }

    private Optional<ExchangeRate> requestExchangeRate(String currency) {
        try {
            return Optional.of(objectMapper.readValue(new URL(apiUrl + currency), ExchangeRate.class));
        } catch (IOException e) {
            logger.error("cannot get exchange rate for {}", currency);
            return Optional.empty();
        }
    }

}
