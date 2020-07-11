package Presenters;

import java.util.ArrayList;
import java.util.List;

public class BootupMenuPresenter extends MenuPresenter {

    public String enterUsername() {
        return "Enter username:";
    }

    public String enterPassword() {
        return "Enter password:";
    }

    public String notValid() {
        return "Not a valid option.";
    } // i dont think you need this anymore now that we use HandleOptions in the selectOption method

    public String selectOption() {
        List<String> bootupOptions = new ArrayList<>();
        bootupOptions.add("Login to existing account");
        bootupOptions.add("Create a new account");
        return handleOptions(bootupOptions, false, "Bootup Menu");
    }

    public String invalidCredentials() {
        return "Incorrect username or password.";
    }

    @Override
    public List<String> constructMainMenu() {
        return null;
    }
}
