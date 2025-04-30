import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.Scanner;//to read user input

public class AccountingApplication {
    public static void main(String[] args) {
        Scanner s = new Scanner(System.in);//scanner to read user input
        Menus.homeMenu(s);//call the home menu from the menus class
    }
}
