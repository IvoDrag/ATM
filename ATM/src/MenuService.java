import java.sql.SQLOutput;
import java.util.Locale;
import java.util.Scanner;

public class MenuService {
    private final Scanner scan = new Scanner(System.in);
    private BankService bankService;
    private Account account;

    public MenuService() {}

    public MenuService(BankService bankService) {
        this.bankService = bankService;
    }

    public Account userInputForCreateAcc() {
        System.out.print("Enter User name: ");
        String user = scan.nextLine();
        System.out.print("Enter password: ");
        String pass = scan.nextLine();
        System.out.print("Enter First name: ");
        String firstname = scan.nextLine();
        System.out.print("Enter Last name: ");
        String lastname = scan.nextLine();
        System.out.print("Enter Address: ");
        String address = scan.nextLine();
        return new Account(user,pass,firstname,lastname,address,0);
    }

    public Account userLogIn() {
        System.out.print("Enter your user name: ");
        String user = scan.nextLine();
        account.setUserName(user);
        System.out.print("Enter your password: ");
        String pass = scan.nextLine();
        account.setUserPassword(pass);
        return new Account(user,pass);
    }

    public boolean confirmForAccCreation() {
        System.out.println("Are you sure that you want to create this Account? (yes/no)");
        String choice = scan.nextLine();
        choice = choice.toLowerCase(Locale.ROOT);
        switch(choice) {
            case "yes":
                return true;
            case "no":
                return false;
        }
        return false;
    }

    private void firstMenuOptions() {
        System.out.println();
        System.out.println("1. Create an Account");
        System.out.println("2. Log in existing Account");
        System.out.println("0. Exit");

        System.out.println();
    }

    public void accountOptions() {
        System.out.println();
        System.out.println("1. Account information");
        System.out.println("2. Deposit money");
        System.out.println("3. Withdraw money");
        System.out.println("4. Delete account!");
        System.out.println("5. Exit from account");
        System.out.println();
    }

    public boolean optionDelete() {
        String option;
        boolean flag = true;
        System.out.println("Are you sure you want to delete your Account: " + account.getUserName() + "? (yes/no)?");
        do {
            option = scan.nextLine();
            option = option.toLowerCase(Locale.ROOT);
            if(option.equals("yes")) {
                return true;
            }
            else if(option.equals("no")) {
                System.out.println("Account will NOT be deleted!");
                return false;
            }
            else {
                System.out.println("Invalid choice!" + '\n' + "Enter (yes/no)!");
            }
        }while(true);
    }

    public double depositFunction(Account account, double money) {
        System.out.println();
        System.out.println("Balance now: " + account.getBalance());
        System.out.println("How much money do you to deposit: ");
        money = Double.parseDouble(scan.nextLine());
        return money;
    }

    public double withDrawFunction(Account account, double money) {
        System.out.println();
        System.out.println("Balance now: " + account.getBalance());
        System.out.println("How much money do you want to withdraw: ");
        money = Double.parseDouble(scan.nextLine());
        return money;
    }

    public void start() {
        account = new Account();
        boolean flagLevelOne = true;
        boolean flagLevelTwo = true;
        int inputOptionLevelOne;
        double depositValue = 0.0;
        double withdrawValue = 0.0;
        int inputOptionLevelTwo;
        System.out.println("----------------------- ATM -----------------------");
        do {
            firstMenuOptions();
            System.out.print("Choose option: ");
            inputOptionLevelOne = Integer.parseInt(scan.nextLine());
            switch (inputOptionLevelOne) {
                case 1:
                    bankService.createAccount(userInputForCreateAcc());
                    break;
                case 2:
                    // Log in Account!
                    account = bankService.logInAccount(userLogIn());
                    do {
                        accountOptions();
                        System.out.print("Choose option: ");
                        inputOptionLevelTwo = Integer.parseInt(scan.nextLine());
                        switch (inputOptionLevelTwo) {
                            case 1:
                                bankService.accountInfo(account);
                                // Account information!
                                break;
                            case 2:
                                // Deposit money!
                                depositValue = depositFunction(account, depositValue);
                                bankService.depositMoney(account,depositValue);
                                break;
                            case 3:
                                // Withdraw money!
                                withdrawValue = withDrawFunction(account, withdrawValue);
                                bankService.withdrawMoney(account, withdrawValue);
                                break;
                            case 4:
                                // Delete account!
                                if(optionDelete()) {
                                    bankService.deleteAccount(account);
                                }
                                flagLevelTwo = false;
                                break;
                            case 5:
                                // Exit from account!
                                flagLevelTwo = false;
                                break;
                        }
                    }while(flagLevelTwo);
                    break;
                case 0:
                    System.out.println("Thank you for using ATM!");
                    flagLevelOne = false;
                    break;
            }
        }while(flagLevelOne);
    }
}
