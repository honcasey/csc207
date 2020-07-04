import java.util.List;

public interface PresenterStrings {

    public void empty(String which);

    public void enterName(String name);

    public void successfullyAdded(String what, String who, String where);

    public void successfullyChanged(String what, String who);

    public void validOptions(List<String> optionList);

    public void whichThreshold(String whichThreshold);

    public void usernameTaken();

    public void currentThreshold(String description, int threshold);
}
