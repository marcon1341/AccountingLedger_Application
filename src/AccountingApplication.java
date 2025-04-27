import java.util.Scanner;

public class AccountingApplication {
    public static void main(String[] args) {
        Scanner s = new Scanner(System.in);
        String userInput;
        do {
            System.out.println("""
                    Please enter a number to proceed:
                    D - Add Deposit
                    P - Make Payment (Debit)
                    L - View Ledger
                    X - Exit - exit the application
                    """);
            System.out.println("Enter your choice: ");
            userInput = s.nextLine();
            if (userInput.equalsIgnoreCase("d")){
                System.out.println("Add Deposit");
            } else if (userInput.equalsIgnoreCase("p")){
                System.out.println("Make Payment");
            } else if (userInput.equalsIgnoreCase("l")) {
                System.out.println("View Ledge");
            } else if (userInput.equalsIgnoreCase("x")) {
                System.out.println("Exiting The Application");
            } else {
                System.out.println("Invalid option Try again");
            }
        }while(!userInput.equalsIgnoreCase("x"));
        s.close();
    }
}
