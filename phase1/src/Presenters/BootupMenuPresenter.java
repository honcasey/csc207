package Presenters;

import java.util.ArrayList;
import java.util.List;

/**
 * <h1>BootupMenuPresenter</h1>
 * Contains all option strings and prompts printed/displayed by Initialization.TradingSystem.
 * Class variables represent commonly used strings.
 */
public class BootupMenuPresenter extends MenuPresenter {

    private final String usernamePrompt = "Enter username:";
    private final String passwordPrompt = "Enter password:";
    private final String takenUsername = "Username is already taken, please try again.";
    private final String invalidCredentials = "Username or Password is wrong, please try again.";

    public String getUsernamePrompt() {
        return usernamePrompt;
    }

    public String getPasswordPrompt() {
        return passwordPrompt;
    }

    public String getTakenUsername() {
        return takenUsername;
    }

    public String getInvalidCredentials() {
        return invalidCredentials;
    }

    public int selectOption() {
        List<String> bootupOptions = new ArrayList<>();
        bootupOptions.add("Login to existing account");
        bootupOptions.add("Create a new account");
        bootupOptions.add("Exit program");
        return handleOptionsByIndex(bootupOptions, false, "Bootup Menu");
    }

    @Override
    public List<String> constructMainMenu() {
        return null;
    }
}
