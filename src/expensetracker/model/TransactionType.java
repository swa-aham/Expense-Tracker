package expensetracker.model;

public enum TransactionType {
    INCOME,
    EXPENSE;

    @Override
    public String toString() {
        return name().charAt(0) + name().substring(1).toLowerCase();
    }
}