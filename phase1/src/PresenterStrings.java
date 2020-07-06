import java.util.List;

public interface PresenterStrings {

    String mainMenu();

    String empty(String which);

    String enterName(String name);

    String successfullyAdded(String what, String who, String where);

    String successfullyChanged(String what, String who);

    String validOptions(List<String> optionList);

    String whichThreshold(String whichThreshold);

    String usernameTaken();

    String currentThreshold(String description, int threshold);

    String freeze(String who, String frozen);

    String logout();


}
