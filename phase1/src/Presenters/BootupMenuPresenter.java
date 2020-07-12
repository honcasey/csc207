package Presenters;

import java.util.ArrayList;
import java.util.List;

public class BootupMenuPresenter extends MenuPresenter {

    private String usernamePrompt = "Enter username:";
    private String passwordPrompt = "Enter password:";
    private String takenUsername = "Username is already taken, please try again.";

    public String getUsernamePrompt() {
        return usernamePrompt;
    }

    public String getPasswordPrompt() {
        return passwordPrompt;
    }

    public String getTakenUsername() {
        return takenUsername;
    }


    public int selectOption() {
        List<String> bootupOptions = new ArrayList<>();
        bootupOptions.add("Login to existing account");
        bootupOptions.add("Create a new account");
        return handleOptionsByIndex(bootupOptions, false, "Bootup Menu");
    }


    @Override
    public List<String> constructMainMenu() {
        return null;
    }
}
