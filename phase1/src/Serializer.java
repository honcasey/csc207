import java.io.*;
import java.util.List;

// general ideas taken from lecture 6 StudentManager.java example
public class Serializer {
    private List<User> users;
    private List<AdminUser> admins;

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
}
