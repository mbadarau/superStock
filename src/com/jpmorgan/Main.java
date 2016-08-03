package com.jpmorgan;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Random;

import com.jpmorgan.constants.Constants;
import com.jpmorgan.enums.StockSymbol;
import com.jpmorgan.enums.TradeOperationFlag;
import com.jpmorgan.model.PreferredStock;
import com.jpmorgan.model.Stock;
import com.jpmorgan.model.Trade;
import com.jpmorgan.services.StockService;
import com.jpmorgan.services.impl.StockServiceImpl;

public class Main {

    private static final Random RANDOM = new Random();
    // Initialize Stock list with afferent values.
    private final static List<Stock> STOCKS = new ArrayList<>();
    static {
        STOCKS.add(new Stock(StockSymbol.TEA, 0, 100));
        STOCKS.add(new Stock(StockSymbol.POP, 8, 100));
        STOCKS.add(new Stock(StockSymbol.ALE, 23, 60));
        STOCKS.add(new PreferredStock(StockSymbol.GIN, 8, 100, 0.02d));
        STOCKS.add(new Stock(StockSymbol.JOE, 13, 250));
    }

    private static StockService stockService = new StockServiceImpl();

    public static void main(String[] args) {
        final int tradesNumber = RANDOM.nextInt(10000);
        System.out.println(Constants.TOTAL_TRADES + tradesNumber);
        Map<Stock, List<Trade>> map = new HashMap<>();
        try {
            for (int i = 0; i < tradesNumber; i++) {
                initializeTrades(map);
            }
            avoidNegativePrice(map);
            printResult(map);
        } catch (Exception e) {
            System.err.println(e.getMessage());
            e.printStackTrace();
        }

    }

    private static int getCongruentSharesQuantity(List<Trade> stockTradesList, TradeOperationFlag tradeOperationFlag,
            Random rand) {
        int result = rand.nextInt(1000);
        int totalShares = 0;
        for (Iterator<Trade> iterator = stockTradesList.iterator(); iterator.hasNext();) {
            Trade trade = iterator.next();
            totalShares += (trade.getSharesQuantity()
                    * ((trade.getTradeOperationFlag().equals(TradeOperationFlag.SELL) ? -1 : 1)));
        }
        if (tradeOperationFlag.equals(TradeOperationFlag.SELL)) {
            while (totalShares - result < 0) {
                result = rand.nextInt(1000);
            }
        }

        return result;
    }

    private static void initializeTrades(Map<Stock, List<Trade>> map){
        final Stock stock = STOCKS.get(RANDOM.nextInt(STOCKS.size()));
        LinkedList<Trade> trades = (LinkedList<Trade>) map.get(stock);
        TradeOperationFlag tradeOperation;
        if (trades == null) {
            trades = new LinkedList<>();
            tradeOperation = TradeOperationFlag.BUY;
        } else {
            tradeOperation = (RANDOM.nextBoolean() ? TradeOperationFlag.BUY : TradeOperationFlag.SELL);
        }
        double price = Math.abs(Math.round(10 * (RANDOM.nextGaussian() + 1) * 100) / 100d);
        int quantity = getCongruentSharesQuantity(trades, tradeOperation, RANDOM);
        stockService.addTrade(map, new Trade(stock, new Date(), quantity, tradeOperation, price));
    }

    private static void printResult(final Map<Stock, List<Trade>> map){
        for (Stock stock : STOCKS) {
            List<Trade> tradeForStock = map.get(stock);
            double tickerPrice = tradeForStock.get(tradeForStock.size() - 1).getPrice()
                    - tradeForStock.get(0).getPrice();
            String stockSymbol = stock.getSymbol().name();
            stock.setPrice(tickerPrice);
            double dividendYield = stockService.getDividendYield(stock);
            System.out.println(Constants.DIVIDEND_YIELD + stockSymbol + Constants.IMPLIES + dividendYield);
            System.out.println(Constants.PE_RATIO + stockSymbol + Constants.IMPLIES
                    + stockService.getPeRatio(stock.getPrice(), dividendYield));
            System.out.println(Constants.STOCK_PRICE + stockSymbol + Constants.IMPLIES + stockService.getStockPrice(map.get(stock)));
        }
        System.out.println(Constants.GBCE_SHARE_INDEX + stockService.getSharesIndexes(STOCKS));
    }

    private static void avoidNegativePrice(Map<Stock, List<Trade>> map) {
        for (Stock stock : map.keySet()) {
            List<Trade> listTrades = map.get(stock);
            if (listTrades.get(listTrades.size() - 1).getPrice() < listTrades.get(0).getPrice()) {
                listTrades.get(listTrades.size() - 1).setPrice(listTrades.get(0).getPrice() + 1d);
            }
        }

    }

}
