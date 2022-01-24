public class Atm {
    public static void main(String[] args) {
        // I have put the url,username and password into the args.
        BankService bankService = new BankService(args[0],args[1],args[2]);
        MenuService menu = new MenuService(bankService);
        menu.start();
    }
}
