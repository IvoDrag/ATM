import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;


/*

THE ACCOUNT REPOSITORY SHOULD NOT KNOW ANYTHING ABOUT BANK SERVICE

*/




public class BankService {
    private AccountRepository accountRepository;
    private MenuService menu;

    public BankService() {
        this.menu = new MenuService();
    }

    public BankService(String url, String user, String password) {
        this.accountRepository = new AccountRepository(url,user,password);
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

    public Account logInAccount(Account account, String inputUserName, String inputUserPassword) {
        account = accountRepository.logIn(account,inputUserName,inputUserPassword);
        if(account.getUserName() != null) {
            System.out.println();
            System.out.println("Successfully logged in!");
            System.out.println("Welcome " + account.getUserName());
            System.out.println("Balance: " + account.getBalance());
            return account;
        }
        else {
            System.out.println();
            System.out.println("Wrong username or password!");
            return null;
        }
    }

//    // CHECKS IN DATABASE IF THERE IS AN ACCOUNT
//    public Account checkIfThereIsAccount(String userName, String userPassword, Account account, ResultSet resultSet, Statement statement) {
//        try {
//            resultSet = statement.executeQuery(userName);
//            if(resultSet.next()) {
//                resultSet = statement.executeQuery(userPassword);
//                if(resultSet.next()) {
//                    System.out.println();
//                    System.out.println("Successfully logged in!");
//                    System.out.println("Welcome, " + account.getUserName());
//                    account = setDBtoAcc(account, resultSet);
//                }
//                else {
//                    System.out.println();
//                    System.out.println("Wrong user name or password!");
//                    System.out.println("ATM will exit automatically!");
//                    System.exit(-1);
//                }
//            }
//            else {
//                System.out.println();
//                System.out.println("Wrong user name or password!");
//                System.out.println("ATM will exit automatically!");
//                System.exit(-1);
//            }
//        }catch(Exception e){
//            e.printStackTrace();
//        }
//        return account;
//    }


    public Account createAccount(Account acc) {
        menu = new MenuService();
        if(menu.confirmForAccCreation()) {
            System.out.println("Account has been created successfully!");
            return accountRepository.saveAccount(acc);
        }
        else {
            System.out.println("Account was NOT created!");
        }
        return null;
    }


//    public void logInAccount(Account acc) {
//        if (checkIfThereIsAccount(acc.getUserName(), acc)){
//
//        }
//        else {
//            System.out.println("Wrong username or password!");
//        }
//    }

    // Set the Account from the database to the Account class!
/*    public Account setDBtoAcc(Account account, ResultSet resultSet) {
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
    }*/

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
        if(accountRepository.deposit(account, money)) {
            System.out.println("Transaction was successful!");
            System.out.println("Balance after deposit: " + money);
        }
        else {
            System.out.println("Deposit failed!");
        }
    }

//    public void checkIfSuccessfulDeposit(Account account,PreparedStatement preparedStatement,double money) {
//        try {
//            if(preparedStatement.executeUpdate() == 1) {
//                account.setBalance(money);
//                System.out.println("Transaction was successful!");
//                System.out.println("Balance after deposit: " + account.getBalance());
//            }
//            else {
//                System.out.println("Deposit failed!");
//            }
//        }catch(Exception e) {
//            e.printStackTrace();
//        }
//    }





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
