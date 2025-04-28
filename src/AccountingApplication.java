import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;//to read user input

public class AccountingApplication {
    public static void main(String[] args) {
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
            addDeposit();
        } else if (userInput.equalsIgnoreCase("p")) {
            makePayment();
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
    //method for deposit
    public static void addDeposit(){
        Scanner s = new Scanner(System.in);

        System.out.print("Enter the date (yyyy-mm-dd): ");
        String date = s.nextLine();

        System.out.print("Enter the time (HH:mm:ss): ");
        String time = s.nextLine();

        System.out.print("Enter the description: ");
        String description = s.nextLine();

        System.out.print("Enter the vendor: ");
        String vendor = s.nextLine();

        System.out.print("Enter the amount: ");
        double amount = 0.0;

        //hasNextDouble to eliminate crashing and save time
        if (s.hasNextDouble()) {
            amount = s.nextDouble();
            s.nextLine();

        //  transaction display line
        String transaction = date + "|" + time + "|" + description + "|" + vendor + "|" + String.format("%.2f", amount);

        // FileWriter to write on transaction.csv using try and catch exception
            try {
                FileWriter fileWriter = new FileWriter("transactions.csv", true); // true to append the order entry
                fileWriter.write(transaction + "\n");
                fileWriter.close();

                System.out.println("Deposit added successfully!");
            } catch (IOException e) {
                System.out.println("Error writing to file: " + e.getMessage());
            }
        }
    }
   public static void makePayment(){
       Scanner s = new Scanner(System.in);
       System.out.print("Enter the date (yyyy-mm-dd): ");
       String date = s.nextLine();

       System.out.print("Enter the time (HH:mm:ss): ");
       String time = s.nextLine();

       System.out.print("Enter the description: ");
       String description = s.nextLine();

       System.out.print("Enter the vendor: ");
       String vendor = s.nextLine();

       System.out.print("Enter the amount: ");
       double amount = 0.0;
       if(s.hasNextDouble()){
           amount = s.nextDouble();
           s.nextLine();

           amount *= -1;//to make it negative(debt)

       //transaction line
           String transaction = date+ "|" + time+ "|" + description+ "|" + vendor+ "|" + String.format("$%.2f",amount);

       try {
           FileWriter fileWriter = new FileWriter("transactions.csv",true);
           fileWriter.write(transaction + "\n");
           fileWriter.close();
           System.out.println("Payment added successfully thanks!");

           }catch (IOException e) {
           System.out.println("Error writing to file: " + e.getMessage());
           }
       }
   }
}

