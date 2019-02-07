package operations;

import helper.DateTime;
import org.omg.CORBA.ORBPackage.InconsistentTypeCode;
import org.omg.CORBA.TIMEOUT;

import java.security.Timestamp;
import java.util.Scanner;

/**
 * The main class
 */
public class Start {

    private static Scanner scanner = new Scanner(System.in);

    // Different menus are defined in enumeration
    public enum Command {
        exit, a,b,c,d,e,f,g,h,i,j, save, load;
        public static final Command values[] = values();
    };

    // get a menu object to display the memu
    private static Menu menu = new Menu();

    //get a library object to do the functions
    private static Library admin = new Library();

    // the main class
    public static void main(String args[]) {
        start();
//        DateTime start = new DateTime();
//        DateTime end = new DateTime(1,1,1970);
//
////        System.out.println(DateTime.diffDays(end, start));
//        System.out.println(start.getTime());
    }

    /**
     * The method to tell which method should be called for the operation
     */
    public static void start() {
        Command command;
        do {
            command = menu.promptMainMenu();
            switch (command) {
                case a:
                    admin.addHolding();
                    break;
                case b:
                    admin.removeHolding();
                    break;
                case c:
                    admin.addMember();
                    break;
                case d:
                    admin.removeMember();
                    break;
                case e:
                    admin.borrowHolding();
                    break;
                case f:
                    admin.returnHolding();
                    break;
                case g:
                    admin.printHoldings();
                    break;
                case h:
                    admin.printMembers();
                    break;
                case i:
                    admin.printAHolding();
                    break;
                case j:
                    admin.printAMember();
                    break;
                case save:
                    admin.save2file();
                    break;
                case load:
                    admin.loadFile();
                    break;
                default:
                    System.out.println("*=>Error: Invalid Input, please have a check and enter again.");
            }
        } while (command != Command.exit);
        admin.exit();
    }

}