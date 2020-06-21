import java.io.*;
import java.util.ArrayList;
import java.util.List;

// general ideas taken from lecture 6 StudentManager.java example
public class UserSerializer {
    List<User> users;

    // cited from https://docs.oracle.com/javase/7/docs/api/java/io/ObjectOutputStream.html

    public void writeToFile(String path) throws IOException {

        // create a connection to the file specified by path
        OutputStream file = new FileOutputStream(path);
        ObjectOutput output = new ObjectOutputStream(file);

        // write user into the file
        output.writeObject(users);

        // close the file
        output.close();
    }

    // cited from here https://docs.oracle.com/javase/7/docs/api/java/io/ObjectInputStream.html

    public void readFromFile(String path) {
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

    public List<User> getUsers() {
        return users;
    }
}
