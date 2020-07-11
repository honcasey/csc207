import Admins.AdminUser;
import Items.Item;
import Transactions.Transaction;
import Users.TradingUser;

import java.io.*;
import java.util.List;
import java.util.Map;
import java.util.UUID;

// general ideas taken from lecture 6 StudentManager.java example
public class Serializer {

    public void writeUsersToFile(String path, List<TradingUser> tradingUsers) throws IOException {

        // create a connection to the file specified by path
        OutputStream file = new FileOutputStream(path);
        ObjectOutput output = new ObjectOutputStream(file);

        // write user into the file
        output.writeObject(tradingUsers);

        // close the file
        output.close();
    }

    public List<TradingUser> readUsersFromFile(String path) throws IOException, ClassNotFoundException {
        // create a connection to the file specified by path
        InputStream file = new FileInputStream(path);
        ObjectInput input = new ObjectInputStream(file);

        // deserialize the list
        List<TradingUser> tradingUsers = (List<TradingUser>) input.readObject();

        // close the file
        input.close();

        // return the list
        return tradingUsers;
    }

    public void writeAdminsToFile(String path, List<AdminUser> admins) throws IOException {

        // create a connection to the file specified by path
        OutputStream file = new FileOutputStream(path);
        ObjectOutput output = new ObjectOutputStream(file);

        // write user into the file
        output.writeObject(admins);

        // close the file
        output.close();
    }

    public List<AdminUser> readAdminsFromFile(String path) throws IOException, ClassNotFoundException {
        // create a connection to the file specified by path
        InputStream file = new FileInputStream(path);
        ObjectInput input = new ObjectInputStream(file);

        // deserialize the list
        List<AdminUser> admins = (List<AdminUser>) input.readObject();

        // close the file
        input.close();

        // return the list
        return admins;
    }

    public void writeTransactionsToFile(String path, Map<UUID, Transaction> transactionMap) throws IOException {
        OutputStream file = new FileOutputStream(path);
        ObjectOutput output = new ObjectOutputStream(file);
        output.writeObject(transactionMap);
        output.close();
    }

    public Map<UUID, Transaction> readTransactionMapFromFile(String path) throws IOException, ClassNotFoundException {
        InputStream file = new FileInputStream(path);
        ObjectInput input = new ObjectInputStream(file);
        Map<UUID, Transaction> transactionMap = (Map<UUID, Transaction>) input.readObject();
        input.close();
        return transactionMap;
    }

    public void writeItemsToFile(String path, Map<Item, TradingUser> pendingItems) throws IOException {
        OutputStream file = new FileOutputStream(path);
        ObjectOutput output = new ObjectOutputStream(file);
        output.writeObject(pendingItems);
        output.close();
    }

    public Map<Item, TradingUser> readItemsFromFile(String path) throws IOException, ClassNotFoundException {
        InputStream file = new FileInputStream(path);
        ObjectInput input = new ObjectInputStream(file);
        Map<Item, TradingUser> pendingItems = (Map<Item, TradingUser>) input.readObject();
        input.close();
        return pendingItems;
    }

    public void writeAccountsToFile(String path, List<TradingUser> Accounts) throws IOException {
        OutputStream file = new FileOutputStream(path);
        ObjectOutput output = new ObjectOutputStream(file);
        output.writeObject(Accounts);
        output.close();
    }

    public List<TradingUser> readAccountsFromFile(String path) throws IOException, ClassNotFoundException {
        InputStream file = new FileInputStream(path);
        ObjectInput input = new ObjectInputStream(file);
        List<TradingUser> Accounts = (List<TradingUser>) input.readObject();
        input.close();
        return Accounts;
    }

    public void writeItemsMapToFile(String path, Map<UUID, Item> itemMap) throws IOException {
        OutputStream file = new FileOutputStream(path);
        ObjectOutput output = new ObjectOutputStream(file);
        output.writeObject(itemMap);
        output.close();
    }

    public Map<UUID, Item> readItemMapFromFile(String path) throws IOException, ClassNotFoundException {
        InputStream file = new FileInputStream(path);
        ObjectInput input = new ObjectInputStream(file);
        Map<UUID, Item> itemMap = (Map<UUID, Item>) input.readObject();
        input.close();
        return itemMap;
    }
}