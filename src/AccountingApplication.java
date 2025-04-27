import java.util.Scanner;//Scanner to read user input

public class AccountingApplication {
    public static void main(String[] args) {
        Scanner s = new Scanner(System.in);//
        String userInput;//variable to store user choice
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
            userInput = s.nextLine();//read input

            //if else conditions to check input
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

        }while(!userInput.equalsIgnoreCase("x"));//this repeat the menu until user input "x"
        s.close();//to close the scanner
    }
}
