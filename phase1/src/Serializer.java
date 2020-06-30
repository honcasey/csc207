import java.io.*;
import java.util.HashMap;
import java.util.List;

// general ideas taken from lecture 6 StudentManager.java example
public class Serializer {

    // cited from https://docs.oracle.com/javase/7/docs/api/java/io/ObjectOutputStream.html

    public void writeUsersToFile(String path, List<User> users) throws IOException {

        // create a connection to the file specified by path
        OutputStream file = new FileOutputStream(path);
        ObjectOutput output = new ObjectOutputStream(file);

        // write user into the file
        output.writeObject(users);

        // close the file
        output.close();
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

    // cited from here https://docs.oracle.com/javase/7/docs/api/java/io/ObjectInputStream.html

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

    public void writeFlaggedAccountsToFile(String path, List<User> frozenAccounts) throws IOException {
        OutputStream file = new FileOutputStream(path);
        ObjectOutput output = new ObjectOutputStream(file);
        output.writeObject(frozenAccounts);
        output.close();
    }

    public List<User> readFlaggedAccountsFromFile(String path) throws IOException, ClassNotFoundException {
        InputStream file = new FileInputStream(path);
        ObjectInput input = new ObjectInputStream(file);
        List<User> flaggedAccounts = (List<User>) input.readObject();
        input.close();
        return flaggedAccounts;
    }
}