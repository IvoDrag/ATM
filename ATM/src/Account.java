import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class Account {
    private Integer id;
    private String userName;
    private String userPassword;
    private String firstName;
    private String lastName;
    private String address;
    private double balance;

    public Account(String userName, String userPassword) {
        this.userName = userName;
        this.userPassword = userPassword;
    }

    public Account(String userName, String password, String firstName, String lastName, String address, double balance) {
        this.userName = userName;
        this.userPassword = password;
        this.firstName = firstName;
        this.lastName = lastName;
        this.address = address;
        this.balance = balance;
    }

    // Getters and Setters
    /*
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getUserPassword() {
        return userPassword;
    }

    public void setUserPassword(String password) {
        this.userPassword = password;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public double getBalance() {
        return balance;
    }

    public void setBalance(double balance) {
        this.balance = balance;
    }
    */
}
