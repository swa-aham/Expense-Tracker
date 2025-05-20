package expensetracker.service;

import expensetracker.model.Category;
import expensetracker.model.Transaction;
import expensetracker.model.TransactionType;

import java.time.LocalDate;
import java.time.Month;
import java.time.YearMonth;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class ExpenseTrackerService {
    private List<Transaction> transactions;
    private FileService fileService;

    public ExpenseTrackerService() {
        this.transactions = new ArrayList<>();
        this.fileService = new FileService();
    }

    public void addTransaction(Transaction transaction) {
        transactions.add(transaction);
    }

    public List<Transaction> getAllTransactions() {
        return new ArrayList<>(transactions);
    }

    public void loadTransactionsFromFile(String filePath) {
        transactions = fileService.loadTransactions(filePath);
    }

    public void saveTransactionsToFile(String filePath) {
        fileService.saveTransactions(filePath, transactions);
    }

    public Map<Month, Double> getMonthlyIncome(int year) {
        Map<Month, Double> monthlyIncome = new HashMap<>();

        for (Transaction transaction : transactions) {
            if (transaction.getType() == TransactionType.INCOME &&
                    transaction.getDate().getYear() == year) {

                Month month = transaction.getDate().getMonth();
                Double currentTotal = monthlyIncome.getOrDefault(month, 0.0);
                monthlyIncome.put(month, currentTotal + transaction.getAmount());
            }
        }

        return monthlyIncome;
    }

    public Map<Month, Double> getMonthlyExpense(int year) {
        Map<Month, Double> monthlyExpense = new HashMap<>();

        for (Transaction transaction : transactions) {
            if (transaction.getType() == TransactionType.EXPENSE &&
                    transaction.getDate().getYear() == year) {

                Month month = transaction.getDate().getMonth();
                Double currentTotal = monthlyExpense.getOrDefault(month, 0.0);
                monthlyExpense.put(month, currentTotal + transaction.getAmount());
            }
        }

        return monthlyExpense;
    }

    public Map<Category, Double> getCategoryTotals(YearMonth yearMonth, TransactionType type) {
        Map<Category, Double> categoryTotals = new HashMap<>();

        for (Transaction transaction : transactions) {
            if (transaction.getType() == type &&
                    transaction.getDate().getYear() == yearMonth.getYear() &&
                    transaction.getDate().getMonth() == yearMonth.getMonth()) {

                Category category = transaction.getCategory();
                Double currentTotal = categoryTotals.getOrDefault(category, 0.0);
                categoryTotals.put(category, currentTotal + transaction.getAmount());
            }
        }

        return categoryTotals;
    }

    public double getMonthlyBalance(YearMonth yearMonth) {
        double income = 0.0;
        double expense = 0.0;

        for (Transaction transaction : transactions) {
            if (transaction.getDate().getYear() == yearMonth.getYear() &&
                    transaction.getDate().getMonth() == yearMonth.getMonth()) {

                if (transaction.getType() == TransactionType.INCOME) {
                    income += transaction.getAmount();
                } else {
                    expense += transaction.getAmount();
                }
            }
        }

        return income - expense;
    }

    public List<Transaction> getTransactionsForMonth(YearMonth yearMonth) {
        return transactions.stream()
                .filter(t -> t.getDate().getYear() == yearMonth.getYear() &&
                        t.getDate().getMonth() == yearMonth.getMonth())
                .collect(Collectors.toList());
    }
}
