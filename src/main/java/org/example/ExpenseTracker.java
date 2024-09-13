package org.example;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import java.util.*;

public class ExpenseTracker
{
    private Scanner scanner;
    private Map<String, List<Double>> expenses;

    public void setScanner(Scanner scanner)
    {
        this.scanner = scanner;
    }

    public void setExpenses(Map<String, List<Double>> expenses)
    {
        this.expenses = expenses;
    }

    public static void main(String[] args)
    {
        ApplicationContext context = new ClassPathXmlApplicationContext("config.xml");
        ExpenseTracker expenseTracker = (ExpenseTracker) context.getBean("expenseTracker");
        expenseTracker.run();
    }

    public void run() {
        while (true)
        {
            System.out.println("Expense Tracker Menu:");
            System.out.println("1. Record an expense");
            System.out.println("2. View expenses by category");
            System.out.println("3. Calculate total expenses by category");
            System.out.println("4. Exit");
            System.out.print("Enter your choice: ");
            int choice = scanner.nextInt();

            //use the scanner class to take input from the user
            scanner.nextLine();

            switch (choice) {
                case 1:
                    recordExpense(scanner, expenses);
                    break;
                case 2:
                    viewExpensesByCategory(expenses);
                    break;
                case 3:
                    calculateTotalExpensesByCategory(expenses);
                    break;
                case 4:
                    System.out.println("Exiting Expense Tracker. Goodbye!");
                    //close scanner to avoid data leak
                    scanner.close();
                    System.exit(0);
                default:
                    System.out.println("Invalid choice. Please select a valid option.");
            }
        }
    }

    private static void recordExpense(Scanner scanner, Map<String, List<Double>> expenses) {
        System.out.print("Enter the expense amount: ");
        double amount = scanner.nextDouble();
        scanner.nextLine();

        System.out.print("Enter the category: ");
        String category = scanner.nextLine();

        expenses.computeIfAbsent(category, k -> new ArrayList<>()).add(amount);
        System.out.println("Expense recorded.");
    }

    private static void viewExpensesByCategory(Map<String, List<Double>> expenses) {
        System.out.println("Expenses by Category:");
        for (Map.Entry<String, List<Double>> entry : expenses.entrySet()) {
            String category = entry.getKey();
            List<Double> categoryExpenses = entry.getValue();
            System.out.println(category + ": " + categoryExpenses);
        }
    }

    private static void calculateTotalExpensesByCategory(Map<String, List<Double>> expenses) {
        System.out.println("Total Expenses by Category:");
        for (Map.Entry<String, List<Double>> entry : expenses.entrySet()) {
            String category = entry.getKey();
            List<Double> categoryExpenses = entry.getValue();
            double total = categoryExpenses.stream().mapToDouble(Double::doubleValue).sum();
            System.out.println(category + ": " + total);
        }
    }
}
