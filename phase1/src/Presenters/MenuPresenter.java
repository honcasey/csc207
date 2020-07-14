package Presenters;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.List;
import java.util.Scanner;

/**
 * Stores all common strings between the menu accessed by an AdminUser, and the menu accessed by a TradingUser.
 */
public abstract class MenuPresenter {

    public String borrowThresholdDescription = "minimum number of times that this user must lend something before they can borrow/trade";
    public String weeklyThresholdDescription = "maximum number of transactions that this user can participate in a week";
    public String incompleteThresholdDescription = "maximum number of incomplete transactions before this user's account is frozen";

    public String previousMenu = "Loading Previous Menu";
    public String optionPrompt = "Please type a number corresponding to one of the above options.";
    public String invalidOption = "Not a valid option. Please enter a valid option.";
    public String logout = "Log out";
    public String itemDescription = "What is the description of this item?";
    public String successfulLogout = "You have successfully logged out.";
    public String usernameInvalid = "Username does not exist.";
    public String usernameTaken = "Username already taken. Please enter a different one.";
    public String nextItem = "Go to next item.";

    /**
     * Formats and displays a list of options to the user.
     * @param OptionList the list of options that you want to be displayed.
     */
    public void displayOptions(List<String> OptionList){
        for(int i = 0; i < OptionList.size(); i++){
            String index = Integer.toString(i+1);
            String OutputLine =  index + ". " + OptionList.get(i);
            System.out.println(OutputLine);
        }
        System.out.println(optionPrompt);
    }

    /**
     * Constructs the option list that the user will be greeted with upon first logging into the program.
     * @return list of options that the user can choose from.
     */
    protected abstract List<String> constructMainMenu();

    /**
     * This method takes in a list of options and handles option display and selection.(generic)
     * @param OptionList the list of options you want displayed.
     * @param BackOption boolean representing if you want a back option appended to the option list and displayed.
     * @param OptionTitle the title of the menu.
     * @return this returns the option that was selected by the user as a string.
     */
    public String handleOptions(List<String> OptionList, boolean BackOption, String OptionTitle) {
        if (BackOption) {
            OptionList.add("Go back.");
        }
        System.out.println(OptionTitle);
        this.displayOptions(OptionList);
        return(this.selectOption(OptionList));
    }

    /**
     * Safely getting input from user inspired by:
     * https://stackoverflow.com/questions/13215639/asking-user-for-another-prompt-after-wrong-input-in-java
     * Answer by Mordechai
     * @param OptionList list of options
     * @return String of selected option
     */
    private String selectOption(List<String> OptionList){
        Scanner scanner = new Scanner(System.in);
        int OptionChosen;
        do {
            while (!scanner.hasNextInt()) {
                System.out.println(invalidOption);
                scanner.next();
            }
            OptionChosen = scanner.nextInt();
        } while (OptionChosen > OptionList.size() || OptionChosen <= 0);
        return(OptionList.get(OptionChosen-1));
    }

    private int GetUserInt(String ErrorMsg){ // TO-DO: delete?
        Scanner scanner = new Scanner(System.in);
        int UserInt;
        do {
            while (!scanner.hasNextInt()) {
                System.out.println(invalidOption);
                scanner.next();
            }
            UserInt = scanner.nextInt();
        } while (UserInt < 0);
        return(UserInt);
    }

    /**
     * Handles options by displaying the input option list as a numbered list, with the choice of adding a "Go Back"
     * option at the end of the list.
     * @param OptionList the list of options you want displayed.
     * @param BackOption boolean representing if you want a back option appended to the option list and displayed.
     * @param OptionTitle the title of the option's page.
     * @return returns the index of the option chosen by the user corresponding the option list that was passed in.
     *          So that optionlist.get(return value) gives the option that the user has chosen.
     */
    public int handleOptionsByIndex(List<String> OptionList, boolean BackOption, String OptionTitle) {
        if (BackOption) {
            OptionList.add("Go back.");
        }
        System.out.println(OptionTitle);
        this.displayOptions(OptionList);
        return(this.selectOptionByIndex(OptionList));
    }

    /**
     * Checks to see if the option in an optionList matches the inputted menu option chosen by a User.
     * @param input the number the User inputted
     * @param optionList the list that the User chose from
     * @param option the name of option comparing the index of the option list
     * @return boolean that returns if the input option number matches the string option given
     */
    public boolean indexToOption(int input, List<String> optionList, String option){
        return optionList.get(input).equals(option);
    }

    private int selectOptionByIndex(List<String> OptionList){
        Scanner scanner = new Scanner(System.in);
        int OptionChosen;
        do {
            while (!scanner.hasNextInt()) {
                System.out.println(invalidOption);
                scanner.next();
            }
            OptionChosen = scanner.nextInt();
        } while (OptionChosen > OptionList.size() || OptionChosen <= 0);
        return(OptionChosen -1);
    }

    /**
     * Handles asking a yes/no question to the user and taking their answer as "Y" or "N"
     * @param Question The yes or no question you would like to ask the user (as a string.)
     * @return return true iff the user has entered "Yes" to your question and returns false iff
     * the user has entered "No" to your question.
     */
    public boolean handleYesNo(String Question, String yesString, String noString){
        Scanner scanner = new Scanner(System.in);
        String UserInput;
        System.out.println(Question);
        System.out.println("Please Enter " + "'" + yesString + "'" + " or " + "'"+noString+"'.");
        do {
            while (!scanner.hasNext()) {
                System.out.println(invalidOption);
                scanner.next();
            }
            UserInput = scanner.next();
        } while (!(UserInput.equals(yesString) || UserInput.equals(noString)));
        return UserInput.equals(yesString);
    }

    /**
     * Prompts the user for the time in specific format.
     * Checks the date string that the user has inputted to see if it is in the accepted format Then returns the time
     * @param optionTimePrompt the question to be displayed prompting time input by the user
     * @return true iff the prompt is in the accepted format dd/mm/yyyy.
     */
    public LocalTime inputTimeGetter(String optionTimePrompt){
        Scanner scanner = new Scanner(System.in);
        LocalTime returnTime;
        while (true) {
            try {
                System.out.println(optionTimePrompt);
                String DateString = scanner.nextLine();
                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm:ss");
                returnTime = LocalTime.parse(DateString, formatter);
                break;
            }
            catch(DateTimeParseException e){
                System.out.println("Invalid time please,try again.");

            }
        }
        return(returnTime);
    }

    /**
     * Prompts the user for information to construct a date object with specific format.
     * @param optionDatePrompt the question to be displayed prompting date input by the user.
     * @return a date object that was constructed by the user.
     */
    public LocalDate inputDateGetter(String optionDatePrompt){
        Scanner scanner = new Scanner(System.in);
        LocalDate returnDate;
        while (true) {
            try {
                System.out.println(optionDatePrompt);
                String DateString = scanner.nextLine();
                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-uuuu");
                returnDate = LocalDate.parse(DateString, formatter);
                break;
            }
            catch(DateTimeParseException e){
                System.out.println("Invalid Date please,try again.");

            }
        }
        return(returnDate);
    }

    public String empty(String which) { return which + " list is empty. Nothing to be checked."; }

    public String enterName(String name) { return "Please enter name for this " + name; }

    public String enterPassword(String name) { return "Please enter password for this " + name; }

    public String successfullyAdded(String what, String who, String where) {
        return what + "has been successfully added to " + who + "'s " + where;
    }
    public String successfullyRemoved(String what, String where){
        return what + "has been successfully removed from" + where;
    }

    public String successfullyChanged(String what, String who) {
        return who + "'s " + what + " has been successfully changed.";
    }

    public String successfullyCreated(String what) {
        return what + " has been successfully created.";
    }

    public String validOptions(List<String> optionList) {
        return "Valid options include: " + optionList.toString();
    }

    public String accountFrozen(String who, String frozen) { // possibly move back to adminmenupresenter
        return who + "'s account is set to " + frozen;
    }
}