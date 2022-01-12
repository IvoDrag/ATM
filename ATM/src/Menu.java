import java.util.Scanner;


public class Menu extends dataBaseMySQL {
    Scanner scan = new Scanner(System.in);
    public void start() {
        System.out.println("-------------------------ATM-------------------------");
        int inputOptionLevelOne;
        int inputOptionLevelTwo;

        boolean flagMenu = true;
        
        boolean flagAccountMenu = true;
        do {
            firstMenuOptions();
            System.out.print("Option: ");
            inputOptionLevelOne = Integer.parseInt(scan.nextLine());
            
            switch(inputOptionLevelOne) {
                case 1:
                    createAccount();
                    break;
                case 2:
                    logInAccountDB();
                    do {
                        accountOptions();
                        System.out.print("Option: ");
                        inputOptionLevelTwo = Integer.parseInt(scan.nextLine());
                        System.out.println();
                        switch(inputOptionLevelTwo) {
                            case 1:
                                //Account Information!
                                printAccountDB();
                                break;
                            case 2:
                                // Deposit Money!
                                depositMoney();
                                break;
                            case 3:
                                // Withdraw Money!
                                withdrawMoney();
                                break;
                            case 4:
                                // Delete Account!
                                deleteAccount();
                                flagAccountMenu = false;
                                break;
                            case 5:
                                // Exit from account!
                                flagAccountMenu = false;
                                break;
                            default:
                                System.out.println("Invalid option! Try again!");
                                break;
                        }
                    }while(flagAccountMenu);

                    break;
                case 0:
                    System.out.println("Thank you for using ATM!");
                    flagMenu = false;
                    break;
                default:
                    System.out.println("Invalid option! Try again!");
                    break;
            }
        }while(flagMenu);
    }
}
