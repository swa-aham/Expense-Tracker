import expensetracker.service.ExpenseTrackerService;
import expensetracker.ui.ConsoleUI;

import java.util.Arrays;

public class Main {
    public static void main(String[] args) {
        ExpenseTrackerService service = new ExpenseTrackerService();
        ConsoleUI ui = new ConsoleUI(service);

        if (args.length > 0) {
            String filePath = args[0];
            System.out.println("Loading data from file: " + filePath);
            service.loadTransactionsFromFile(filePath);
        }

        ui.start();
    }
}