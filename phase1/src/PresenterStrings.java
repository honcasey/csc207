import java.util.List;

public interface PresenterStrings {

    void mainMenu();

    void empty(String which);

    void enterName(String name);

    void successfullyAdded(String what, String who, String where);

    void successfullyChanged(String what, String who);

    void validOptions(List<String> optionList);

    void whichThreshold(String whichThreshold);

    void usernameTaken();

    void currentThreshold(String description, int threshold);
}
