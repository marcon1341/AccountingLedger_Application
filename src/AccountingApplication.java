import java.util.Scanner;//to read user input

public class AccountingApplication {
    public static void main(String[] args) {
        Transactions t = new Transactions();
        Scanner s = new Scanner(System.in);
        String userInput;
        //do while loop to run once the home screen and
        // keep running until user choice "x"

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
                System.out.println("View Ledge");
            } else if (userInput.equalsIgnoreCase("x")) {
                System.out.println("Exiting The Application");
            } else {
                System.out.println("Invalid option Try again");
            }
        } while (!userInput.equalsIgnoreCase("x"));//this repeat the menu until user input "x"
        s.close();
    }
}