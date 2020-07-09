import java.io.*;
import java.util.HashMap;
import java.util.List;
import java.util.UUID;

// general ideas taken from lecture 6 StudentManager.java example
public class Serializer {

    public void writeUsersToFile(String path, List<User> users) throws IOException {

        // create a connection to the file specified by path
        OutputStream file = new FileOutputStream(path);
        ObjectOutput output = new ObjectOutputStream(file);

        // write user into the file
        output.writeObject(users);

        // close the file
        output.close();
    }

    public List<User> readUsersFromFile(String path) throws IOException, ClassNotFoundException {
        // create a connection to the file specified by path
        InputStream file = new FileInputStream(path);
        ObjectInput input = new ObjectInputStream(file);

        // deserialize the list
        List<User> users = (List<User>) input.readObject();

        // close the file
        input.close();

        // return the list
        return users;
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

    public void writeTransactionsToFile(String path, HashMap<UUID, Transaction> transactionMap) throws IOException {

        // create a connection to the file specified by path
        OutputStream file = new FileOutputStream(path);
        ObjectOutput output = new ObjectOutputStream(file);

        // write user into the file
        output.writeObject(transactionMap);

        // close the file
        output.close();
    }

    public HashMap<UUID, Transaction> readTransactionMapFromFile(String path) throws IOException, ClassNotFoundException {
        // create a connection to the file specified by path
        InputStream file = new FileInputStream(path);
        ObjectInput input = new ObjectInputStream(file);

        // deserialize the list
        HashMap<UUID, Transaction> transactionMap = (HashMap<UUID, Transaction>) input.readObject();

        // close the file
        input.close();

        // return the list
        return transactionMap;
    }

    public void writeItemsToFile(String path, HashMap<Item, User> pendingItems) throws IOException {
        OutputStream file = new FileOutputStream(path);
        ObjectOutput output = new ObjectOutputStream(file);
        output.writeObject(pendingItems);
        output.close();
    }

    public HashMap<Item, User> readItemsFromFile(String path) throws IOException, ClassNotFoundException {
        InputStream file = new FileInputStream(path);
        ObjectInput input = new ObjectInputStream(file);
        HashMap<Item, User> pendingItems = (HashMap<Item, User>) input.readObject();
        input.close();
        return pendingItems;
    }

    public void writeAccountsToFile(String path, List<User> Accounts) throws IOException {
        OutputStream file = new FileOutputStream(path);
        ObjectOutput output = new ObjectOutputStream(file);
        output.writeObject(Accounts);
        output.close();
    }

    public List<User> readAccountsFromFile(String path) throws IOException, ClassNotFoundException {
        InputStream file = new FileInputStream(path);
        ObjectInput input = new ObjectInputStream(file);
        List<User> Accounts = (List<User>) input.readObject();
        input.close();
        return Accounts;
    }

}