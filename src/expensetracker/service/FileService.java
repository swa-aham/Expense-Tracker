package expensetracker.service;

import expensetracker.model.Category;
import expensetracker.model.Transaction;
import expensetracker.model.TransactionType;

import java.io.*;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

public class FileService {
    private static final String CSV_SEPARATOR = ",";
    private static final DateTimeFormatter DATE_FORMATTER = DateTimeFormatter.ISO_LOCAL_DATE;

    public List<Transaction> loadTransactions(String filePath) {
        List<Transaction> transactions = new ArrayList<>();

        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
            String line;
            // Skip header if it exists
            if ((line = reader.readLine()) != null && line.startsWith("Date")) {
                // This is a header, skip it
            }

            while ((line = reader.readLine()) != null) {
                try {
                    Transaction transaction = parseTransaction(line);
                    if (transaction != null) {
                        transactions.add(transaction);
                    }
                } catch (Exception e) {
                    System.err.println("Error parsing line: " + line);
                    e.printStackTrace();
                }
            }
        } catch (IOException e) {
            System.err.println("Error reading file: " + filePath);
            e.printStackTrace();
        }

        return transactions;
    }

    public void saveTransactions(String filePath, List<Transaction> transactions) {
        try (PrintWriter writer = new PrintWriter(new FileWriter(filePath))) {
            // Write header
            writer.println("Date,Amount,Type,Category,Description");

            // Write transactions
            for (Transaction transaction : transactions) {
                writer.println(transaction.toFileString());
            }
        } catch (IOException e) {
            System.err.println("Error writing to file: " + filePath);
            e.printStackTrace();
        }
    }

    private Transaction parseTransaction(String line) {
        String[] parts = line.split(CSV_SEPARATOR);

        if (parts.length < 5) {
            System.err.println("Invalid line format: " + line);
            return null;
        }

        try {
            LocalDate date = LocalDate.parse(parts[0], DATE_FORMATTER);
            double amount = Double.parseDouble(parts[1]);
            TransactionType type = TransactionType.valueOf(parts[2].toUpperCase());
            Category category = Category.valueOf(parts[3].toUpperCase().replace(' ', '_'));
            String description = parts[4];

            return new Transaction(amount, description, category, type, date);
        } catch (Exception e) {
            System.err.println("Error parsing transaction: " + line);
            e.printStackTrace();
            return null;
        }
    }
}
