import Admins.AdminUser;
import Items.Item;
import Transactions.Transaction;
import Users.TradingUser;

import java.io.*;
import java.util.List;
import java.util.Map;
import java.util.UUID;

/**
 * <h1>Serializer</h1>
 * This class is responsible for the serialization and deserialization of collections of objects.
 *
 * <p>General ideas were taken from week 6 StudentManager.java example in ReadWriteEx</p>
 */
public class Serializer {

    /**
     * Writes a List of TradingUser into a file specified by a filepath.
     * @param path The filepath corresponding to the file it is written to.
     * @param tradingUsers A List of TradingUser that is being written.
     * @throws IOException
     */
    public void writeUsersToFile(String path, List<TradingUser> tradingUsers) throws IOException {

        // create a connection to the file specified by path
        OutputStream file = new FileOutputStream(path);
        ObjectOutput output = new ObjectOutputStream(file);

        // write user into the file
        output.writeObject(tradingUsers);

        // close the file
        output.close();
    }

    /**
     * Reads a List of TradingUser from a file specified by a filepath.
     * @param path The filepath corresponding to the file it is being read from.
     * @return A List of TradingUser.
     * @throws IOException
     * @throws ClassNotFoundException
     */
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

    /**
     * Writes a List of AdminUser into a file specified by a filepath.
     * @param path The filepath corresponding to the file it is written to.
     * @param admins A List of AdminUser that is being written.
     * @throws IOException
     */
    public void writeAdminsToFile(String path, List<AdminUser> admins) throws IOException {
        OutputStream file = new FileOutputStream(path);
        ObjectOutput output = new ObjectOutputStream(file);
        output.writeObject(admins);
        output.close();
    }

    /**
     * Reads a List of AdminUser from a file specified by a filepath.
     * @param path The filepath corresponding to the file it is being read from.
     * @return A List of AdminUser.
     * @throws IOException
     * @throws ClassNotFoundException
     */
    public List<AdminUser> readAdminsFromFile(String path) throws IOException, ClassNotFoundException {
        InputStream file = new FileInputStream(path);
        ObjectInput input = new ObjectInputStream(file);
        List<AdminUser> admins = (List<AdminUser>) input.readObject();
        input.close();
        return admins;
    }

    /**
     * Writes a Map of UUID to Transaction into a file specified by a filepath.
     * @param path The filepath corresponding to the file it is written to.
     * @param transactionMap A Map of UUID to Transaction.
     * @throws IOException
     */
    public void writeTransactionsToFile(String path, Map<UUID, Transaction> transactionMap) throws IOException {
        OutputStream file = new FileOutputStream(path);
        ObjectOutput output = new ObjectOutputStream(file);
        output.writeObject(transactionMap);
        output.close();
    }

    /**
     * Reads a Map of UUID to Transaction from a file specified by a filepath.
     * @param path The filepath corresponding to the file it is being read from.
     * @return A Map of UUID to Transaction.
     * @throws IOException
     * @throws ClassNotFoundException
     */
    public Map<UUID, Transaction> readTransactionMapFromFile(String path) throws IOException, ClassNotFoundException {
        InputStream file = new FileInputStream(path);
        ObjectInput input = new ObjectInputStream(file);
        Map<UUID, Transaction> transactionMap = (Map<UUID, Transaction>) input.readObject();
        input.close();
        return transactionMap;
    }

    /**
     * Writes a Map of Item to TradingUser into a file specified by a filepath.
     * @param path The filepath corresponding to the file it is written to.
     * @param pendingItems A Map of Item to TradingUser.
     * @throws IOException
     */
    public void writeItemsToFile(String path, Map<Item, TradingUser> pendingItems) throws IOException {
        OutputStream file = new FileOutputStream(path);
        ObjectOutput output = new ObjectOutputStream(file);
        output.writeObject(pendingItems);
        output.close();
    }

    /**
     * Reads a Map of Item to TradingUser from a file specified by a filepath.
     * @param path The filepath corresponding to the file it is being read from.
     * @return A Map of Item to TradingUser.
     * @throws IOException
     * @throws ClassNotFoundException
     */
    public Map<Item, TradingUser> readItemsFromFile(String path) throws IOException, ClassNotFoundException {
        InputStream file = new FileInputStream(path);
        ObjectInput input = new ObjectInputStream(file);
        Map<Item, TradingUser> pendingItems = (Map<Item, TradingUser>) input.readObject();
        input.close();
        return pendingItems;
    }

    /**
     * Writes a List of TradingUser into a file specified by a filepath.
     * @param path The filepath corresponding to the file it is written to.
     * @param Accounts A List of TradingUser.
     * @throws IOException
     */
    public void writeAccountsToFile(String path, List<TradingUser> Accounts) throws IOException {
        OutputStream file = new FileOutputStream(path);
        ObjectOutput output = new ObjectOutputStream(file);
        output.writeObject(Accounts);
        output.close();
    }

    /**
     * Reads a List of TradingUser from a file specified by a filepath.
     * @param path The filepath corresponding to the file it is being read from.
     * @return A List of TradingUser
     * @throws IOException
     * @throws ClassNotFoundException
     */
    public List<TradingUser> readAccountsFromFile(String path) throws IOException, ClassNotFoundException {
        InputStream file = new FileInputStream(path);
        ObjectInput input = new ObjectInputStream(file);
        List<TradingUser> Accounts = (List<TradingUser>) input.readObject();
        input.close();
        return Accounts;
    }

    /**
     * Writes a Map of UUID to Item into a file specified by a filepath.
     * @param path The filepath corresponding to the file it is written to.
     * @param itemMap A Map of UUID to Item.
     * @throws IOException
     */
    public void writeItemsMapToFile(String path, Map<UUID, Item> itemMap) throws IOException {
        OutputStream file = new FileOutputStream(path);
        ObjectOutput output = new ObjectOutputStream(file);
        output.writeObject(itemMap);
        output.close();
    }

    /**
     * Reads a Map of UUID to Item into a file specified by a filepath.
     * @param path The filepath corresponding to the file it is being read from.
     * @return A Map of UUID to Item.
     * @throws IOException
     * @throws ClassNotFoundException
     */
    public Map<UUID, Item> readItemMapFromFile(String path) throws IOException, ClassNotFoundException {
        InputStream file = new FileInputStream(path);
        ObjectInput input = new ObjectInputStream(file);
        Map<UUID, Item> itemMap = (Map<UUID, Item>) input.readObject();
        input.close();
        return itemMap;
    }
}