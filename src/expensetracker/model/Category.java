package expensetracker.model;

public enum Category {
    // Income categories
    SALARY,
    BUSINESS,
    OTHER_INCOME,

    // Expense categories
    FOOD,
    RENT,
    TRAVEL,
    UTILITIES,
    ENTERTAINMENT,
    OTHER_EXPENSE;

    public static Category[] getIncomeCategories() {
        return new Category[]{SALARY, BUSINESS, OTHER_INCOME};
    }

    public static Category[] getExpenseCategories() {
        return new Category[]{FOOD, RENT, TRAVEL, UTILITIES, ENTERTAINMENT, OTHER_EXPENSE};
    }

    @Override
    public String toString() {
        return name().charAt(0) + name().substring(1).toLowerCase().replace('_', ' ');
    }
}
