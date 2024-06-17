import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

class User {
    private int userId;
    private int userPin;
    private double balance;

    public User(int userId, int userPin, double balance) {
        this.userId = userId;
        this.userPin = userPin;
        this.balance = balance;
    }

    public int getUserId() {
        return userId;
    }

    public int getUserPin() {
        return userPin;
    }

    public double getBalance() {
        return balance;
    }

    public void setBalance(double balance) {
        this.balance = balance;
    }
}

class Transaction {
    private String type;
    private double amount;

    public Transaction(String type, double amount) {
        this.type = type;
        this.amount = amount;
    }

    public String getType() {
        return type;
    }

    public double getAmount() {
        return amount;
    }
}

class TransactionHistory {
    private List<Transaction> transactions;

    public TransactionHistory() {
        this.transactions = new ArrayList<>();
    }

    public void addTransaction(Transaction transaction) {
        transactions.add(transaction);
    }

    public void displayTransactionHistory() {
        System.out.println("Transaction History:");
        for (Transaction transaction : transactions) {
            System.out.println("Type: " + transaction.getType() + ", Amount: " + transaction.getAmount());
        }
    }
}

class ATM {
    private User user;
    private TransactionHistory transactionHistory;

    public ATM(User user) {
        this.user = user;
        this.transactionHistory = new TransactionHistory();
    }

    public void displayMenu() {
        System.out.println("ATM Menu:");
        System.out.println("1. Withdraw Money");
        System.out.println("2. Deposit Money");
        System.out.println("3. Transfer Money");
        System.out.println("4. Transaction History");
        System.out.println("5. Quit");
    }

    public void withdrawMoney(double amount) {
        if (amount <= user.getBalance()) {
            user.setBalance(user.getBalance() - amount);
            Transaction transaction = new Transaction("Withdrawal", amount);
            transactionHistory.addTransaction(transaction);
            System.out.println("Withdrawal successful. New balance: " + user.getBalance());
        } else {
            System.out.println("Insufficient funds.");
        }
    }

    public void depositMoney(double amount) {
        user.setBalance(user.getBalance() + amount);
        Transaction transaction = new Transaction("Deposit", amount);
        transactionHistory.addTransaction(transaction);
        System.out.println("Deposit successful. New balance: " + user.getBalance());
    }

    public void transferMoney(double amount, int recipientUserId) {
        if (amount <= user.getBalance()) {
            user.setBalance(user.getBalance() - amount);
            Transaction transaction = new Transaction("Transfer", amount);
            transactionHistory.addTransaction(transaction);
            System.out.println("Transfer successful. New balance: " + user.getBalance());
        } else {
            System.out.println("Insufficient funds.");
        }
    }

    public void displayTransactionHistory() {
        transactionHistory.displayTransactionHistory();
    }
}

public class Main {
    public static void main(String[] args) {
        User user = new User(1234, 1234, 10000.0);
        ATM atm = new ATM(user);

        Scanner scanner = new Scanner(System.in);

        System.out.print("Enter user ID: ");
        int userId = scanner.nextInt();
        System.out.print("Enter user PIN: ");
        int userPin = scanner.nextInt();

        if (userId == user.getUserId() && userPin == user.getUserPin()) {
            atm.displayMenu();
            int choice = scanner.nextInt();

            while (choice != 5) {
                switch (choice) {
                    case 1:
                        System.out.print("Enter amount to withdraw: ");
                        double withdrawAmount = scanner.nextDouble();
                        atm.withdrawMoney(withdrawAmount);
                        break;
                    case 2:
                        System.out.print("Enter amount to deposit: ");
                        double depositAmount = scanner.nextDouble();
                        atm.depositMoney(depositAmount);
                        break;
                    case 3:
                        System.out.print("Enter recipient user ID: ");
                        int recipientUserId = scanner.nextInt();
                        System.out.print("Enter amount to transfer: ");
                        double transferAmount = scanner.nextDouble();
                        User recipient = new User(recipientUserId, 1234, 10000.0);
                        atm.transferMoney(transferAmount, recipientUserId);
                        break;
                    case 4:
                        atm.displayTransactionHistory();
                        break;
                    default:
                        System.out.println("Invalid choice.");
                }

                atm.displayMenu();
                choice = scanner.nextInt();
            }
        } else {
            System.out.println("Invalid user ID or PIN.");
        }

        scanner.close();
    }
}