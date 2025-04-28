import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;

public class Transactions {

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
    public static void ledgerMenu(){
        Scanner s = new Scanner(System.in);
        String userInput;
        do{
        System.out.println("""
                        A - All
                        D - Deposit
                        P - View Payment
                        R - Reports
                        H - Back - go back to the report page
                        """);
        System.out.println("Enter your choice");
        userInput = s.nextLine();
        if(userInput.equalsIgnoreCase("a")){
            //allTransactions();
        } else if(userInput.equalsIgnoreCase("d")) {
            System.out.println("View Deposit");
        } else if(userInput.equalsIgnoreCase("p")){
            System.out.println("View Payment");
        }else if(userInput.equalsIgnoreCase("r")){
            System.out.println("View Reports");
        }else if(userInput.equalsIgnoreCase("h")){
            System.out.println("Return to Home Screen");
        }else {
            System.out.println("Invalid option try again");
        }
        }while(!userInput.equalsIgnoreCase("h"));
        s.close();
    }

}



