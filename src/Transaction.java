public class Transaction {
        private String date;
        private String time;
        private String description;
        private String vendor;
        private double amount;

        //constructors for the new object
        public Transaction(String date, String time, String description, String vendor, double amount){
            this.date = date;
            this.time = time;
            this.description = description;
            this.vendor = vendor;
            this.amount = amount;
        }
        //getters to access
        public String getDate(){
            return this.date;
        }
        public String getTime(){
            return this.time;
        }
        public String getDescription(){
            return this.description;
        }
        public String getVendor(){
            return this.vendor;
        }
        public double getAmount(){
            return this.amount;
        }
        //add formating for display
        @Override
        public String toString(){
            return String.format(date + "|" + time + "|" + description + "|" + vendor + "|" + String.format("%.2f",amount));
        }

    }

