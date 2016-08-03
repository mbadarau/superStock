package com.jpmorgan.services.impl;

import java.util.LinkedList;
import java.util.List;
import java.util.ListIterator;
import java.util.Map;

import com.jpmorgan.model.PreferredStock;
import com.jpmorgan.model.Stock;
import com.jpmorgan.model.Trade;
import com.jpmorgan.services.CommonService;
import com.jpmorgan.services.StockService;

public class StockServiceImpl implements StockService {

    private CommonService commonService = new CommonServiceImpl();

    @Override
    public double getDividendYield(Stock stock) {
        if (stock instanceof PreferredStock) {
            return (((PreferredStock) stock).getFixedDividend() * stock.getParValue()) / stock.getPrice();
        }
        return (stock.getLastDividend() / stock.getPrice());
    }

    @Override
    public double getPeRatio(double tickerPrice, double dividendYield) {
        return (tickerPrice / dividendYield);
    }

    @Override
    public double getStockPrice(List<Trade> trades) {
        if (trades != null) {
            double[] tradesPricesArray = new double[trades.size()];
            double[] tradesQuantitiesArray = new double[trades.size()];
            for (ListIterator<Trade> iterator = trades.listIterator(); iterator.hasNext();) {
                int i = iterator.nextIndex();
                Trade trade = iterator.next();
                tradesPricesArray[i] = trade.getPrice();
                tradesQuantitiesArray[i] = trade.getSharesQuantity();
            }
            if (tradesPricesArray.length != 0 && tradesQuantitiesArray.length != 0) {
                double pricesPerQuantitiesSum = 0d;
                double quantitiesSum = 0d;
                for (int i = 0; i < tradesPricesArray.length; i++) {
                    pricesPerQuantitiesSum += tradesPricesArray[i] * tradesQuantitiesArray[i];
                    quantitiesSum += tradesQuantitiesArray[i];
                }
                return pricesPerQuantitiesSum / quantitiesSum;
            }
        }
        return 0d;
    }

    @Override
    public double getSharesIndexes(List<Stock> stocks) {
        double[] tradesPricesArray = new double[stocks.size()];
        double totalParValues = 0d;
        for (ListIterator<Stock> iterator = stocks.listIterator(); iterator.hasNext();) {
            int i = iterator.nextIndex();
            Stock stock = iterator.next();
            totalParValues += stock.getParValue();
            tradesPricesArray[i] = stock.getPrice();
        }
        double geometricMean = commonService.getGeometricMean(tradesPricesArray);
        return totalParValues != 0d ? geometricMean / totalParValues : 0d;
    }

    @Override
    public Map<Stock, List<Trade>> addTrade(Map<Stock, List<Trade>> map, Trade trade) {
        List<Trade> tradesList = map.get(trade.getStock());
        if (tradesList == null) {
            tradesList = new LinkedList<>();
        }
        tradesList.add(trade);
        map.put(trade.getStock(), tradesList);
        return map;
    }

}
