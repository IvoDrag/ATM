

/*


THIS JAVA CLASS (MENU) IS EXCLUDED FROM THE PROJECT !


 */



package Projects.ATM;

import java.util.HashMap;
import java.util.InputMismatchException;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Menu extends Account {
    Scanner scan = new Scanner(System.in);

    public boolean isValidUsername(String name) {
        String regex = "^[A-Za-z]\\w{3,29}$";
        Pattern p = Pattern.compile(regex);
        if (name == null) {
            return false;
        }
        Matcher m = p.matcher(name);
        return m.matches();
    }

    HashMap<String,Integer> data = new HashMap<String,Integer>();

    public void start() {
        System.out.println("-------------------------ATM-------------------------");
        String inputUser = null;
        String inputPass = null;
        data.put("Ivailo", 132013);
        data.put("Radina", 1811);

        System.out.print("Enter username: ");
        boolean flag = true;
        do {
            inputUser = scan.nextLine();
            switch (inputUser) {
                case "Ivailo":
                    setUser(inputUser);
                    System.out.print("Enter password for Ivailo: ");
                    int passWrongCnt = 0;
                    boolean flagPass = true;
                    do{
                        inputPass = scan.nextLine();
                        switch(inputPass) {
                            case 132013:
                                System.out.println("CORRECT PASSWORD!");
                                //System.out.println("Welcome Ivailo!");
                                setPassword(inputPass);
                                flagPass = false;
                                flag = false;
                                break;
                            default:
                                passWrongCnt++;
                                if(passWrongCnt == 3) {
                                    System.out.println();
                                    System.out.print("Incorrect password too many times! ATM will exit automatically!");
                                    System.out.println();
                                    System.exit(-2);
                                }
                                System.out.print("Wrong password! Try again: ");
                                break;
                        }
                    }while(flagPass);
                    break;

                case "Radina":
                    setUser(inputUser);
                    System.out.print("Enter password for Radina: ");
                    flagPass = true;
                    passWrongCnt = 0;
                    do{
                        inputPass = scan.nextInt();
                        switch(inputPass) {
                            case 1818:
                                System.out.println("CORRECT PASSWORD!");
                                //System.out.println("Welcome Radina!");
                                setPassword(inputPass);
                                flagPass = false;
                                flag = false;
                                break;
                            default:
                                passWrongCnt++;
                                if(passWrongCnt == 3) {
                                    System.out.println();
                                    System.out.print("Incorrect password too many times! ATM will exit automatically!");
                                    System.out.println();
                                    System.exit(-2);
                                }
                                System.out.print("Wrong password! Try again: ");
                                break;
                        }
                    }while(flagPass);
                    break;
                default:
                    System.out.print("Invalid account! Try again: ");
                    break;
            }
        }while(flag);

        boolean flagToExitATM = true;
        do {
            accountOptions();
            System.out.print("Choose an option: ");
            System.out.println();
            int optionForAcc = scan.nextInt();
            scan.nextLine();
            switch (optionForAcc) {
                case 1:
                    accountInfo();
                    break;
                case 2:
                    System.out.print("How much money do you want to withdraw: ");
                    double withdraw = scan.nextDouble();
                    withdrawFunction(withdraw);
                    break;
                case 3:
                    System.out.print("How much money do you wish to deposit: ");
                    double deposit = scan.nextDouble();
                    depositFunction(deposit);
                    break;
                case 4:
                    System.out.println("Are you sure you want to exit? (y/n)");
                    String exitLvl = scan.nextLine();
                    if(exitLvl.equals("y") || exitLvl.equals("Y")) {
                        System.out.println("-----------------------------");
                        System.out.println("Thank you for using ATM!");
                        System.out.println("-----------------------------");
                        flagToExitATM = false;
                    }
                    break;
                default:
                    break;
            }
        }while(flagToExitATM);
    }
}
