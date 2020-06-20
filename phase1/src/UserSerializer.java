import java.io.*;


public class UserSerializer {

    public void writeToFile(String path, User user) throws IOException {

        // create a connection to the file specified by path
        OutputStream file = new FileOutputStream(path);
        ObjectOutput output = new ObjectOutputStream(file);

        // write user into the file
        output.writeObject(user);

        // close the file
        output.close();
    }

    public void readFromFile(String path) {}
}
