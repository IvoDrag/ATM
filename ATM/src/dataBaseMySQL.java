package Projects.ATM;

import java.sql.*;
import java.util.Scanner;

public class dataBaseMySQL extends Account {
    private final String url = "jdbc:mysql://localhost:3306/bank_db";
    private final String user = "root";
    private final String passwordDB = "password123";
    private final Scanner scan = new Scanner(System.in);

    private Connection connection;
    private Statement statement;
    private ResultSet resultSet;
    private PreparedStatement ps;
    // User_Name, Passwords ,First_Name, Last_Name, Address, Balance


    public void establishConnection() throws SQLException {
        connection = DriverManager.getConnection(url, user, passwordDB);
        statement = connection.createStatement();
    }

    public void createAccount() {
        try {
            connection = DriverManager.getConnection(url, user, passwordDB);

            System.out.print("Enter User name: ");
            String user = scan.nextLine();
            System.out.print("Enter password: ");
            String pass = scan.nextLine();
            System.out.print("Enter First name: ");
            String firstname = scan.nextLine();
            System.out.print("Enter Last name: ");
            String lastname = scan.nextLine();
            System.out.print("Enter Address: ");
            String addres = scan.nextLine();
            double bal = 0.0;

            ps = connection.prepareStatement("INSERT INTO bank_table(User_Name,Passwords,First_Name,Last_Name,Address, Balance) VALUES (?,?,?,?,?,?)");

            ps.setString(1, user);
            ps.setString(2, pass);
            ps.setString(3, firstname);
            ps.setString(4, lastname);
            ps.setString(5, addres);
            ps.setDouble(6, bal);

            boolean flagForCreateAcc = true;
            do {
                justLines();
                System.out.println("Are you sure you want to create this account? yes/no");
                String choice = scan.nextLine();
                justLines();
                if (choice.equals("yes") || choice.equals("YES")) {
                    ps.executeUpdate();
                    System.out.println();
                    justLines();
                    System.out.println("Account successfully created!");
                    justLines();
                    flagForCreateAcc = false;
                } else if (choice.equals("no") || choice.equals("NO")) {
                    System.out.println("The account WILL NOT be created!");
                    flagForCreateAcc = false;
                } else {
                    System.out.println("Invalid choice! Try again!");
                }

            } while (flagForCreateAcc);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void logInAccountDB() {
        try {
            establishConnection();
            int wrongPasswordCnt = 0;
            boolean flagUser = true;
            boolean flagPass = true;
            do {
                String inputUser;
                String inputPass;
                System.out.print("Enter your user name: ");
                inputUser = scan.nextLine();

                String sql = "SELECT * FROM bank_table WHERE User_Name='" + inputUser + "'";

                resultSet = statement.executeQuery(sql);
                if (resultSet.next()) {
                    do {
                        System.out.print("Enter password: ");
                        inputPass = scan.nextLine();
                        String sqlPass = "SELECT * FROM bank_table WHERE Passwords='" + inputPass + "'";
                        resultSet = statement.executeQuery(sqlPass);
                        if (resultSet.next()) {
                            System.out.println();
                            justLines();
                            System.out.println("Successfully logged in!");
                            resultSet = statement.executeQuery(sql);
                            if (resultSet.next()) {
                                System.out.println("Welcome: " + resultSet.getString(3));
                                justLines();
                                setUser(inputUser);
                                setPassword(inputPass);
                                flagPass = false;
                                flagUser = false;
                            }
                        } else {
                            wrongPasswordCnt++;
                            if (wrongPasswordCnt == 3) {
                                System.out.println();
                                justLines();
                                System.out.println("YOU HAVE ENTERED WRONG PASSWORD TOO MANY TIMES!");
                                System.out.println("ATM WILL EXIT AUTOMATICALLY!");
                                justLines();
                                System.exit(-2);
                            }
                            System.out.println("Invalid password! Try again!");
                        }
                    } while (flagPass);
                } else {
                    System.out.println("Invalid User! Try again!");
                    System.out.println();
                }
            } while (flagUser);
        } catch (Exception e) {
            System.out.println("ERROR: " + e.getMessage());
        }
    }

    public void printAccountDB() {
        double balance;
        try {
            establishConnection();
            resultSet = statement.executeQuery("select * from bank_table WHERE User_Name='" + getUser() + "'");

            justLines();
            while (resultSet.next()) {
                balance = resultSet.getDouble("Balance");
                System.out.println("User: " + resultSet.getString("User_Name") + "\n" + "First name: " + resultSet.getString("First_Name") + "\n" + "Last name: " + resultSet.getString("Last_Name")
                        + "\n" + "Address: " + resultSet.getString("Address") + "\n" + "Balance: " + balance);
            }
            justLines();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void depositMoney() {
        double moneyToDepositInput = 0;
        double moneyToDeposit = 0;
        double balance = 0;
        try {
            connection = DriverManager.getConnection(url, user, passwordDB);

            resultSet = statement.executeQuery("select * from bank_table where User_Name='" + getUser() + "'");
            if (resultSet.next()) {
                balance = resultSet.getDouble(6);
            }

            System.out.print("How much money do you want to deposit: ");
            moneyToDepositInput = Double.parseDouble(scan.nextLine());

            moneyToDeposit = moneyToDepositInput + balance;

            ps = connection.prepareStatement("update bank_table set Balance = ? where User_Name ='" + getUser() + "'");
            ps.setDouble(1, moneyToDeposit);

            justLines();
            System.out.println("Deposited " + moneyToDepositInput + " successfully!");

            ps.executeUpdate();

            System.out.println("Balance after deposit: " + moneyToDeposit);
            justLines();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void withdrawMoney() {
        double moneyToWithdraw = 0;
        double nowBalance = 0;
        try {
            establishConnection();

            resultSet = statement.executeQuery("select * FROM bank_table where User_Name='" + getUser() + "'");
            if (resultSet.next()) {
                nowBalance = resultSet.getDouble(6);
            }
            justLines();
            System.out.println("Your balance: " + nowBalance);

            System.out.print("How much money do you want to withdraw: ");
            moneyToWithdraw = Double.parseDouble(scan.nextLine());
            justLines();
            System.out.println();
            if (nowBalance - moneyToWithdraw < 0) {
                justLines();
                System.out.println("Invalid transaction! Not enough money!");
                justLines();
            } else if (nowBalance - moneyToWithdraw >= 0) {
                nowBalance = nowBalance - moneyToWithdraw;
                justLines();
                System.out.println("Successfully withdrew " + moneyToWithdraw + " money!");
                System.out.println("Balance after transaction: " + nowBalance);
                justLines();
            }

            ps = connection.prepareStatement("update bank_table set Balance = ? where User_Name ='" + getUser() + "'");
            ps.setDouble(1, nowBalance);

            ps.executeUpdate();

            System.out.println();


        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void deleteAccount() {
        try {
            connection = DriverManager.getConnection(url, user, passwordDB);
            justLines();
            System.out.println("Are you sure you want to delete your account (" + getUser() + ") ? yes/no");
            String inputUser = scan.nextLine();
            if (inputUser.equals("yes") || inputUser.equals("YES")) {
                ps = connection.prepareStatement("delete from bank_table where User_Name='" + getUser() + "'");
                ps.executeUpdate();
                justLines();
                System.out.println("Account has been deleted successfully!");
                justLines();
            } else if (inputUser.equals("no") || inputUser.equals("NO")) {
                System.out.println();
                justLines();
                System.out.println("Account WILL NOT be deleted!");
                justLines();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
