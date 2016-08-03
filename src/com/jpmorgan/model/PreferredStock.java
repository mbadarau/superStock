package com.jpmorgan.model;

import com.jpmorgan.enums.StockSymbol;

public class PreferredStock extends com.jpmorgan.model.Stock {

    private double fixedDividend;

    public PreferredStock(StockSymbol symbol, double lastDividend, int parValue, double fixedDividend) {
        super(symbol, lastDividend, parValue);
        this.fixedDividend = fixedDividend;
    }

    public PreferredStock(StockSymbol symbol, double lastDividend, int parValue, double tickerPrice,
            double fixedDividend) {
        super(symbol, lastDividend, parValue, tickerPrice);
        this.fixedDividend = fixedDividend;
    }

    public double getFixedDividend() {
        return fixedDividend;
    }

    public void setFixedDividend(double fixedDividend) {
        this.fixedDividend = fixedDividend;
    }

}
