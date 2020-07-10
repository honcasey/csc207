package Transactions;

import java.time.LocalDate;
import java.util.*;
import java.util.UUID;
import java.util.Map;

import Exceptions.InvalidTransactionException;
import Users.User;
import Users.TransactionHistory;

public class PastTransactionManager extends TransactionManager{
    private Map<UUID, Transaction> allTransactions;
    public PastTransactionManager(Map<UUID, Transaction> transactions){
        super(transactions);

    }

    public List<Transaction> generateTransactionsList(TransactionHistory transactionHistory) throws InvalidTransactionException {
        List<UUID>pastTransactionsIDs = transactionHistory.getAllTransactions();
        return getTransactionsFromIdList(pastTransactionsIDs);
    }

    public int numTransactionsInWeek(User user) throws InvalidTransactionException {
        TransactionHistory transactionHistory = user.getTransactionHistory();
        int numTransactions = 0;
        List<Transaction> allTransactions = generateTransactionsList(transactionHistory);
//        ZoneId k = ZoneId.of("America/Montreal");
//        LocalDate today = LocalDate.now(k); alternative way of doing it; saving just in case
        Calendar currentCal = Calendar.getInstance();
        int week = currentCal.get(Calendar.WEEK_OF_YEAR);
        int year = currentCal.get(Calendar.YEAR);
        Calendar targetCal = Calendar.getInstance();
        int targetWeek;
        int targetYear;
        for (int i = 0; i < allTransactions.size();){
            LocalDate date;
            Transaction currTrans = allTransactions.get(i);
            date = currTrans.getTransactionMeetings().get(currTrans.getTransactionMeetings().size() - 1).getDate();
            Date setDate = java.sql.Date.valueOf(date);
            targetCal.setTime(setDate);
            targetWeek = targetCal.get(Calendar.WEEK_OF_YEAR);
            targetYear = targetCal.get(Calendar.YEAR);
            if(targetWeek == week && targetYear == year){
                numTransactions ++;
            }
            i++;
        }
        // main idea of this code from: https://stackoverflow.com/questions/10313797/how-to-check-a-day-is-in-the-current-week-in-java
        return numTransactions;
    }

    /**
     * Returns if the user has exceeded the weekly limit of transactions
     * @param user User
     * @return boolean
     */
    public boolean weeklyThresholdExceeded(User user) throws InvalidTransactionException {
        int threshold = user.getWeeklyThreshold();
        int numberWeeklyTransactions = numTransactionsInWeek(user);
        return numberWeeklyTransactions >= threshold;
    }

}
