import java.util.Scanner;

public class Menus {
    public static void homeMenu(Scanner s){
        System.out.println("""
            $$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$
            $$         ACCOUNTING LEDGER APPLICATION          $$
            $$------------------------------------------------$$
            $$ >Add Deposit<  >Make Payment<  >View Reports<  $$
            $$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$
            """);
        String userInput;
        do {
            System.out.println("""
                    Please enter a number to proceed:
                    D - Add Deposit
                    P - Make Payment (Debit)
                    L - View Ledger
                    X - Exit - exit the application
                    """);
            //asks to type choice
            System.out.println("Enter your choice: ");

            //if else conditions to check input
            userInput = s.nextLine();
            if (userInput.equalsIgnoreCase("d")) {
                Transactions.addDeposit();
            } else if (userInput.equalsIgnoreCase("p")) {
                Transactions.makePayment();
            } else if (userInput.equalsIgnoreCase("l")) {
                ledgerMenu(s);
            } else if (userInput.equalsIgnoreCase("x")) {
                System.out.println("Exiting The Application");
            } else {
                System.out.println("Invalid option Try again");
            }
        } while (!userInput.equalsIgnoreCase("x"));//this repeat the menu until user input "x"
        s.close();
    }
    public static void ledgerMenu(Scanner s) {
        System.out.println("""
            $$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$
            $$         LEDGER MENU          $$
            $$------------------------------$$
            $$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$
            """);

        String userInput;
        do {
            System.out.println("""
                    A - All
                    D - Deposit
                    P - View Payment
                    R - Reports
                    H - Back - go back to the report page
                    """);
            System.out.println("Enter your choice");
            userInput = s.nextLine();
            if (userInput.equalsIgnoreCase("a")) {
                Transactions.viewTransactions();
            } else if (userInput.equalsIgnoreCase("d")) {
                Transactions.viewDeposits();
            } else if (userInput.equalsIgnoreCase("p")) {
                Transactions.viewPayments();
            } else if (userInput.equalsIgnoreCase("r")) {
                reportMenu(s);
            } else if (userInput.equalsIgnoreCase("h")) {
                System.out.println("Returning to Home Screen");
                break;//exit the loop to home menu
            } else {
                System.out.println("Invalid option try again");
            }
        } while (!userInput.equalsIgnoreCase("h"));

    }
    public static void reportMenu(Scanner s) {
        System.out.println("""
            $$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$
            $$           REPORT MENU              $$
            $$------------------------------------$$
            $$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$
            """);
        String userInput;
        do {
            System.out.println("""
                    Report Menu:
                    1 - Month To Date
                    2 - Previous Month
                    3 - Year To Date
                    4 - Previous Year
                    5 - Search by Vendor
                    0 - Back to Ledger Menu
                    """);
            System.out.println("Enter your choice: ");
            userInput = s.nextLine();

            if (userInput.equals("1")) {
                Transactions.monthToDate();
            } else if (userInput.equals("2")) {
                Transactions.previousMonth();
            } else if (userInput.equals("3")) {
                Transactions.yearToDate();
            } else if (userInput.equals("4")) {
                Transactions.previousYear();
            } else if (userInput.equals("5")) {
                Transactions.searchVendor();
            } else if (userInput.equals("0")) {
                ledgerMenu(s);
            } else {
                System.out.println("Invalid option try again: ");
            }
        } while (!userInput.equals("0"));
    }

}

