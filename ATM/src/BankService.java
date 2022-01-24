import java.awt.*;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;

public class BankService {
    private AccountRepository accountRepository;
    private MenuService menu;

    public BankService() {
        this.menu = new MenuService();
    }

    public BankService(String url, String user, String password) {
        this.accountRepository = new AccountRepository(url,user,password);
    }


    // CHECK IF ACCOUNT IS SUCCESSFULLY INSERTED INTO THE DATABASE
    public void checkIfAccountCreated(PreparedStatement preparedStatement) {
        try {
            if (preparedStatement.executeUpdate() == 1) {
                System.out.println("Account was inserted into the DataBase!");
            }
            else {
                System.out.println("Oops, something went wrong!");
                System.out.println("Account was not inserted into the DataBase!");
            }
        }catch(Exception e) {
            e.printStackTrace();
        }
    }

//    public void checkIfAccountIsDeleted(PreparedStatement preparedStatement) {
//        try{
//            if(preparedStatement.executeUpdate() == 1) {
//                System.out.println("Account was successfully deleted from the DataBase!");
//            }
//            else {
//                System.out.println("Oops, something went wrong!");
//                System.out.println("Account was NOT deleted from the DataBase!");
//            }
//        }catch(Exception e) {
//            e.printStackTrace();
//        }
//    }

    public void checkIfAccountIsDeleted(PreparedStatement preparedStatement) {
        try {
            preparedStatement.executeUpdate();
            System.out.println("Account was successfully deleted from the DataBase!");
        }catch(Exception e) {
            e.printStackTrace();
        }
    }

    // CHECKS IN DATABASE IF THERE IS AN ACCOUNT
    public Account checkIfThereIsAccount(String userName, String userPassword, Account account, ResultSet resultSet, Statement statement) {
        try {
            resultSet = statement.executeQuery(userName);
            if(resultSet.next()) {
                resultSet = statement.executeQuery(userPassword);
                if(resultSet.next()) {
                    System.out.println();
                    System.out.println("Successfully logged in!");
                    System.out.println("Welcome, " + account.getUserName());
                    account = setDBtoAcc(account, resultSet);
                }
                else {
                    System.out.println();
                    System.out.println("Wrong user name or password!");
                    System.out.println("ATM will exit automatically!");
                    System.exit(-1);
                }
            }
            else {
                System.out.println();
                System.out.println("Wrong user name or password!");
                System.out.println("ATM will exit automatically!");
                System.exit(-1);
            }
        }catch(Exception e){
            e.printStackTrace();
        }
        return account;
    }


    public Account createAccount(Account acc) {
        if(menu.confirmForAccCreation()) {
            System.out.println("Account has been created successfully!");
            return accountRepository.saveAccount(acc);
        }
        else {
            System.out.println("Account was NOT created!");
        }
        return null;
    }

    public Account logInAccount(Account acc) {
        return accountRepository.logIn(acc);
    }

    // Set the Account from the database to the Account class!
    public Account setDBtoAcc(Account account, ResultSet resultSet) {
        try {
            account.setId(resultSet.getInt("ID"));
            account.setUserName(resultSet.getString("UserName"));
            account.setUserPassword(resultSet.getString("UserPassword"));
            account.setFirstName(resultSet.getString("FirstName"));
            account.setLastName(resultSet.getString("LastName"));
            account.setAddress(resultSet.getString("Address"));
            account.setBalance(resultSet.getDouble("Balance"));
        }catch(Exception e) {
            e.printStackTrace();
        }
        return account;
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

    // ???
    public void depositMoney(Account account, double money) {
        money = money + account.getBalance();
        accountRepository.deposit(account, money);
    }

    public void checkIfSuccessfulDeposit(Account account,PreparedStatement preparedStatement,double money) {
        try {
            if(preparedStatement.executeUpdate() == 1) {
                account.setBalance(money);
                System.out.println("Transaction was successful!");
                System.out.println("Balance after deposit: " + account.getBalance());
            }
            else {
                System.out.println("Deposit failed!");
            }
        }catch(Exception e) {
            e.printStackTrace();
        }
    }
    // ???
    public void withdrawMoney(Account account, double money) {
        if(account.getBalance() - money < 0) {
            System.out.println("Invalid transaction!");
            System.out.println("Not enough money in bank!");
        }
        else {
            money = account.getBalance() - money;
            accountRepository.withdraw(account,money);
        }
    }

    public void checkIfSuccessfulWithdraw(Account account, PreparedStatement preparedStatement, double money) {
        try{
            if(preparedStatement.executeUpdate() == 1) {
                account.setBalance(money);
                System.out.println("Transaction was successful!");
                System.out.println("Balance after withdraw: " + account.getBalance());
            }
            else {
                System.out.println("Withdraw failed!");
            }
        }catch(Exception e) {
            e.printStackTrace();
        }
    }

    public void deleteAccount(Account account) {
        accountRepository.delete(account);
    }
}
