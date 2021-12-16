public class Account  {
    private String user_Name;
    private String password;

    public String getUser() {
        return user_Name;
    }
    public String getPassword() { return password; }

    public void setUser(String u) { user_Name = u; }
    public void setPassword(String pass) { password = pass; }

    public void firstMenuOptions() {
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

    public void justLines() {
        System.out.println("----------------------------------------");
    }

}
