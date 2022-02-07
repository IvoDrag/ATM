public class BankService {
    private AccountRepository accountRepository;
    private MenuService menu;

    public BankService(String url, String user, String password) {
        this.accountRepository = new AccountRepository(url, user, password);
    }

    public Account logInAccount(Account account, String inputUserName, String inputUserPassword) {
        account = accountRepository.logIn(account, inputUserName, inputUserPassword);
        if (account.getUserName() != null) {
            System.out.println();
            System.out.println("Successfully logged in!");
            System.out.println("Welcome " + account.getUserName());
            System.out.println("Balance: " + account.getBalance());
            return account;
        } else {
            System.out.println();
            System.out.println("Wrong username or password!");
            return null;
        }
    }

    public Account createAccount(Account acc) {
        menu = new MenuService();
        if (menu.confirmForAccCreation()) {
            System.out.println("Account has been created successfully!");
            return accountRepository.saveAccount(acc);
        } else {
            System.out.println("Account was NOT created!");
        }
        return null;
    }

    public void accountInfo(Account account) {
        Account acc = accountRepository.printAccountInfo(account);
        System.out.println("ID: " + acc.getId());
        System.out.println("User: " + acc.getUserName());
        System.out.println("First name: " + acc.getFirstName());
        System.out.println("Last name: " + acc.getLastName());
        System.out.println("Address: " + acc.getAddress());
        System.out.println("Balance: " + acc.getBalance());
    }

    public void depositMoney(Account account, double money) {
        money = money + account.getBalance();
        if (accountRepository.deposit(account, money)) {
            System.out.println("Transaction was successful!");
            System.out.println("Balance after deposit: " + money);
        } else {
            System.out.println("Deposit failed!");
        }
    }

    public void withdrawMoney(Account account, double money) {
        if (account.getBalance() - money < 0) {
            System.out.println("Invalid transaction!");
            System.out.println("Not enough money in bank!");
        } else {
            money = account.getBalance() - money;
            if (accountRepository.withdraw(account, money)) {
                System.out.println("Withdraw was successful!");
                System.out.println("Balance after transaction: " + money);
            } else {
                System.out.println("Something went wrong! No transaction was made!");
            }
        }
    }

    public void deleteAccount(Account account) {
        if (accountRepository.delete(account)) {
            System.out.println("Account was successfully deleted!");
        } else {
            System.out.println("Account WAS NOT deleted!");
        }
    }
}
