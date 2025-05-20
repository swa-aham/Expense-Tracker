# Java Expense Tracker

A simple, interactive Expense Tracker application built in Java. This project allows users to add income and expenses, categorize transactions, view monthly summaries, and save/load data from CSV files.

## Features

- **Add Transactions:** Record both income and expenses.
- **Categorization:** Assign transactions to categories (e.g., Food, Rent, Travel, Salary, Business, etc.).
- **Monthly Summary:** View a detailed summary of income, expenses, and balance for any month.
- **File Import/Export:** Load transactions from a CSV file and save your data for future use.
- **User-Friendly Console UI:** Simple prompts guide you through all actions.

## Getting Started

### Prerequisites

- Java 17 or higher (tested with OpenJDK 22)
- (Optional) An IDE like IntelliJ IDEA or VS Code

### Running the Application

1. **Clone the repository:**
   ```sh
   git clone https://github.com/swa-aham/Expense-Tracker.git
   cd Expense-Tracker
   ```

2. **Compile & Run the application:**

   _Run the Main.java class_

### File Format

CSV files should have the following columns:
```
Date,Amount,Type,Category,Description
2025-05-01,5000.00,INCOME,SALARY,Monthly Salary
2025-05-02,100.50,EXPENSE,FOOD,Grocery shopping
...
```

## Screenshots of the program execution

- Adding transactions
  
![image](https://github.com/user-attachments/assets/fd9a4b07-2c2c-48fc-8c55-6cc99588e4a5)
![image](https://github.com/user-attachments/assets/a9e91ce4-7d7d-4a3e-9dc6-0bdc0290e674)

- Viewing monthly summary
  
![image](https://github.com/user-attachments/assets/633ebee6-396f-4e15-bcdd-cadd94ae32f6)

- Loading data from the file
  
![image](https://github.com/user-attachments/assets/ee618767-7d81-46a0-97de-6744136ba2a6)
![image](https://github.com/user-attachments/assets/5c0a32d0-419f-4a5a-b945-6499f2a960e3)

- Adding data to the file

![image](https://github.com/user-attachments/assets/371e3474-b2f2-4231-b101-a4ff43181a51)
![image](https://github.com/user-attachments/assets/6d4bf53c-cb86-4add-938b-c3421ddbda2e)

- Exit

![image](https://github.com/user-attachments/assets/8c04d999-d694-40b9-b8d9-6021b4b51852)


## Project Structure

- `src/expensetracker/model/` - Data models (Transaction, Category, TransactionType)
- `src/expensetracker/service/` - Business logic and file operations
- `src/expensetracker/ui/` - Console-based user interface
- `sample-data.csv` - Example data file

## Customization

- Easily add new categories by editing the `Category` enum.
- Modify or extend file formats as needed.
