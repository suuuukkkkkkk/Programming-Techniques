package operations;

import helper.DateTime;
import helper.Validator;
import io.ReadFile;
import io.SaveFile;
import operations.exceptions.InsufficientCreditException;
import operations.exceptions.ItemInactiveException;
import operations.exceptions.OnLoanException;
import operations.holding.Book;
import operations.holding.Holding;
import operations.holding.Video;
import operations.member.Member;
import operations.member.PremiumMember;
import operations.member.StandardMember;

import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.Objects;
import java.util.Scanner;


public class Library implements SystemOperations {

    private static Scanner scanner;

    private static ReadFile reader;
    private static SaveFile saver;


    private static ArrayList<Holding> holdings; // used to read current holdings
    private static ArrayList<Member> members; // used to read current members

    // the constructor
    public Library() {
        // initialization
        scanner = new Scanner(System.in);
        reader = new ReadFile();
        saver = new SaveFile();
        holdings = new ArrayList<>(15);
        members = new ArrayList<>(15);

        // load from files
        loadFile();
//        Book book1 = new Book("b000001", "Intro to Java");
//        book1.setDefaultLoanFee(10);
//        book1.setMaxLoanPeriod(28);
//        holdings.add(book1);
//
//        Book book2 = new Book("b000002", "Learning UML");
//        book2.setDefaultLoanFee(10);
//        book2.setMaxLoanPeriod(28);
//        holdings.add(book2);
//
//        Book book3 = new Book("b000003", "Design Patterns");
//        book3.setDefaultLoanFee(10);
//        book3.setDefaultLoanFee(28);
//        holdings.add(book3);
//
//        Book book4 = new Book("b000004", "Advanced Java");
//        book4.setDefaultLoanFee(10);
//        book4.setMaxLoanPeriod(28);
//        holdings.add(book4);
//
//        Video video1 = new Video("v000001", "Java 1", 4);
//        video1.setMaxLoanPeriod(7);
//        holdings.add(video1);
//
//        Video video2 = new Video("v000002", "Java 2", 6);
//        video2.setMaxLoanPeriod(7);
//        holdings.add(video2);
//
//        Video video3 = new Video("v000003", "UML 1", 6);
//        video3.setMaxLoanPeriod(7);
//        holdings.add(video3);
//
//        Video video4 = new Video("v000004", "UML 2", 4);
//        video4.setMaxLoanPeriod(7);
//        holdings.add(video4);
//
//        StandardMember smem1 = new StandardMember("s0000001", "Joe Bloggs");
//        members.add(smem1);
//
//        StandardMember smem2 = new StandardMember("s0000002", "Jane Smith");
//        members.add(smem2);
//
//        PremiumMember pmem1 = new PremiumMember("p000001", "Fred Bloggs");
//        members.add(pmem1);
//
//        PremiumMember pmem2 = new PremiumMember("p000002", "Fred Smith");
//        members.add(pmem2);
    }

    /**
     * Used to add a new holding, it prompts to enter necessary information
     */
    public static void addHolding() {
        if ( !(holdings.contains(null) || holdings.contains("")) ){
            int type;
            String id;
            String title;
            int loanFee = 0;
            int maxLoanPeriod = 28;
            // show the submenu : book or video
            while (true) {
                System.out.println(
                        "Choose the type of the holding:\n" +
                        "[1] A Book\n" +
                        "[2] A Video\n" +
                        "-> The holding is a: "
                );

                try {
                    scanner = new Scanner(System.in);
                    type = scanner.nextInt();
                    break;
                } catch (InputMismatchException e){
                    System.out.printf("*=>Error: The input must be either 1 or 2!\n");
                }
            }
            // prompt for accepting ID
            while (true) {
                System.out.println("-> The Holding ID: ");
                scanner = new Scanner(System.in);
                id = scanner.next();
                if (Validator.validateId('h', id)) {
                    break;
                } else {
                    System.out.println("*=>Error: It's not a valid ID (E.g. b000001 or v000001).");
                }

            }
            // prompt for accepting title
            while (true) {
                System.out.println("-> The Holding Title: ");
                scanner = new Scanner(System.in);
                title = scanner.next();

                if (Validator.validateHoldingTitle(title)){
                    break;
                } else {
                    System.out.println("*=>Error: Not a valid Name! " +
                            "A valid name can only contain at least 1 letter.");
                }
            }

            // prompt for accepting default loan fee
            while (true) {
                System.out.println("-> Default Loan Fee: ");
                try {
                    scanner = new Scanner(System.in);
                    loanFee = scanner.nextInt();
                    break;
                } catch (InputMismatchException e){
                    System.out.printf("*=>Error: Default Loan Fee must be an integer!\n");
                }
            }

            // prompt for accepting max loan period
            while (true) {
                System.out.println("-> Max Loan Period: ");
                try {
                    scanner = new Scanner(System.in);
                    maxLoanPeriod = scanner.nextInt();
                    break;
                } catch (InputMismatchException e){
                    System.out.printf("*=>Error: Max loan period must be an integer!\n");
                }
            }

            // to initiate a new object depending on it's a book or a video
            if (type == 1){
                Book newBook = new Book(id, title);
                newBook.setDefaultLoanFee(loanFee);
                newBook.setMaxLoanPeriod(maxLoanPeriod);
                holdings.add(newBook);
            }else if (type == 2){
                Video newVideo = new Video(id, title, loanFee);
                newVideo.setMaxLoanPeriod(maxLoanPeriod);
                holdings.add(newVideo);
            }

            System.out.println(
                    ">>> Successfully added!\n" +
                            "             Id: " + id + "\n" +
                            "          title: " + title + "\n" +
                            "       Loan Fee: " + loanFee + "\n" +
                            "Max Loan Period: " + maxLoanPeriod
            );

        } else {
            System.out.println("*=>Error: It has reached maximum number!");
        }
    }

    /**
     * Remove a holding by entering its ID
     */
    public static void removeHolding() {
        while (true) {
            System.out.println("Please Enter Holding ID: ");
            String searchID = scanner.next();
            boolean hasFound = false;

            if (searchID.equals("e") || searchID.equals("exit")) {
                return;
            } else {
                for (Holding holding : holdings) {
                    if (holding.getID().equals(searchID)) {
                        holdings.remove(holding);
                        hasFound = true;
                        break;
                    }
                }

                if (hasFound) {
                    System.out.println(">>> The holding has been removed.");
                    break;
                } else {
                    System.out.println("*=> There is no such a holding. Please have a check.");
                }
            }
        }
    }

    /**
     * Used to add a new member, it prompts to enter necessary information
     */
    public static void addMember() {
        if ( !(members.contains(null) || members.contains("")) ){
            int type;
            String id;
            String name;

            // show the submenu : standard or premium
            while (true) {
                System.out.println(
                        "Choose the type of the member:\n" +
                                "[1] A Standard Member\n" +
                                "[2] A Premium Member\n" +
                                "-> The member is a: "
                );

                try {
                    type = scanner.nextInt();
                    break;
                } catch (InputMismatchException e){
                    System.out.printf("*=>Error: It must be either 1 or 2!\n");
                }
            }
            // prompt for accepting ID
            while (true) {
                System.out.println("-> The Member ID: ");
                id = scanner.next();
                if (Validator.validateId('m', id)) {
                    break;
                } else {
                    System.out.println("*=>Error: It's not a valid ID (E.g. s000001 or p000001).");
                }

            }
            // prompt for accepting title
            while (true) {
                System.out.println("-> Full Name: ");
                name = scanner.next();

                if (Validator.validateFullName(name)){
                    break;
                } else {
                    System.out.println("*=>Error: Not a valid Name! " +
                            "A valid name can only contain letters with at least 1 character.");
                }
            }

            // to initiate a new object depending on it's a book or a video
            if (type == 1){
                Member stdMem = new StandardMember(id, name);
                members.add(stdMem);
                System.out.println(
                        ">>> Successfully added!\n" +
                                "        Id: " + id + "\n" +
                                " Full Name: " + name + "\n" +
                                "Max Credit: " + 30
                );
            }else if (type == 2){
                Member preMem = new PremiumMember(id, name);
                members.add(preMem);
                System.out.println(
                        ">>> Successfully added!\n" +
                                "        Id: " + id + "\n" +
                                " Full Name: " + name + "\n" +
                                "Max Credit: " + 45
                );
            }

        } else {
            System.out.println("*=>Error: It has reached maximum number!");
        }
    }

    /**
     * Remove a member by entering its ID
     */
    public static void removeMember() {
        while (true) {
            System.out.println("Please Enter Member ID: ");
            String searchID = scanner.next();
            boolean hasFound = false;

            if (searchID.equals("e") || searchID.equals("exit")) {
                return;
            } else {
                for (Member member : members) {
                    if (member.getID().equals(searchID)) {
                        members.remove(member);
                        hasFound = true;
                        break;
                    }
                }

                if (hasFound) {
                    System.out.println(">>> The member has been removed.");
                    break;
                } else {
                    System.out.println("*=> There is no such a member. Please have a check.");
                }
            }
        }
    }

    /**
     * Borrow a book or video
     * 1. Enter the holding ID
     * 2. Enter the member ID (the member who wants to borrow it)
     */
    public static void borrowHolding() {
        String hid = "";
        String mid = "";

        Holding lent = null;
        Member borrower = null;
        boolean isAvailable = false;

        // validate holding
        while (true) {
            System.out.println("-> The holding ID to be lent: ");
            scanner = new Scanner(System.in);
            hid = scanner.next();

            // type e or exit to stop current
            if (hid.equals("e") || hid.equals("exit")){
                return;
            }

            // to validate whether it is a valid holding ID
            if(Validator.validateId('h', hid)){
                if (isIdInHoldings(hid, holdings)){ // check whether it is in the list
                    try {
                        for (Holding holding : holdings) {
                            if (holding.getID().equals(hid) && holding.getStatus()) {
                                lent = holding;
                                isAvailable = true;
                                break;
                            }
                        }
                    } catch (ItemInactiveException ie) {
                        System.out.printf("*=>Error: The holding is currently inactive!");
                    } catch (OnLoanException oe) {
                        System.out.println("*=>Error: The current holding is on loan!\n");
                    }

                    // stop borrow holding function
                    break;
                } else {
                    System.out.println("*=>Error: There is no such a record!\n");
                    return;
                }
            } else {
                System.out.println("*=>Error: Not a valid ID! Please have a check.\n");
            }
        }

        // if the holding can be borrowed, then make sure which person will borrow it
        if (isAvailable) {
            while (true) {
                System.out.println("-> The borrower ID: ");
                scanner = new Scanner(System.in);
                mid = scanner.next();

                if (mid.equals("e") || mid.equals("exit")) {
                    return;
                }

                if (Validator.validateId('m', mid)) {
                    if (isIdInMembers(mid, members)) {
                        for (Member member: members) {
                            if (member.getID().equals(mid)) {
                                if (member.getCredit() >= lent.getDefaultLoanFee()) {
                                    lent.setBorrowDate(new DateTime());
                                    member.setCredit(member.getCredit() - lent.getDefaultLoanFee());
                                    ArrayList<Holding> currentHoldings;

                                    if (member.getHoldings() == null)
                                        currentHoldings = new ArrayList<>();
                                    else
                                        currentHoldings = member.getHoldings();

                                    currentHoldings.add(lent);
                                    member.setHoldings(currentHoldings);
                                    System.out.println(">>> Successfully borrowed!\n");
                                    break;
                                } else {
                                    System.out.println("*=>Error: Not enough credit!\n");
                                    break;
                                }
                            }
                        }

                        break;
                    } else {
                        System.out.println("*=>Error: There is no such a member ID in the list!\n");
                    }
                }
            }
        }
    }

    /**
     * Return a book or video
     * 1. Enter the borrower's ID (who wants to return)
     * 2. Enter the holding ID
     */
    public static void returnHolding() {
        String mid = "";
        String hid = "";
        Holding borrowedHolding = null;
        ArrayList<Holding> onHand = null;
        Member borrower = null;
        boolean isMember = false;

        // to get the member type
        while (true) {
            System.out.printf("-> The borrower ID: ");
            mid = scanner.next();

            if (mid.equals("e") || mid.equals("exit")) {
                return;
            }

            if (Validator.validateId('m', mid)) {
                if (isIdInMembers(mid, members)) {
                    for (Member member : members) {
                        if (member.getID().equals(mid)) {
                            member.getHoldings(); // get current holdings on hold

                            // to test the type of the member
                            if (member instanceof PremiumMember)
                                borrower = (PremiumMember) member;
                            else
                                borrower = (StandardMember) member;

                            onHand = borrower.getHoldings();
                            isMember = true;
                            break;
                        }
                    }

                    break;
                } else {
                    System.out.println("*=>Error: There is no such a member!");
                }
            } else {
                System.out.println("*=>Error: Not a valid member ID");
            }
        }

        // the the holding which needs to be returned
        if (isMember) {
            while (true) {
                System.out.printf("-> The returning holding ID: ");
                hid = scanner.next();

                if (hid.equals("e") || hid.equals("exit")) {
                    return;
                }

                if (Validator.validateId('h', hid)) {
                    if (isIdInHoldings(hid, holdings)) {
                        for (Holding holding: holdings) {
                            if (holding.getID().equals(hid)) {
                                if (holding.getID().charAt(0) == 'b')
                                    borrowedHolding = (Book) holding;
                                else
                                    borrowedHolding = (Video) holding;

                                break;
                            }
                        }

                        break;
                    }
                } else {
                    System.out.printf("*=>Error: Not a valid holding ID.\n");
                }
            }
        }

        // to check whether it can be returned
        try {
            if (borrower.returnHolding(borrowedHolding, new DateTime())) {
                borrowedHolding.setReturnDate(new DateTime());

                if (borrowedHolding.getLateFee() != 0)
                    borrower.setCredit(borrower.getCredit() - borrowedHolding.getLateFee());

                for (Holding holding: onHand) {
                    if (holding.getID().equals(hid)) {
                        holding.setBorrowDate(null);
                        onHand.remove(holding);
                        break;
                    }
                }

                borrower.setHoldings(onHand);

                System.out.println(">>> Successfully returned the holding!");
            }
        } catch (InsufficientCreditException e) {
            e.printStackTrace();
            System.out.println("Do you want to reset credit? (y/n)");
            String reset = scanner.next();

            if (reset.equals("y")) {
                borrower.resetCredit();
            }
        }

    }

    /**
     * Print all current holdings in the list
     */
    public static void printHoldings() {
        for (Holding holding : holdings) {
//            printHoldingRecord(holding);
            holding.print();
        }
    }

    /**
     * Print all members in the list
     */
    public static void printMembers() {
        for (Member member : members) {
//            printMemberRecord(member);
            member.print();
        }
    }

    /**
     * Search a holding by its ID
     */
    public static void printAHolding() {
        while (true) {
            System.out.println(">> Search by holding ID: ");
            String hid = scanner.next();
            if (Validator.validateId('h', hid)) {
                if (isIdInHoldings(hid, holdings)) {
                    for (Holding holding: holdings){
                        if (holding.getID().equals(hid)){
//                            printHoldingRecord(holding);
                            holding.print();
                            break;
                        }
                    }
                    break;
                } else {
                    System.out.println("*=>Error: The holding ID is not in current list! Please try again.");
                }
            } else if (hid.equals('e') || hid.equals("exit")){
                break;
            } else {
                System.out.printf("*=>Error: It is not a valid holding ID! " +
                        "Please have a check and try again.");
            }
        }
    }

    /**
     * Search a member by ID
     */
    public static void printAMember() {
        while (true) {
            System.out.println(">> Search by member ID: ");
            String mid = scanner.next();
            if (Validator.validateId('m', mid)) {
                if (isIdInMembers(mid, members)) {
                    for (Member member: members){
                        if (member.getID().equals(mid)){
//                            printMemberRecord(member);
                            member.print();
                            break;
                        }
                    }
                    break;
                } else {
                    System.out.println("*=>Error: The member ID is not in current list! Please try again.");
                }
            } else if (mid.equals('e') || mid.equals("exit")){
                break;
            } else {
                System.out.printf("*=>Error: It is not a valid member ID! " +
                        "Please have a check and try again.");
            }
        }
    }

    /**
     * Save current holdings and members to text-based files
     */
    public static void save2file() {
        ArrayList<String> holdingRecords = new ArrayList<>();
        ArrayList<String> memberRecords = new ArrayList<>();

        for (Holding holding: holdings) {
            // b000001,Intro to Java,10,28,not removed,active,not on loan,-,-,0
            String currentRecord = holding.getID() + "," + holding.getTitle() + ","
                    + holding.getDefaultLoanFee() + "," + holding.getMaxLoanPeriod() + ",";

            if (holding.isRemoved())
                currentRecord += "removed,";
            else
                currentRecord += "not removed,";

            if (holding.isActive())
                currentRecord += "active,";
            else
                currentRecord += "not active,";

            if (holding.isOnLoan())
                currentRecord += "on loan,";
            else
                currentRecord += "not on loan,";

//            if (holding.getBorrowDate() != null)
//                currentRecord += holding.getBorrowDate().toString() + ",";
//            else
//                currentRecord += "-,";
            String borrowDate = "-,";
            String returnDate = "-,";
            if (holding.getReturnDate() != null) {
//                currentRecord += holding.getReturnDate().toString() + ",";
                currentRecord += borrowDate + returnDate;
            } else {
                if (holding.getBorrowDate() != null) {
                    borrowDate = holding.getBorrowDate().toString();
                    currentRecord += borrowDate + returnDate;
                } else {
                    currentRecord += borrowDate + returnDate;
                }
            }
            currentRecord += holding.getLateFee() + "\n";

            holdingRecords.add(currentRecord);
        }

        boolean hSaved = saver.write2File(holdingRecords, "data/holdings.txt");

        for (Member member: members) {
            //s000001,Joe Bloggs,30,30,-
            String currentRecord = member.getID() + "," + member.getFullName() + ","
                    + member.getMaxCredit() + "," + member.getCredit() + ",";

            ArrayList<Holding> borrows = member.getHoldings();
            String borrowIDs= "-";
            if (borrows != null || !borrows.isEmpty()) {
                for (int i = 0; i < borrows.size(); i++) {
                    if (i == 0)
                        borrowIDs = borrows.get(i).getID();
                    else
                        borrowIDs += "-" + borrows.get(i).getID();
                }
            }

            currentRecord += borrowIDs + "\n";

            memberRecords.add(currentRecord);
        }

        saver = new SaveFile();
        boolean mSaved = saver.write2File(memberRecords, "data/members.txt");

        if (hSaved) {
            if (mSaved) {
                System.out.printf(">>> Congrats! The content has been saved!\n");
            } else {
                System.out.println("*=>Error: Problem occurred when saving the members.\n");
            }
        } else {
            System.out.println("*=>Error: Problem occurred when saving the holdings.\n");
        }

    }

    /**
     * Load from text-based files
     */
    public static void loadFile() {
//        save2file();

        ArrayList<String> holdingRecords = reader.readRecords("data/holdings.txt");
        for (String record: holdingRecords) {
            String attributes[] = record.split(",");
            String hid = attributes[0];
            String title = attributes[1];
            int defaultLoanFee = Integer.parseInt(attributes[2]);
            int maxLoanPeriod = Integer.parseInt(attributes[3]);
            String isRemoved = attributes[4];
            String isActive = attributes[5];
            String isOnLoan = attributes[6];
            String borrowDate = attributes[7];
            String returnDate = attributes[8];
            int lateFee = Integer.parseInt(attributes[9]);

            // initiate
            Holding holding;
            if (hid.charAt(0) == 'b') {
                holding = new Book(hid, title);
            } else {
                holding = new Video(hid, title, defaultLoanFee);
            }

            holding.setDefaultLoanFee(defaultLoanFee);
            holding.setMaxLoanPeriod(maxLoanPeriod);

            // set if it is removed or not
            if (isRemoved.equals("not removed"))
                holding.setRemoved(false);
            else
                holding.setRemoved(true);

            // set if it is active or not
            if (isActive.equals("active"))
                holding.setActive(true);
            else
                holding.setActive(false);

            // set if it is borrowed or not
            if (isOnLoan.equals("not on loan"))
                holding.setOnLoan(false);
            else
                holding.setOnLoan(true);

            // set the borrowed date
            if (!borrowDate.equals("-")) {
                String dates[] = borrowDate.split("-");
                int day = Integer.parseInt(dates[0]);
                int month = Integer.parseInt(dates[1]);
                int year = Integer.parseInt(dates[2]);
                holding.setBorrowDate(new DateTime(day, month,year));
            }

            // set the return date
            if (!returnDate.equals("-")) {
                String dates[] = returnDate.split("-");
                int day = Integer.parseInt(dates[0]);
                int month = Integer.parseInt(dates[1]);
                int year = Integer.parseInt(dates[2]);
                holding.setReturnDate(new DateTime(day, month,year));
            }

            // set the late fee
            holding.setLateFee(lateFee);

            holdings.add(holding);

//            System.out.println(holding instanceof Book);
        }

        reader = new ReadFile();
        ArrayList<String> memberRecords = reader.readRecords("data/members.txt");
        for (String record: memberRecords) {
            String attribtues[] = record.split(",");

            // attributes
            String mid = attribtues[0];
            String name = attribtues[1];
            int credit = Integer.parseInt(attribtues[3]);
            String borrows = attribtues[4];

            Member member;

            if (mid.charAt(0) == 's'){
                member = new StandardMember(mid, name);
            } else {
                member = new PremiumMember(mid, name);
            }

            member.setCredit(credit);

            if (!borrows.equals("-") || !borrows.equals("")) {
                String holdingIds[] = borrows.split("-");
                ArrayList<Holding> onLoans = new ArrayList<>();

                for (String hid: holdingIds) {
                    for (Holding holding: holdings) {
                        if (holding.getID().equals(hid)) {
                            onLoans.add(holding);
                            break;
                        }
                    }
                }

                member.setHoldings(onLoans);
            }

            members.add(member);
//System.out.printf(">>> added " + member.getID());
        }

        System.out.println(">>> Successfully Loaded from files!");
    }

    /**
     * Stop the program, and save all holdings and members' status
     */
    public static void exit() {
        save2file();
        System.out.println(">>> Successfully Exit!");
        System.exit(1);
    }

    /**
     * Used to tell whether a holding ID is in a list
     */
    public static boolean isIdInHoldings(String hid, ArrayList<Holding> holdings){
        for (Holding holding: holdings){
            if (holding.getID().equals(hid))
                return true;
        }

        return false;
    }

    /**
     * Used to tell whether a member ID is in a list
     */
    public static boolean isIdInMembers(String mid, ArrayList<Member> members){
        for (Member member: members){
            if (member.getID().equals(mid))
                return true;
        }

        return false;
    }

    /**
     * Print a holding record in a certain format
     */
//    public static void printHoldingRecord(Holding holding) {
//        System.out.format("ID:              %s%n", holding.getID());
//        System.out.format("Title:           %s%n", holding.getTitle());
//        System.out.format("Loan Fee:        %s%n", holding.getDefaultLoanFee());
//        System.out.format("Max Loan Period: %s%n", holding.getMaxLoanPeriod());
//        System.out.println("");
//    }

    /**
     * Print a member record in a certain format
     */
//    public static void printMemberRecord(Member member) {
//        System.out.format("ID:         %s%n", member.getID());
//        System.out.format("Title:      %s%n", member.getFullName());
//        System.out.format("Max Credit: %s%n", member.getMaxCredit());
//        System.out.println("");
//    }


    public boolean active() {

        return false;
    }

    public boolean deactive() {
        return false;
    }
}