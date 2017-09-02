package com.german.downloader.rates;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class ExchangeRate {

    public String base;
    public Date date;
    public Map<String, Double> rates = new HashMap<>();
}
