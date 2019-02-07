package operations; 
import java.util.Scanner;

public class Menu {

    private Scanner scanner = new Scanner(System.in);


    /**
     * It is used for displaying the menu
     */
    public Start.Command promptMainMenu() {
        return getCommand(
                "=============================================\n" +
                "Start Management System\n" +
                "=============================================" +
                "\n[ 1] Add Holding" +
                "\n[ 2] Remove Holding" +
                "\n[ 3] Add Member" +
                "\n[ 4] Remove Member" +
                "\n[ 5] Borrow Holding" +
                "\n[ 6] Return Holding" +
                "\n[ 7] Print All Holdings" +
                "\n[ 8] Print All Members" +
                "\n[ 9] Print Specific Holding" +
                "\n[10] Print Specific Member" +
                "\n[11] Save To File" +
                "\n[12] Load From File" +
                "\n[ 0] Exit" +
                "\n---------------------------------------------" +
                "\nEnter an option: ");
    }

    /**
     * Returns the selected menu number by the administrator
     */
    private Start.Command getCommand(String text) {
        System.out.print(text);
        int input = scanner.nextInt();
        scanner.nextLine();
        return Start.Command.values()[input];
    }

    /**
     * This function will get a string value from the user
     */
    protected String getString(String text) {
        System.out.print(text);
        return scanner.nextLine();
    }

    /**
     * This function will get an int value from the administrator
     */
    protected int getInt(String text) {
        System.out.print(text);
        return Integer.parseInt(scanner.nextLine());
    }


}
