import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Scanner;

class Transactions {
    public static Transaction parseTransaction(String line) {//to convert the line to transaction object
        String[] parts = line.split("\\|");//split the transaction line @"|"
        if (parts.length < 5) return null;

        //give index number for each part
        String date = parts[0];
        String time = parts[1];
        String description = parts[2];
        String vendor = parts[3];
        double amount = Double.parseDouble(parts[4]);

        return new Transaction(date, time, description, vendor, amount);//
    }


    //method to add deposit
    public static void addDeposit() {
        Scanner s = new Scanner(System.in);


        //asks the user to input date
        System.out.print("Enter the date (yyyy-mm-dd): ");
        String date = s.nextLine();

        //asks teh user to input time
        System.out.print("Enter the time (HH:mm:ss): ");
        String time = s.nextLine();

        //asks the user to input description
        System.out.print("Enter the description: ");
        String description = s.nextLine();


        //asks the user to input vendor name
        System.out.print("Enter the vendor: ");
        String vendor = s.nextLine();

         //asks the user to input amount
        System.out.println("Enter the amount: ");
        if (s.hasNextDouble()) {//check the validity(double like 99.99 and prevent the crash from invalid input e.g"amazon"
            double amount = s.nextDouble();
            s.nextLine();//new line

            //  format the transaction display line
            String transaction = date + "|" + time + "|" + description + "|" + vendor + "|" + String.format("%.2f", amount);

            // FileWriter to write on transaction.csv using try and catch exception
            try {
                FileWriter fileWriter = new FileWriter("transactions.csv", true); // true to append the order entry
                fileWriter.write(transaction + "\n");
                fileWriter.close();//to make sure the data saved and to prevent crashes

                System.out.println("Deposit added successfully! \n");
            } catch (IOException e) {
                System.out.println("Error writing to file: " + e.getMessage());
            }
        }else {
            System.out.println("Invalid format");
        }
    }

    //method to make payment(debit)
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
            amount *= -1;//to make it negative for payment

            //format the transaction display line
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

    //to display all transaction from the csv
    public static void viewTransactions() {
        System.out.println("############### All Transactions ###############\n");
        try {
            FileReader flReader = new FileReader("transactions.csv");
            BufferedReader br = new BufferedReader(flReader);//to read efficiently

            String line;
            br.readLine();//to skip the header
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
                if (t != null && t.getAmount() > 0) {//to get only +ve only positive transactions
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
                if (t != null && t.getAmount() < 0) {//for -ve transaction
                    System.out.println(t);
                }
            }
        } catch (IOException e) {
            System.out.println("Error reading transactions.csv: " + e.getMessage());
        }
    }

    // Filter and show only current month transactions
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

                    //compare year and month
                    if (transactionDate.getYear() == today.getYear() && transactionDate.getMonth() == today.getMonth()) {
                        System.out.println(t);
                    }
                }
            }

        } catch (IOException e) {
            System.out.println("Error reading file: " + e.getMessage());

        }
    }

    //Filter and show previous month transactions
    public static void previousMonth(){
        System.out.println("############### Previous Month Transactions ###############\n");
        try {
            FileReader flReder = new FileReader("transactions.csv");
            BufferedReader br = new BufferedReader(flReder);

            String line;
            br.readLine();

            LocalDate today = LocalDate.now();
            LocalDate lastMonth = today.minusMonths(1);//to get the previous month -1

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

    //Filter and show current year transactions
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
    //Filter and show last year transactions
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


    // Search for vendor names
    public static void searchVendor(){
        Scanner s = new Scanner(System.in);
        System.out.print("Enter vendor's name to search: ");

        String search = s.nextLine().toLowerCase();//to store the variable to search and make it case insensitive

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


