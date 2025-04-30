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

                System.out.println("Deposit added successfully! \n");
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
                System.out.println("Payment added successfully thanks!\n");

            } catch (IOException e) {
                System.out.println("Error writing to file: " + e.getMessage());
            }
        }else {
            System.out.println("Invalid format");
        }
    }

    public static void viewTransactions() {
        System.out.println("############### All Transactions ###############\n");
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
        System.out.println("############### Deposits List ###############\n");
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
        System.out.println("############### Payments List ###############\n");
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

    //method for month to date
    //Changed all reporting methods (e.g., monthToDate, previousMonth, etc.) to use the parseTransaction() method with the Transaction class instead of manually splitting each line. This reduces duplication, improves readability, and allows safer access to transaction fields like date, vendor, and amount.

    public static void monthToDate() {
        System.out.println("############### Month To Date Transactions ###############\n");
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
                    if (transactionDate.getYear() == today.getYear() && transactionDate.getMonth() == today.getMonth()) {
                        System.out.println(t);
                    }
                }
            }

        } catch (IOException e) {
            System.out.println("Error reading file: " + e.getMessage());

        }
    }

    public static void previousMonth(){
        System.out.println("############### Previous Month Transactions ###############\n");
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
        System.out.println("############### Year To Date Transactions ###############\n");

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
        System.out.println("############### Previous Year Transactions ###############\n");
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
        System.out.println("############### Transaction Matching Vendor ###############\n");
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


