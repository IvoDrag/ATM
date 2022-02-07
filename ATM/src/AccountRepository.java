import java.sql.*;

public class AccountRepository {
    private Connection connection;
    private Statement statement;
    private PreparedStatement preparedStatement;
    private ResultSet resultSet;

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
            preparedStatement.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return account;
    }


    public Account getAccountFromDB(Account account, ResultSet resultSet) throws SQLException {
        while(resultSet.next()) {
            account.setId(resultSet.getInt("ID"));
            account.setUserName(resultSet.getString("UserName"));
            account.setUserPassword(resultSet.getString("UserPassword"));
            account.setFirstName(resultSet.getString("FirstName"));
            account.setLastName(resultSet.getString("LastName"));
            account.setAddress(resultSet.getString("Address"));
            account.setBalance(resultSet.getDouble("Balance"));
        }
        return account;
    }


    public Account logIn(Account account, String inputUserName, String inputUserPassword) {
        try {
            String sqlString = "SELECT * FROM bank_table WHERE UserName=? AND UserPassword=?";
            PreparedStatement login = connection.prepareStatement(sqlString);
            login.setString(1, inputUserName);
            login.setString(2, inputUserPassword);
            resultSet = login.executeQuery();
            account = getAccountFromDB(account,resultSet);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return account;
    }

    public Account printAccountInfo(Account account) {
        try {
            resultSet = statement.executeQuery("SELECT * FROM bank_table WHERE UserName='" + account.getUserName() + "'");
            account = getAccountFromDB(account, resultSet);
        }catch(Exception e) {
            e.printStackTrace();
        }
        return account;
    }

    //                  ??????
    public boolean deposit(Account account, double money) {
        try{
            preparedStatement = connection.prepareStatement("UPDATE bank_table SET Balance = ? WHERE UserName='" + account.getUserName() + "'");
            preparedStatement.setDouble(1, money);
            if(preparedStatement.executeUpdate() == 1) {
                return true;
            }
            else {
                return false;
            }
        }catch(Exception e) {
            e.printStackTrace();
        }
        return false;
    }


    // DONE
    public boolean withdraw(Account account, double money) {
        try {
            preparedStatement = connection.prepareStatement("UPDATE bank_table SET Balance = ? WHERE UserName='" + account.getUserName() + "'");
            preparedStatement.setDouble(1,money);
            if(preparedStatement.executeUpdate() == 1) {
                return true;
            }
            else {
                return false;
            }
        }catch(Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    // NEED TO REPAIR
    public boolean delete(Account account) {
        try{
            preparedStatement = connection.prepareStatement("DELETE FROM bank_table WHERE UserName='" + account.getUserName() + "'");
            if(preparedStatement.executeUpdate() == 1) {
                return true;
            }
            else {
                return false;
            }
        }catch(Exception e) {
            e.printStackTrace();
        }
        return false;
    }

}
