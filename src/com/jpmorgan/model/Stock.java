package com.jpmorgan.model;

import com.jpmorgan.enums.StockSymbol;

public class Stock {

    private StockSymbol symbol;
    private double lastDividend;
    private double parValue;
    private double price;

    public Stock(StockSymbol symbol, double lastDividend, double parValue) {
        this.symbol = symbol;
        this.lastDividend = lastDividend;
        this.parValue = parValue;
    }

    public Stock(StockSymbol symbol, double lastDividend, double parValue, double price) {
        this.symbol = symbol;
        this.lastDividend = lastDividend;
        this.parValue = parValue;
        this.price = price;
    }

    public StockSymbol getSymbol() {
        return symbol;
    }

    public void setSymbol(StockSymbol symbol) {
        this.symbol = symbol;
    }

    public double getLastDividend() {
        return lastDividend;
    }

    public void setLastDividend(double lastDividend) {
        this.lastDividend = lastDividend;
    }

    public double getParValue() {
        return parValue;
    }

    public void setParValue(double parValue) {
        this.parValue = parValue;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    @Override
    public int hashCode() {
        return 17 * 31 * ((this.symbol).hashCode());
    }

    @Override
    public boolean equals(Object obj) {
        boolean result = false;
        if (obj instanceof Stock) {
            Stock stock = (Stock) obj;
            result = this.symbol.equals(stock.getSymbol());
        }
        return result;
    }

}
