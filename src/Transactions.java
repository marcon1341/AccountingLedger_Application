import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Scanner;

class Transactions {
    public static Transaction parseTransaction(String line) {
        String[] parts = line.split("\\|");
        if (parts.length < 5) return null;

        String date = parts[0];
        String time = parts[1];
        String description = parts[2];
        String vendor = parts[3];
        double amount = Double.parseDouble(parts[4]);

        return new Transaction(date, time, description, vendor, amount);
    }


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

        System.out.println("Enter the amount: ");
        if (s.hasNextDouble()) {
            double amount = s.nextDouble();
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
        }else {
            System.out.println("Invalid format");
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

        System.out.println("Enter the amount: ");
        if (s.hasNextDouble()) {
            double amount = s.nextDouble();
            s.nextLine();
            amount *= -1;
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
        }else {
            System.out.println("Invalid format");
        }
    }

    public static void ledgerMenu(Scanner s) {

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
                System.out.println("Returning to Home Screen");
                break;//exit the loop to home menu
            } else {
                System.out.println("Invalid option try again");
            }
        } while (!userInput.equalsIgnoreCase("h"));

    }
    public static void viewTransactions() {
        System.out.println("~~~~ All Transactions ~~~~");
        try {
            FileReader flReader = new FileReader("transactions.csv");
            BufferedReader br = new BufferedReader(flReader);

            String line;
            br.readLine();
            while ((line = br.readLine()) != null) {
                Transaction t = parseTransaction(line);
                if (t != null) {
                    System.out.println(t);
                }
            }
        } catch (IOException e) {
            System.out.println("Error reading file: " + e.getMessage());
        }
    }
    //new method for view deposits
    public static void viewDeposits() {
        System.out.println("~~~~ Deposits List ~~~~");
        try {
            FileReader flReader = new FileReader("transactions.csv");
            BufferedReader br = new BufferedReader(flReader);

            String line;
            br.readLine();
            while ((line = br.readLine()) != null) {
                Transaction t = parseTransaction(line);
                if (t != null && t.getAmount() > 0) {
                    System.out.println(t);
                }
            }

        } catch (Exception e) {
            System.out.println("Error reading file: " + e.getMessage());
        }

    }

    //view payment method to see negative amount payments
    public static void viewPayments() {
        System.out.println("~~~~ Payments List ~~~~");
        try  {
            FileReader flReader = new FileReader("transactions.csv");
            BufferedReader br = new BufferedReader(flReader);
            br.readLine(); // Skip header
            String line;

            while ((line = br.readLine()) != null) {
                Transaction t = parseTransaction(line);
                if (t != null && t.getAmount() < 0) {
                    System.out.println(t);
                }
            }
        } catch (IOException e) {
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
                previousYear();
            } else if (userInput.equals("5")) {
                searchVendor();
            } else if (userInput.equals("0")) {
                Transactions.ledgerMenu(s);
            } else {
                System.out.println("Invalid option try again: ");
            }
        } while (!userInput.equals("0"));
    }

    //method for month to date
    //Changed all reporting methods (e.g., monthToDate, previousMonth, etc.) to use the parseTransaction() method with the Transaction class instead of manually splitting each line. This reduces duplication, improves readability, and allows safer access to transaction fields like date, vendor, and amount.

    public static void monthToDate() {
        System.out.println("~~~~ Month To Date Transactions ~~~~");
        try {
            FileReader flReder = new FileReader("transactions.csv");
            BufferedReader br = new BufferedReader(flReder);

            String line;
            br.readLine();//skip header

            LocalDate today = LocalDate.now();//todays date

            while ((line = br.readLine()) != null) {
                Transaction t = parseTransaction(line);
                if (t != null) {
                    LocalDate transactionDate = LocalDate.parse(t.getDate(), DateTimeFormatter.ofPattern("yyyy-MM-dd"));

                    //copare year and month
                    if (transactionDate.getYear() == today.getYear() && transactionDate.getMonth() == today.getMonth());
                    System.out.println(t);
                }
            }

        } catch (IOException e) {
            System.out.println("Error reading file: " + e.getMessage());

        }
    }

    public static void previousMonth(){
        System.out.println("~~~~ Previous Month Transactions ~~~~");
        try {
            FileReader flReder = new FileReader("transactions.csv");
            BufferedReader br = new BufferedReader(flReder);

            String line;
            br.readLine();

            LocalDate today = LocalDate.now();
            LocalDate lastMonth = today.minusMonths(1);

            while ((line = br.readLine()) != null){
                Transaction t = parseTransaction(line);

                if(t != null){
                    LocalDate transactionDate = LocalDate.parse(t.getDate(), DateTimeFormatter.ofPattern("yyyy-MM-dd"));

                    if(transactionDate.getYear() == lastMonth.getYear() && transactionDate.getMonth()== lastMonth.getMonth()){
                        System.out.println(t);
                    }
                }
            }

        } catch (Exception e) {
            System.out.println("Error reading file: " + e.getMessage());
        }
    }


    public static void yearToDate() {
        System.out.println("~~~~ Year To Date Transactions ~~~~");

        try {
            FileReader flReader = new FileReader("transactions.csv");
            BufferedReader br = new BufferedReader(flReader);
            String line;
            br.readLine();//skip header

            LocalDate today = LocalDate.now();

            while ((line = br.readLine()) != null) {
                Transaction t = parseTransaction(line);
                if (t != null) {
                    LocalDate transactionDate = LocalDate.parse(t.getDate(), DateTimeFormatter.ofPattern("yyyy-MM-dd"));

                    if (transactionDate.getYear() == today.getYear()) {
                        System.out.println(t);
                    }

                }
            }
        } catch (IOException e) {
            System.out.println("Error reading file: " + e.getMessage());
        }
    }

    public static void previousYear() {
        System.out.println("~~~~ Previous Year Transactions ~~~~");
        try {
            FileReader flReader = new FileReader("transactions.csv");
            BufferedReader br = new BufferedReader(flReader);
            String line;
            br.readLine();

            LocalDate today = LocalDate.now();
            LocalDate lastYear = today.minusYears(1);

            while ((line = br.readLine()) != null) {
                Transaction t = parseTransaction(line);
                if (t != null) {
                    LocalDate transactionDate = LocalDate.parse(t.getDate(), DateTimeFormatter.ofPattern("yyyy-MM-dd"));
                    if (transactionDate.getYear() == lastYear.getYear()) {
                        System.out.println(t);

                    }
                }
            }

        } catch (IOException e) {
            System.out.println("Error reading file: " + e.getMessage());
        }
    }
    public static void searchVendor(){
        Scanner s = new Scanner(System.in);
        String search = s.nextLine().toLowerCase();
        System.out.println("~~~~ Transaction Matching Vendor ~~~~");
        try{
            FileReader flReader = new FileReader("transactions.csv");
            BufferedReader br = new BufferedReader(flReader);
            String line;
            br.readLine();

            while ((line = br.readLine())!= null){
                Transaction t = parseTransaction(line);
                if(t!=null && t.getVendor().toLowerCase().contains(search)){
                    System.out.println(t);

                }
            }

        } catch (IOException e) {
            System.out.println("Error reading transactions.csv: " + e.getMessage());
        }
    }
}


