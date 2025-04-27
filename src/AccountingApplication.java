import java.io.FileWriter;
import java.io.IOException;
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
                addDeposit();

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
    //method for deposit
    public static void addDeposit(){
        Scanner s = new Scanner(System.in);

        System.out.println("Enter the date e.g.(yyyy-mm-dd: )");
        String date = s.nextLine();

        System.out.println("Enter the time e.g.(HH:mm:ss: )");
        String time = s.nextLine();

        System.out.println("Enter the description: ");
        String description = s.nextLine();

        System.out.println("Enter the vendor: ");
        String vendor = s.nextLine();

        System.out.println("Enter the amount: ");
        Double amount = s.nextDouble();
        s.nextLine();
        //transaction display line
        String transactionDisplay = String.format("%s | %s | %s | %s | $%.2f", date, time, description, vendor, amount);

    //create file writer to write on transactions.csv file
        try {
            FileWriter fileWriter = new FileWriter("transactions.csv", true);//append to order entry
            fileWriter.write(transactionDisplay + "\n");
            fileWriter.close();

            System.out.println("Deposit added successfully!");
        } catch (IOException e) {
            System.out.println("Error to write to file: " + e.getMessage());;
            }
        s.close();
        }

}

