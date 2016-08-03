package com.jpmorgan.services;

import java.util.List;
import java.util.Map;

import com.jpmorgan.model.Stock;
import com.jpmorgan.model.Trade;

public interface StockService {

    double getDividendYield(Stock stock);

    double getPeRatio(double tickerPrice, double dividendYield);

    double getStockPrice(List<Trade> trades);

    double getSharesIndexes(List<Stock> stocks);

    Map<Stock, List<Trade>> addTrade(Map<Stock, List<Trade>> map, Trade trade);

}