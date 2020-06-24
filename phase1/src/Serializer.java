import java.io.*;
import java.util.HashMap;
import java.util.List;

// general ideas taken from lecture 6 StudentManager.java example
public class Serializer {
    private List<User> users;
    private List<AdminUser> admins;
    private HashMap<Item, User> pendingItems;

    // cited from https://docs.oracle.com/javase/7/docs/api/java/io/ObjectOutputStream.html

    public void writeUsersToFile(String path) throws IOException {

        // create a connection to the file specified by path
        OutputStream file = new FileOutputStream(path);
        ObjectOutput output = new ObjectOutputStream(file);

        // write user into the file
        output.writeObject(users);

        // close the file
        output.close();
    }

    public void writeAdminsToFile(String path) throws IOException {

        // create a connection to the file specified by path
        OutputStream file = new FileOutputStream(path);
        ObjectOutput output = new ObjectOutputStream(file);

        // write user into the file
        output.writeObject(admins);

        // close the file
        output.close();
    }

    // cited from here https://docs.oracle.com/javase/7/docs/api/java/io/ObjectInputStream.html

    public void readUsersFromFile(String path) {
        try {
            // create a connection to the file specified by path
            InputStream file = new FileInputStream(path);
            ObjectInput input = new ObjectInputStream(file);

            // deserialize the list
            users = (List<User>) input.readObject();

            // close the file
            input.close();
        } catch (IOException | ClassNotFoundException e) {
            // TODO idk what we should return if an error is caught
        }

    }

    public void readAdminsFromFile(String path) {
        try {
            // create a connection to the file specified by path
            InputStream file = new FileInputStream(path);
            ObjectInput input = new ObjectInputStream(file);

            // deserialize the list
            admins = (List<AdminUser>) input.readObject();

            // close the file
            input.close();
        } catch (IOException | ClassNotFoundException e) {
            // TODO idk what we should return if an error is caught
        }

    }

    public void writePendingItems(String path) throws IOException {
        OutputStream file = new FileOutputStream(path);
        ObjectOutput output = new ObjectOutputStream(file);

        output.writeObject(pendingItems);
        output.close();
    }

    public void readPendingItems(String path){
        try {
            InputStream file = new FileInputStream(path);
            ObjectInput input = new ObjectInputStream(file);
            pendingItems = (HashMap<Item, User>) input.readObject();
            input.close();
        } catch (IOException | ClassNotFoundException e) {
            //TODO do something
        }
    }

    public void setUsers(List<User> users) {
        this.users = users;
    }

    public List<User> getUsers() {
        return users;
    }

    public void setAdmins(List<AdminUser> admins) {
        this.admins = admins;
    }

    public List<AdminUser> getAdmins() {
        return admins;
    }

    public void setItems(HashMap<Item, User> pendingItems) {
        this.pendingItems = pendingItems;
    }

    public HashMap<Item, User> getPendingItems() {
        return pendingItems;
    }
}
