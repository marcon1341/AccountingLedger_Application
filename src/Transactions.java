import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalTime;
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
        double amount = Double.parseDouble(parts[4]);//to convert transaction amount from string to double

        return new Transaction(date, time, description, vendor, amount);//record the new transaction
    }


    //method to add deposit
    public static void addDeposit() {
        Scanner s = new Scanner(System.in);

        //insert present date automatically
        String date = LocalDate.now().toString();

        //insert present time automatically
        String time = LocalTime.now().format(DateTimeFormatter.ofPattern("HH:mm:ss"));

        //asks the user to input description
        System.out.print("Enter the description: ");
        String description = s.nextLine();


        //asks the user to input vendor name
        System.out.print("Enter the vendor: ");
        String vendor = s.nextLine();

         //asks the user to input amount
        System.out.println("Enter the amount: ");
        if (s.hasNextDouble()) {//check the validity(double like 99.99 and prevent the crash from invalid input e.g "amazon"
            double amount = s.nextDouble();
            s.nextLine();//new line

            //  format the new transaction display line
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

        //insert present date automatically
        String date = LocalDate.now().toString();

        //insert present time automatically
        String time = LocalTime.now().format(DateTimeFormatter.ofPattern("HH:mm:ss"));

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

                    //to flitter transaction by time using LocalDate
                    LocalDate transactionDate = LocalDate.parse(t.getDate(), DateTimeFormatter.ofPattern("yyyy-MM-dd"));

                    //compare the date to todays year and month
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

        String search = s.nextLine().trim().toLowerCase();//to store the variable to search and make it case insensitive

        System.out.println("############### Transaction Matching Vendor ###############\n");

        try{
            FileReader flReader = new FileReader("transactions.csv");
            BufferedReader br = new BufferedReader(flReader);
            String line;
            br.readLine();

            while ((line = br.readLine())!= null){
                Transaction t = parseTransaction(line);
                if(t!=null && t.getVendor().toLowerCase().contains(search)){//allows partial search
                    System.out.println(t);

                }
            }

        } catch (IOException e) {
            System.out.println("Error reading transactions.csv: " + e.getMessage());
        }
    }

    //custom search method
    public static void customSearch() {

        Scanner s = new Scanner(System.in);//scanner object to read input

        System.out.println("############### Custom Search ###############");

        //optional flitters
        System.out.print("Start Date (yyyy-MM-dd): ");
        String startInput = s.nextLine();

        System.out.print("End Date (yyyy-MM-dd): ");
        String endInput = s.nextLine();

        System.out.print("Description: ");
        String description = s.nextLine().toLowerCase();

        System.out.print("Vendor: ");
        String vendor = s.nextLine().toLowerCase();

        System.out.print("Amount: ");
        String amountInput = s.nextLine();

        LocalDate startDate;
        if (startInput.isEmpty()) {
            startDate = null;
        } else {
            startDate = LocalDate.parse(startInput);//to Parse start date input to LocalDate
        }

        LocalDate endDate;
        if (endInput.isEmpty()) {
            endDate = null;
        } else {
            endDate = LocalDate.parse(endInput);//to Parse end date input to LocalDate
        }

        Double amount;
        if (amountInput.isEmpty()) {
            amount = null;
        } else {
            amount = Double.parseDouble(amountInput);//to Parse amount input to Double
        }

        try {
            BufferedReader br = new BufferedReader(new FileReader("transactions.csv"));
            String line;
            br.readLine(); // skip header
            while ((line = br.readLine()) != null) {
                Transaction t = parseTransaction(line);
                if (t == null) continue;// Skip this line if parsing failed

                LocalDate transactionDate = LocalDate.parse(t.getDate());// Parse the transaction date from the object

                boolean is = true;

                // If the user provided a start date and the transaction is before that, it's not true
                if (startDate != null && transactionDate.isBefore(startDate)) is = false;

                // If there's an end date and the transaction is after it, it's not true
                if (endDate != null && transactionDate.isAfter(endDate)) is = false;

                // If the user gave a description, and it doesn't match the one in the transaction
                if (!description.isEmpty() && !t.getDescription().toLowerCase().contains(description)) is = false;

                // If a vendor name was given and doesn't appear in the transaction
                if (!vendor.isEmpty() && !t.getVendor().toLowerCase().contains(vendor)) is = false;

                // If the amount is specified and doesn't match exactly, it's not true
                if (amount != null && t.getAmount() != amount) is = false;

                if (is) {
                    System.out.println(t);
                }
            }
        } catch (IOException e) {
            System.out.println("Error reading transactions.csv: " + e.getMessage());
        }
    }
}


