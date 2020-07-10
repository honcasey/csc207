package Presenters;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

public class MenuPresenter {
    public List<String> allThresholds = Arrays.asList("borrow", "weekly", "incomplete");
    public List<String> userLists = Arrays.asList("wishlist", "inventory");
    private String optionPrompt = "Please type a number corresponding to one of the above options.";
    private String invalidOption = "Not a valid option. Please enter a valid option.";

    /**
     * Formats and displays a list of options to the user.
     * @param OptionList the list of options that you want to be displayed.
     */
    protected void displayOptions(List<String> OptionList){
        for(int i = 0; i < OptionList.size(); i++){
            String index = Integer.toString(i+1);
            String OutputLine =  index + ". " + OptionList.get(i);
            System.out.println(OutputLine);
        }
        System.out.println(optionPrompt);
    }

    /**
     * Adds Back option at the end of options being displayed to the user.
     * @param OptionList The list of options being displayed prior to calling this method.
     */
    protected void addBackOption(List<String> OptionList){
        String LastIndex = Integer.toString(OptionList.size() + 1);
        String LastOption = ". Go back";
        OptionList.add(LastIndex + LastOption);
    }

    /**
     * Construct methods like this return a list of options/prompts that the menu will have.
     * <p>
     * This particular method constructs the option list that the user will be greeted with upon first logging into
     * the program.
     *
     * @return this returns a list of options that the user can choose from.
     */
    public List<String> constructMainMenu() { return null; }

    /**
     * This method takes in a list of options and handles option display and selection.(generic)
     * @param OptionList the list of options you want displayed.
     * @param BackOption boolean representing if you want a back option appended to the option list and displayed.
     * @param OptionTitle the title of the menu.
     * @return this returns the option that was selected by the user as a string.
     */
    public String handleOptions(List<String> OptionList, boolean BackOption, String OptionTitle) {
        if (BackOption) {
            this.addBackOption(OptionList);
        }
        System.out.println(OptionTitle);
        this.displayOptions(OptionList);
        return(this.selectOption(OptionList));
    }

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

    private int GetUserInt(String ErrorMsg){
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
     * This method takes in a list of options and handles option display and selection. (generic)
     * @param OptionList the list of options you want displayed.
     * @param BackOption boolean representing if you want a back option appended to the option list and displayed.
     * @param OptionTitle the title of the option's page.
     * @return returns the index of the option chosen by the user corresponding the option list that was passed in.
     *          So that optionlist.get(return value) gives the option that the user has chosen.
     */
    public int handleOptionsByIndex(List<String> OptionList, boolean BackOption, String
            OptionTitle) {
        if (BackOption) {
            this.addBackOption(OptionList);
        }
        System.out.println(OptionTitle);
        this.displayOptions(OptionList);
        return(this.selectOptionByIndex(OptionList));
    }

    public boolean indexToOption(int input, List<String> strings, String s) {
        return strings.get(input).equals(s);
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
    /** inputTimeGetter
     * Checks the date string that the user has inputted to see if it is in the accepted format.
     * @return this returns tru iff Returns true iff it is
     *      in the accepted format dd/mm/yyyy.
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


    /** inputDateGetter
     * This method prompts the user for information to construct a date object, then
     * @param optionDatePrompt
     * @return
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

    public String enterName(String name) {
        return "Please enter name for this " + name;
    }

    public String enterPassword(String name) { return "Please enter password for this " + name; }

    public String successfullyAdded(String what, String who, String where) {
        return what + "has been successfully added to " + who + "'s " + where;
    }

    public String successfullyChanged(String what, String who) {
        return who + "'s " + what + "has been successfully changed.";
    }

    public String validOptions(List<String> optionList) {
        return "Valid options include: " + optionList.toString();
    }

    public String usernameTaken() { return "Username already taken. Please enter a different one."; }

    public String usernameInvalid() { return "Username does not exist."; }

    public String accountFrozen(String who, String frozen) { // possibly move back to adminmenupresenter
        return who + "'s account has been set to " + frozen;
    }

    public String logout() { return "You have successfully logged out."; }


}
