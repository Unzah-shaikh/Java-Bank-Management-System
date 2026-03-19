import java.util.*;

/**
 * ---------------------------------------------
 * JAVA BANK MANAGEMENT SYSTEM (CONSOLE BASED)
 * ---------------------------------------------
 * Features:
 * - User Registration & Login
 * - Bank Account Creation
 * - Debit / Credit Money
 * - Balance Checking
 * - Money Transfer
 * - Transaction History
 * - Loan Requests
 * - Customer Support Simulation
 * - View All Account Balances
 *
 * Note:
 * This is a beginner-friendly project using Java Collections.
 */

public class Main {

    // -------------------- DATA STORAGE --------------------

    // Stores user email and password
    private static Map<String, String> registeredEmails = new HashMap<>();

    // Stores email -> account number
    private static Map<String, String> accountNumbers = new HashMap<>();

    // Stores email -> account balance
    private static Map<String, Integer> accountBalances = new HashMap<>();

    // Stores email -> transaction history list
    private static Map<String, List<String>> transactionHistory = new HashMap<>();

    // Stores email -> loan request amount
    private static Map<String, Integer> loanRequests = new HashMap<>();

    private static Random random = new Random();

    // -------------------- EMAIL VALIDATION --------------------

    // Takes valid email input from user
    private static String getEmail(Scanner s, String prompt) {
        while (true) {
            System.out.println(prompt);
            String email = s.nextLine();

            if (isValidEmail(email)) {
                return email;
            } else {
                System.out.println("Invalid email. Please try again.");
            }
        }
    }

    // Checks email format using regex
    private static boolean isValidEmail(String email) {
        String emailRegex = "^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,6}$";
        return email.matches(emailRegex);
    }

    // -------------------- MAIN PROGRAM --------------------

    public static void main(String[] args) {

        Scanner s = new Scanner(System.in);

        while (true) {

            // ----------- MAIN MENU -----------
            System.out.println("\n=== EASY, FAST, AND SECURE BANKING ===");
            System.out.println("1. Register");
            System.out.println("2. Login");
            System.out.println("3. Exit");
            System.out.print("Enter your choice: ");

            int choice = s.nextInt();
            s.nextLine();

            // ----------- REGISTER -----------
            if (choice == 1) {

                System.out.print("Enter Full Name: ");
                String name = s.nextLine();

                String email = getEmail(s, "Enter Email:");

                System.out.print("Enter Password: ");
                String password = s.nextLine();

                if (registeredEmails.containsKey(email)) {
                    System.out.println("User already exists!");
                } else {
                    registeredEmails.put(email, password);
                    System.out.println("Registration Successful!");
                }
            }

            // ----------- LOGIN -----------
            else if (choice == 2) {

                String email = getEmail(s, "Enter Email:");

                System.out.print("Enter Password: ");
                String password = s.nextLine();

                if (registeredEmails.containsKey(email) && registeredEmails.get(email).equals(password)) {

                    System.out.println("Login Successful!");

                    System.out.println("1. Open Bank Account");
                    System.out.println("2. Exit");

                    int opt = s.nextInt();
                    s.nextLine();

                    if (opt == 1) {

                        // -------- ACCOUNT CREATION --------
                        System.out.print("Enter Full Name: ");
                        String fullName = s.nextLine();

                        System.out.print("Enter Initial Balance: ");
                        int balance = s.nextInt();
                        s.nextLine();

                        System.out.print("Set Security PIN: ");
                        String pin = s.nextLine();

                        String accountNo = generateRandomAccountNumber();

                        accountNumbers.put(email, accountNo);
                        accountBalances.put(email, balance);
                        transactionHistory.put(email, new ArrayList<>());

                        System.out.println("Account Created!");
                        System.out.println("Account Number: " + accountNo);

                        // -------- USER DASHBOARD --------
                        while (true) {

                            System.out.println("\n--- MENU ---");
                            System.out.println("1. Debit Money");
                            System.out.println("2. Credit Money");
                            System.out.println("3. Check Balance");
                            System.out.println("4. Transfer Money");
                            System.out.println("5. Transaction History");
                            System.out.println("6. Customer Support");
                            System.out.println("7. Apply for Loan");
                            System.out.println("8. View All Accounts");
                            System.out.println("9. Logout");

                            int ch = s.nextInt();
                            s.nextLine();

                            // -------- DEBIT --------
                            if (ch == 1) {
                                System.out.print("Amount: ");
                                int amount = s.nextInt();
                                s.nextLine();

                                System.out.print("Enter PIN: ");
                                String inputPin = s.nextLine();

                                if (inputPin.equals(pin)) {
                                    int currentBalance = accountBalances.get(email);

                                    if (amount > currentBalance) {
                                        System.out.println("Insufficient Balance!");
                                    } else {
                                        accountBalances.put(email, currentBalance - amount);
                                        transactionHistory.get(email).add("Debited: " + amount);
                                        System.out.println("Money Debited!");
                                    }
                                } else {
                                    System.out.println("Wrong PIN!");
                                }
                            }

                            // -------- CREDIT --------
                            else if (ch == 2) {
                                System.out.print("Amount: ");
                                int amount = s.nextInt();
                                s.nextLine();

                                System.out.print("Enter PIN: ");
                                String inputPin = s.nextLine();

                                if (inputPin.equals(pin)) {
                                    accountBalances.put(email, accountBalances.get(email) + amount);
                                    transactionHistory.get(email).add("Credited: " + amount);
                                    System.out.println("Money Credited!");
                                } else {
                                    System.out.println("Wrong PIN!");
                                }
                            }

                            // -------- CHECK BALANCE --------
                            else if (ch == 3) {
                                System.out.print("Enter PIN: ");
                                String inputPin = s.nextLine();

                                if (inputPin.equals(pin)) {
                                    System.out.println("Balance: " + accountBalances.get(email));
                                } else {
                                    System.out.println("Wrong PIN!");
                                }
                            }

                            // -------- TRANSFER --------
                            else if (ch == 4) {
                                System.out.print("Receiver Account No: ");
                                String receiverAcc = s.nextLine();

                                System.out.print("Amount: ");
                                int amount = s.nextInt();
                                s.nextLine();

                                System.out.print("Enter PIN: ");
                                String inputPin = s.nextLine();

                                if (inputPin.equals(pin)) {

                                    if (accountNumbers.containsValue(receiverAcc)) {

                                        if (accountBalances.get(email) < amount) {
                                            System.out.println("Insufficient Balance!");
                                        } else {

                                            String receiverEmail = "";

                                            for (Map.Entry<String, String> entry : accountNumbers.entrySet()) {
                                                if (entry.getValue().equals(receiverAcc)) {
                                                    receiverEmail = entry.getKey();
                                                    break;
                                                }
                                            }

                                            accountBalances.put(email, accountBalances.get(email) - amount);
                                            accountBalances.put(receiverEmail, accountBalances.get(receiverEmail) + amount);

                                            transactionHistory.get(email).add("Sent: " + amount);
                                            transactionHistory.get(receiverEmail).add("Received: " + amount);

                                            System.out.println("Transfer Successful!");
                                        }
                                    } else {
                                        System.out.println("Account not found!");
                                    }
                                } else {
                                    System.out.println("Wrong PIN!");
                                }
                            }

                            // -------- TRANSACTION HISTORY --------
                            else if (ch == 5) {
                                System.out.println("--- Transaction History ---");

                                List<String> history = transactionHistory.get(email);

                                if (history.isEmpty()) {
                                    System.out.println("No transactions yet.");
                                } else {
                                    for (String record : history) {
                                        System.out.println(record);
                                    }
                                }
                            }

                            // -------- CUSTOMER SUPPORT --------
                            else if (ch == 6) {
                                System.out.print("Describe your issue: ");
                                String issue = s.nextLine();
                                System.out.println("Support request submitted!");
                            }

                            // -------- LOAN --------
                            else if (ch == 7) {
                                System.out.print("Loan Amount: ");
                                int loan = s.nextInt();
                                s.nextLine();

                                if (loan <= accountBalances.get(email) * 5) {
                                    loanRequests.put(email, loan);
                                    System.out.println("Loan request submitted!");
                                } else {
                                    System.out.println("Loan denied!");
                                }
                            }

                            // -------- VIEW ALL ACCOUNTS --------
                            else if (ch == 8) {
                                System.out.println("--- All Accounts ---");

                                for (Map.Entry<String, Integer> entry : accountBalances.entrySet()) {
                                    System.out.println(entry.getKey() + " | Balance: " + entry.getValue());
                                }
                            }

                            // -------- LOGOUT --------
                            else if (ch == 9) {
                                System.out.println("Logged out!");
                                break;
                            }
                        }
                    }
                } else {
                    System.out.println("Invalid credentials!");
                }
            }

            // -------- EXIT --------
            else if (choice == 3) {
                System.out.println("Thank you for using the system!");
                break;
            }
        }

        s.close();
    }

    // -------------------- ACCOUNT NUMBER GENERATOR --------------------

    private static String generateRandomAccountNumber() {
        return String.format("%012d", random.nextInt(1000000000));
    }
}
