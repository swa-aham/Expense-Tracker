package expensetracker.ui;

import expensetracker.model.Category;
import expensetracker.model.Transaction;
import expensetracker.model.TransactionType;
import expensetracker.service.ExpenseTrackerService;

import java.time.LocalDate;
import java.time.Month;
import java.time.YearMonth;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

public class ConsoleUI {
    private final ExpenseTrackerService service;
    private final Scanner scanner;
    private final DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");

    public ConsoleUI(ExpenseTrackerService service) {
        this.service = service;
        this.scanner = new Scanner(System.in);
    }

    public void start() {
        boolean running = true;

        while (running) {
            displayMenu();
            String choice = scanner.nextLine().trim();

            switch (choice) {
                case "1":
                    addTransaction();
                    break;
                case "2":
                    viewMonthlySummary();
                    break;
                case "3":
                    loadFromFile();
                    break;
                case "4":
                    saveToFile();
                    break;
                case "5":
                    running = false;
                    break;
                default:
                    System.out.println("Invalid choice. Please try again.");
            }
        }

        System.out.println("Thank you for using Expense Tracker. Goodbye!");
    }

    private void displayMenu() {
        System.out.println("\n===== EXPENSE TRACKER =====");
        System.out.println("1. Add Transaction");
        System.out.println("2. View Monthly Summary");
        System.out.println("3. Load from File");
        System.out.println("4. Save to File");
        System.out.println("5. Exit");
        System.out.print("Enter your choice: ");
    }

    private void addTransaction() {
        System.out.println("\n----- Add Transaction -----");

        // Get transaction type
        TransactionType type = getTransactionType();

        // Get category based on transaction type
        Category category = getCategory(type);

        // Get amount
        double amount = getAmount();

        // Get description
        System.out.print("Enter description: ");
        String description = scanner.nextLine().trim();

        // Get date
        LocalDate date = getDate();

        // Create and add transaction
        Transaction transaction = new Transaction(amount, description, category, type, date);
        service.addTransaction(transaction);

        System.out.println("Transaction added successfully!");
    }

    private TransactionType getTransactionType() {
        while (true) {
            System.out.println("Select transaction type:");
            System.out.println("1. Income");
            System.out.println("2. Expense");
            System.out.print("Enter your choice (1/2): ");

            String choice = scanner.nextLine().trim();

            if (choice.equals("1")) {
                return TransactionType.INCOME;
            } else if (choice.equals("2")) {
                return TransactionType.EXPENSE;
            } else {
                System.out.println("Invalid choice. Please try again.");
            }
        }
    }

    private Category getCategory(TransactionType type) {
        Category[] categories;

        if (type == TransactionType.INCOME) {
            categories = Category.getIncomeCategories();
            System.out.println("\nSelect income category:");
        } else {
            categories = Category.getExpenseCategories();
            System.out.println("\nSelect expense category:");
        }

        for (int i = 0; i < categories.length; i++) {
            System.out.printf("%d. %s\n", i + 1, categories[i]);
        }

        while (true) {
            System.out.print("Enter your choice (1-" + categories.length + "): ");
            try {
                int choice = Integer.parseInt(scanner.nextLine().trim());

                if (choice >= 1 && choice <= categories.length) {
                    return categories[choice - 1];
                }
            } catch (NumberFormatException e) {
                // Do nothing, will display error message
            }

            System.out.println("Invalid choice. Please try again.");
        }
    }

    private double getAmount() {
        while (true) {
            System.out.print("Enter amount: ");
            try {
                return Double.parseDouble(scanner.nextLine().trim());
            } catch (NumberFormatException e) {
                System.out.println("Invalid amount. Please enter a valid number.");
            }
        }
    }

    private LocalDate getDate() {
        while (true) {
            System.out.print("Enter date (yyyy-MM-dd) or press Enter for today: ");
            String dateStr = scanner.nextLine().trim();

            try {
                if (dateStr.isEmpty()) {
                    return LocalDate.now();
                } else {
                    return LocalDate.parse(dateStr, dateFormatter);
                }
            } catch (DateTimeParseException e) {
                System.out.println("Invalid date format. Please use yyyy-MM-dd format.");
            }
        }
    }

    private void viewMonthlySummary() {
        System.out.println("\n----- Monthly Summary -----");

        YearMonth yearMonth = getYearMonth();
        List<Transaction> monthTransactions = service.getTransactionsForMonth(yearMonth);

        if (monthTransactions.isEmpty()) {
            System.out.println("No transactions found for " + yearMonth.getMonth() + " " + yearMonth.getYear());
            return;
        }

        // Display income by category
        System.out.println("\nINCOME BY CATEGORY:");
        Map<Category, Double> incomeByCategory = service.getCategoryTotals(yearMonth, TransactionType.INCOME);
        double totalIncome = 0.0;

        for (Map.Entry<Category, Double> entry : incomeByCategory.entrySet()) {
            System.out.printf("%-15s: $%.2f\n", entry.getKey(), entry.getValue());
            totalIncome += entry.getValue();
        }

        System.out.printf("%-15s: $%.2f\n", "TOTAL INCOME", totalIncome);

        // Display expenses by category
        System.out.println("\nEXPENSES BY CATEGORY:");
        Map<Category, Double> expensesByCategory = service.getCategoryTotals(yearMonth, TransactionType.EXPENSE);
        double totalExpenses = 0.0;

        for (Map.Entry<Category, Double> entry : expensesByCategory.entrySet()) {
            System.out.printf("%-15s: $%.2f\n", entry.getKey(), entry.getValue());
            totalExpenses += entry.getValue();
        }

        System.out.printf("%-15s: $%.2f\n", "TOTAL EXPENSES", totalExpenses);

        // Display balance
        double balance = totalIncome - totalExpenses;
        System.out.println("\nMONTHLY BALANCE:");
        System.out.printf("%-15s: $%.2f\n", "BALANCE", balance);

        // Display all transactions for the month
        System.out.println("\nALL TRANSACTIONS:");
        System.out.printf("%-12s %-10s %-20s %-15s %s\n", "DATE", "AMOUNT", "TYPE", "CATEGORY", "DESCRIPTION");
        System.out.println("-".repeat(80));

        for (Transaction t : monthTransactions) {
            System.out.printf("%-12s $%-9.2f %-20s %-15s %s\n",
                    t.getDate().format(dateFormatter),
                    t.getAmount(),
                    t.getType(),
                    t.getCategory(),
                    t.getDescription());
        }
    }

    private YearMonth getYearMonth() {
        while (true) {
            System.out.print("Enter year and month (yyyy-MM) or press Enter for current month: ");
            String input = scanner.nextLine().trim();

            try {
                if (input.isEmpty()) {
                    return YearMonth.now();
                } else {
                    return YearMonth.parse(input);
                }
            } catch (DateTimeParseException e) {
                System.out.println("Invalid format. Please use yyyy-MM format.");
            }
        }
    }

    private void loadFromFile() {
        System.out.print("Enter file path to load: ");
        String filePath = scanner.nextLine().trim();

        if (!filePath.isEmpty()) {
            try {
                service.loadTransactionsFromFile(filePath);
                System.out.println("Data loaded successfully!");
            } catch (Exception e) {
                System.err.println("Error loading data: " + e.getMessage());
            }
        }
    }

    private void saveToFile() {
        System.out.print("Enter file path to save: ");
        String filePath = scanner.nextLine().trim();

        if (!filePath.isEmpty()) {
            try {
                service.saveTransactionsToFile(filePath);
                System.out.println("Data saved successfully!");
            } catch (Exception e) {
                System.err.println("Error saving data: " + e.getMessage());
            }
        }
    }
}
