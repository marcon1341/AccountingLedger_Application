import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Scanner;

public class Transactions {

    //method for deposit
    public static void addDeposit() {
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

    public static void makePayment() {
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
        if (s.hasNextDouble()) {
            amount = s.nextDouble();
            s.nextLine();

            amount *= -1;//to make it negative(debt)

            //transaction line
            String transaction = date + "|" + time + "|" + description + "|" + vendor + "|" + String.format("%.2f", amount);

            try {
                FileWriter fileWriter = new FileWriter("transactions.csv", true);
                fileWriter.write(transaction + "\n");
                fileWriter.close();
                System.out.println("Payment added successfully thanks!");

            } catch (IOException e) {
                System.out.println("Error writing to file: " + e.getMessage());
            }
        }
    }

    public static void ledgerMenu() {
        Scanner s = new Scanner(System.in);
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
                viewTransactions();
            } else if (userInput.equalsIgnoreCase("d")) {
                viewDeposits();
            } else if (userInput.equalsIgnoreCase("p")) {
                viewPayments();
            } else if (userInput.equalsIgnoreCase("r")) {
                reportMenu();
            } else if (userInput.equalsIgnoreCase("h")) {
                System.out.println("Return to Home Screen");
            } else {
                System.out.println("Invalid option try again");
            }
        } while (!userInput.equalsIgnoreCase("h"));
    }

    public static void viewTransactions() {
        try {
            FileReader flReader = new FileReader("transactions.csv");
            BufferedReader br = new BufferedReader(flReader);
            System.out.println("All Transactions");
            String line;
            while ((line = br.readLine()) != null) {
                System.out.println(line);
            }

            br.close();
        } catch (IOException e) {
            System.out.println("Error reading file: " + e.getMessage());
        }
    }

    public static void viewDeposits() {

        try {
            FileReader flReader = new FileReader("transactions.csv");
            BufferedReader br = new BufferedReader(flReader);
            String line;

            System.out.println("Deposits");

            br.readLine();//to skip the first line(the header in csv file)

            while ((line = br.readLine()) != null) {
                String[] on = line.split("\\|");//to split @ "|"
                double amount = Double.parseDouble(on[4]);//to parse the amount

                if (amount > 0) {
                    System.out.println(line);
                }
            }
            br.close();
        } catch (Exception e) {
            System.out.println("Error reading transactions.csv: " + e.getMessage());
        }
    }

    //view payment method to see negative amount payments
    public static void viewPayments() {

        try {
            FileReader flReader = new FileReader("transactions.csv");
            BufferedReader br = new BufferedReader(flReader);
            String line;

            System.out.println("Deposits");

            br.readLine();//to skip the first line(the header in csv file)

            while ((line = br.readLine()) != null) {
                String[] on = line.split("\\|");//to split @ "|"
                double amount = Double.parseDouble(on[4]);//to parse the amount

                if (amount < 0) {//check when amounts are in negative
                    System.out.println(line);
                }
            }
            br.close();
        } catch (Exception e) {
            System.out.println("Error reading transactions.csv: " + e.getMessage());
        }
    }

    //report menu screen
    public static void reportMenu() {
        Scanner s = new Scanner(System.in);
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
               monthToDate();
            } else if (userInput.equals("2")) {
                previousMonth();
            } else if (userInput.equals("3")) {
                yearToDate();
            } else if (userInput.equals("4")) {
                System.out.println("previous year");
            } else if (userInput.equals("5")) {
                System.out.println("search by vendor");
            } else if (userInput.equals("0")) {
                System.out.println("back to leadger menu");
            } else {
                System.out.println("Invalid option try again: ");
            }
        } while (!userInput.equals("0"));
    }

    public static void monthToDate() {
        try {
            FileReader flReader = new FileReader("transactions.csv");
            BufferedReader br = new BufferedReader(flReader);
            String line;
            System.out.println("Month to date transaction");
            br.readLine();//to skip the header
            LocalDate today = LocalDate.now();//to get todays date

            while ((line = br.readLine()) != null) {
                String[] on = line.split("\\|");
                String date = on[0];
                //parsing the date
                LocalDate transactionDate = LocalDate.parse(date, DateTimeFormatter.ofPattern("yyyy-MM-dd"));

                //to compare year and month
                if (transactionDate.getYear() == today.getYear() && transactionDate.getMonth() == today.getMonth()) {
                    System.out.println(line);
                }
            }
            br.close();
        } catch (Exception e) {
            System.out.println("Error reading transactions.csv: " + e.getMessage());
        }
    }
    //new method added for previous month
    public static void previousMonth() {
        try {
            FileReader flReader = new FileReader("transactions.csv");
            BufferedReader br = new BufferedReader(flReader);
            String line;

            System.out.println("Previous month transaction");

            br.readLine();//to skip the header

            LocalDate today = LocalDate.now();//to get todays date
            LocalDate lastMonth = today.minusMonths(1);

            while ((line = br.readLine()) != null) {
                String[] on = line.split("\\|");
                String date = on[0];
                //parsing the date
                LocalDate transactionDate = LocalDate.parse(date, DateTimeFormatter.ofPattern("yyyy-MM-dd"));

                //to compare year and month
                if (transactionDate.getYear() == lastMonth.getYear() && transactionDate.getMonth() == lastMonth.getMonth()) {
                    System.out.println(line);
                }
            }
            br.close();
        } catch (Exception e) {
            System.out.println("Error reading transactions.csv: " + e.getMessage());
        }

    }
    //new method for year to date
    public static void yearToDate() {

        try {
            FileReader flReader = new FileReader("transactions.csv");
            BufferedReader br = new BufferedReader(flReader);
            String line;

            System.out.println("Year to date transaction");

            br.readLine();//to skip the header

            LocalDate today = LocalDate.now();//to get todays date

            while ((line = br.readLine()) != null) {
                String[] on = line.split("\\|");
                String date = on[0];
                //parsing the date
                LocalDate transactionDate = LocalDate.parse(date, DateTimeFormatter.ofPattern("yyyy-MM-dd"));

                //to compare year and month
                if (transactionDate.getYear() == today.getYear()) {
                    System.out.println(line);
                }
            }
            br.close();
        } catch (Exception e) {
            System.out.println("Error reading transactions.csv: " + e.getMessage());
        }
    }

}










