import java.sql.*;

public class AccountRepository {
    private Connection connection;
    private Statement statement;
    private PreparedStatement preparedStatement;
    private ResultSet resultSet;
    private BankService bankService;

    public AccountRepository() {
        this.bankService = new BankService();
    }

    public AccountRepository(String url, String root, String password) {
        this.connection = establishConnection(url,root,password);
    }

    private Connection establishConnection(String url, String root, String password) {
        try {
            this.connection = DriverManager.getConnection(url, root, password);
            statement = connection.createStatement();
        }catch (Exception e) {
            e.printStackTrace();
        }
        return connection;
    }

    public Account saveAccount(Account account) {
        try {
            preparedStatement = connection.prepareStatement("INSERT INTO bank_table(UserName, UserPassword, FirstName, LastName, Address, Balance) VALUES(?,?,?,?,?,?)");
            preparedStatement.setString(1, account.getUserName());
            preparedStatement.setString(2, account.getUserPassword());
            preparedStatement.setString(3, account.getFirstName());
            preparedStatement.setString(4, account.getLastName());
            preparedStatement.setString(5, account.getAddress());
            preparedStatement.setDouble(6, account.getBalance());
            bankService.checkIfAccountCreated(preparedStatement);
        }catch(Exception e) {
            e.printStackTrace();
        }
        return account;
    }

    public Account logIn(Account account) {
        bankService = new BankService();
        try {
            String userNameSearch = "SELECT * FROM bank_table WHERE UserName='" + account.getUserName() + "'";
            String userPasswordSearch = "SELECT * FROM bank_table WHERE UserPassword='" + account.getUserPassword() + "'";
            account = bankService.checkIfThereIsAccount(userNameSearch,userPasswordSearch,account,resultSet,statement);
        }catch(Exception e) {
            e.printStackTrace();
        }
        return account;
    }

    public Account printAccountInfo(Account account) {
        try {
            resultSet = statement.executeQuery("SELECT * FROM bank_table WHERE UserName='" + account.getUserName() + "'");
            while(resultSet.next()) {
                account.setId(resultSet.getInt("ID"));
                account.setUserName(resultSet.getString("UserName"));
                account.setUserPassword(resultSet.getString("UserPassword"));
                account.setFirstName(resultSet.getString("FirstName"));
                account.setLastName(resultSet.getString("LastName"));
                account.setAddress(resultSet.getString("Address"));
                account.setBalance(resultSet.getDouble("Balance"));
            }
        }catch(Exception e) {
            e.printStackTrace();
        }
        return account;
    }

    public void deposit(Account account, double money) {
        try{
            preparedStatement = connection.prepareStatement("UPDATE bank_table SET Balance = ? WHERE UserName='" + account.getUserName() + "'");
            preparedStatement.setDouble(1, money);
            bankService.checkIfSuccessfulDeposit(account,preparedStatement,money);
        }catch(Exception e) {
            e.printStackTrace();
        }
    }

    public void withdraw(Account account, double money) {
        try {
            preparedStatement = connection.prepareStatement("UPDATE bank_table SET Balance = ? WHERE UserName='" + account.getUserName() + "'");
            preparedStatement.setDouble(1,money);
            bankService.checkIfSuccessfulWithdraw(account,preparedStatement,money);
        }catch(Exception e) {
            e.printStackTrace();
        }
    }

    public void delete(Account account) {
        try{
            preparedStatement = connection.prepareStatement("DELETE FROM bank_table WHERE UserName='" + account.getUserName() + "'");
            bankService.checkIfAccountIsDeleted(preparedStatement);
        }catch(Exception e) {
            e.printStackTrace();
        }
    }

}
