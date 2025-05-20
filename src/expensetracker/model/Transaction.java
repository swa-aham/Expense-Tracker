package expensetracker.model;

import java.time.LocalDate;
import java.util.Objects;

public class Transaction {
    private double amount;
    private String description;
    private Category category;
    private TransactionType type;
    private LocalDate date;

    public Transaction(double amount, String description, Category category,
                       TransactionType type, LocalDate date) {
        this.amount = amount;
        this.description = description;
        this.category = category;
        this.type = type;
        this.date = date;
    }

    // Getters and Setters
    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }

    public TransactionType getType() {
        return type;
    }

    public void setType(TransactionType type) {
        this.type = type;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Transaction that = (Transaction) o;
        return Double.compare(amount, that.amount) == 0 &&
                Objects.equals(description, that.description) &&
                category == that.category &&
                type == that.type &&
                Objects.equals(date, that.date);
    }

    @Override
    public int hashCode() {
        return Objects.hash(amount, description, category, type, date);
    }

    @Override
    public String toString() {
        return "Transaction{" +
                "amount=" + amount +
                ", description='" + description + '\'' +
                ", category=" + category +
                ", type=" + type +
                ", date=" + date +
                '}';
    }

    // Format for saving to file
    public String toFileString() {
        return String.format("%s,%f,%s,%s,%s", date, amount, type, category, description);
    }
}
