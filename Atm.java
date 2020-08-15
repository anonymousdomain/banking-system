import java.util.Scanner;

public class Atm {

    public static void main(String args[]) {
        // itit scanner
        Scanner scan = new Scanner(System.in);
        // init bank
        Bank theBank = new Bank("bank of ethiopia");
        // add a user which also creats savings accounts
        User aUser = theBank.addUser("dawit", "mekonnen", "12345");
        // add checking accounts
        Account newAccount = new Account("checking", aUser, theBank);
        aUser.addAccount(newAccount);
        theBank.addAccount(newAccount);

        User curUser;
        while (true) {
            // stay in the login promot until sucssful login
            curUser = Atm.mainMenuPromt(theBank, scan);
            // stay in mainmenu until user quits
            Atm.printUserMenu(curUser, scan);
        }
    }

    public static User mainMenuPromt(Bank theBank, Scanner scan) {
        String userID;
        String pin;
        User authUser;
        do {
            System.out.printf("\n\nwelcome to %s\n\n", theBank.getName());
            System.out.print("Enter user ID:");
            userID = scan.nextLine();
            System.out.print("Enter pin: ");
            pin = scan.nextLine();

            authUser = theBank.UserLogin(userID, pin);
            if (authUser == null) {
                System.out.println("incorrect user id/pin combination. " + "please try again");
            }
        } while (authUser == null);
        return authUser;
    }

    public static void printUserMenu(User theUser, Scanner scan) {
        theUser.printAccountSummary();

        int choice;
        do {
            System.out.printf("welcome %s,what would like to do", theUser.getFirstName());
            System.out.println("1)show account tarnsaction history");
            System.out.println("2)withdrawal");
            System.out.println("3)deposit");
            System.out.println("4)Transfer");
            System.out.println("5)Quit");
            System.out.println();
            System.out.println("Enter the choice");
            choice = scan.nextInt();
            if (choice < 1 || choice > 5) {
                System.out.println("invalid choice.please choose 1-5");
            }
        } while (choice < 1 || choice > 5);
        // process the choice
        switch (choice) {
            case 1:
                Atm.showTransferHistory(theUser, scan);
                break;

            case 2:
                Atm.withdrawFunds(theUser, scan);
                break;
            case 3:
                Atm.depositFunds(theUser, scan);
                break;
            case 4:
                Atm.transferFunds(theUser, scan);
                break;
            case 5:
                // gobble up the rest of previous input
                scan.nextLine();
                break;

        }
        // redisplay this menu unless the user wants to quit
        if (choice != 5) {
            Atm.printUserMenu(theUser, scan);
        }
    }

    public static void showTransferHistory(User theUser, Scanner scan) {
        int theAcct;
        do {
            System.out.printf("Enter the number(1-%d)of the accounr \n" + "whose transactions u wants to see",
                    theUser.numAccounts());
            theAcct = scan.nextInt() - 1;
            if (theAcct < 0 || theAcct >= theUser.numAccounts()) {
                System.out.println("invaliid account.plrase try again.");
            }
        } while (theAcct < 0 || theAcct >= theUser.numAccounts());
        theUser.printAcctTransferHistory(theAcct);
    }

    public static void transferFunds(User theUser, Scanner scan) {
        int fromAcct;
        int toAcct;
        double amount;
        double acctBal;
        // get the account to trasfer from
        do {
            System.out.printf("Enter the number(1-%d)of the account\n" + "to transfer from: ", theUser.numAccounts());
            fromAcct = scan.nextInt() - 1;
            if (fromAcct < 0 || fromAcct >= theUser.numAccounts()) {
                System.out.println("invalid account.please try again.");
            }
        } while (fromAcct < 0 || fromAcct >= theUser.numAccounts());
        acctBal = theUser.getAcctBalance(fromAcct);
        // get the account transfer to
        do {
            System.out.printf("Enter the number(1-%d)of the account\n" + "to transfer to: ", theUser.numAccounts());
            toAcct = scan.nextInt() - 1;
            if (toAcct < 0 || toAcct >= theUser.numAccounts()) {
                System.out.println("invalid account.please try again.");
            }
        } while (toAcct < 0 || toAcct >= theUser.numAccounts());
        // get the amount to transfer
        do {
            System.out.printf("Enter the amount to transfer (max $%.02f):$", acctBal);
            amount = scan.nextDouble();
            if (amount < 0) {
                System.out.println("amount must be greater than zero");
            } else if (amount > acctBal) {
                System.out.printf("amount must not be greater than\n" + "balance of $%.02f\n", acctBal);
            }
        } while (amount < 0 || amount > acctBal);
        // do transfer
        theUser.addAcctTransaction(fromAcct, -1 * amount,
                String.format("Transfer to account %s", theUser.getAcctUUID(toAcct)));

        theUser.addAcctTransaction(toAcct, amount,
                String.format("Transfer to account %s", theUser.getAcctUUID(fromAcct)));
    }

    public static void withdrawFunds(User theUser, Scanner scan) {
        int fromAcct;
        double amount;
        double acctBal;
        String memo;
        // get the account to trasfer from
        do {
            System.out.printf("Enter the number(1-%d)of the account\n" + "to withdraw from: ", theUser.numAccounts());
            fromAcct = scan.nextInt() - 1;
            if (fromAcct < 0 || fromAcct >= theUser.numAccounts()) {
                System.out.println("invalid account.please try again.");
            }
        } while (fromAcct < 0 || fromAcct >= theUser.numAccounts());
        acctBal = theUser.getAcctBalance(fromAcct);

        // get the amount to transfer
        do {
            System.out.printf("Enter the amount to transfer (max $%.02f):$", acctBal);
            amount = scan.nextDouble();
            if (amount < 0) {
                System.out.println("amount must be greater than zero");
            } else if (amount > acctBal) {
                System.out.printf("amount must not be greater than\n" + "balance of $%.02f\n", acctBal);
            }
        } while (amount < 0 || amount > acctBal);
        // gobble up the rest of previous input
        scan.nextLine();
        // get the memo
        System.out.print("enter the memo: ");
        memo = scan.nextLine();
        // do withdraw
        theUser.addAcctTransaction(fromAcct, -1 * amount, memo);
    }

    public static void depositFunds(User theUser, Scanner scan) {
        int toAcct;
        double amount;
        double acctBal;
        String memo;
        // get the account to trasfer from
        do {
            System.out.printf("Enter the number(1-%d)of the account\n" + "to deposit in: ", theUser.numAccounts());
            toAcct = scan.nextInt() - 1;
            if (toAcct < 0 || toAcct >= theUser.numAccounts()) {
                System.out.println("invalid account.please try again.");
            }
        } while (toAcct < 0 || toAcct >= theUser.numAccounts());
        acctBal = theUser.getAcctBalance(toAcct);

        // get the amount to transfer
        do {
            System.out.printf("Enter the amount to transfer (max $%.02f):$", acctBal);
            amount = scan.nextDouble();
            if (amount < 0) {
                System.out.println("amount must be greater than zero");
            }

        } while (amount < 0);
        // gobble up the rest of previous input
        scan.nextLine();
        // get the memo
        System.out.print("enter the memo: ");
        memo = scan.nextLine();
        // do withdraw
        theUser.addAcctTransaction(toAcct, amount, memo);
    }
}
