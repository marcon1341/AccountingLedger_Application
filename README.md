The Application is a simple Java-based console program that helps users manage financial transactions using command line java program. It allows to:

>> Record deposits and payments

>> View all transactions, deposits, or payments.

>> Generate reports for specific time periods (month-to-date, previous year, year to date and previous month).

>> Search by vendor  using date, time, description, vendor and amount.

>> Custom search for specific report.
>>
 Home screen, ledger and report menu sample
![Screenshot 2025-05-01 172240](https://github.com/user-attachments/assets/8232260c-c36a-404f-a3ff-654f5122f2c5)


one interesting piece of code from this application I used LocalDate and LocalTime to automatically capture the current date and time when a user adds a deposit or makes a payment. Instead of asking the user to manually type this every time,

        //insert present date automatically
        String date = LocalDate.now().toString();

        //insert present time automatically
        String time = LocalTime.now().format(DateTimeFormatter.ofPattern("HH:mm:ss"));
