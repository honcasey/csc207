package Transactions;

import java.time.LocalDate;
import java.util.*;
import java.util.UUID;
import Users.TradingUser;
import Users.TransactionHistory;

/**
 * <h1>PastTransactionManager</h1>
 * Manages methods regarding TransactionHistory of a User, extends TransactionManager.
 */
public class PastTransactionManager extends TransactionManager{

    /**
     * Constructs an instance of a PastTransactionManager.
     * @param transactions hashmap of all transactions and their UUID
     */
    public PastTransactionManager(Map<UUID, Transaction> transactions){
        super(transactions);
    }

    /** A helper method for weeklyThresholdExceeded */
    private int numTransactionsInWeek(TradingUser tradingUser){
        // get transaction history
        TransactionHistory transactionHistory = tradingUser.getTransactionHistory();
        List<UUID> transactionHistoryId = transactionHistory.getAllPastTransactions();

        // number of transactions in a week
        int numTransactions = 0;

        //  create a list of transactions from the UUIDs of transaction history lists
        List<Transaction> allTransactions = getTransactionsFromIdList(transactionHistoryId);

        // instance of calendar
        Calendar currentCal = Calendar.getInstance();
        int week = currentCal.get(Calendar.WEEK_OF_YEAR);
        int year = currentCal.get(Calendar.YEAR);
        Calendar targetCal = Calendar.getInstance();

        int targetWeek;
        int targetYear;
        // for each transaction in the list, look at the date of the transaction (the date of the last meeting) and compare it to the current dates above

        for (int i = 0; i < allTransactions.size();){
            LocalDate date;
            Transaction currTrans = allTransactions.get(i);
            date = currTrans.getTransactionMeetings().get(currTrans.getTransactionMeetings().size() - 1).getDate();
            Date setDate = java.sql.Date.valueOf(date);
            targetCal.setTime(setDate);
            targetWeek = targetCal.get(Calendar.WEEK_OF_YEAR);
            targetYear = targetCal.get(Calendar.YEAR);

            // if the week number of the date of the transaction matches the current calendar, increment numTransactions
            if(targetWeek == week && targetYear == year){
                numTransactions ++;
            }
            i++;
        }
        // main idea of this code from: https://stackoverflow.com/questions/10313797/how-to-check-a-day-is-in-the-current-week-in-java
        // answer by Benjamin Cox
        return numTransactions;
    }

    /**
     * Returns if the tradingUser has exceeded the weekly limit of transactions
     * @param tradingUser TradingUser of Interest
     * @return boolean
     */
    public boolean weeklyThresholdExceeded(TradingUser tradingUser) {
        int threshold = tradingUser.getWeeklyThreshold();
        int numberWeeklyTransactions = numTransactionsInWeek(tradingUser);
        return numberWeeklyTransactions >= threshold;
    }
}