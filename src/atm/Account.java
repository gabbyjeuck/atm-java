/*
 * Filename: Account.java 
 * Author: Gabrielle Jeuck
 * Purpose: Class for supporting ATM accounts - checking/savings.   
 *          Allows for balance/deposit/withdraw/transfers of funds.
 */
package atm;

import static atm.GUI.df;
import javax.swing.JOptionPane;

/**
 *
 * @author gwins
 */
public class Account {

    //declarations
    private double balance; // setting each accounts beginning balance
    private static int serviceCount = 0; // used to count withdraws
    private final double serviceFee = 1.50; // after 4 withdraws fee per withdraw
    private String name;

    // Account constructor
    public Account(String name, double balance) {
        this.balance = balance;
        this.name = name;
    } // end constructor

    // getters
    public double getBalance() {
        return this.balance;
    } // end getBalance

    // String for getBalance for purpose of repeat prints on balances
    public String getBalance(String x) {
        String str = "\n" + x + " Balance: $" + df.format(this.balance);
        return str;
    } // end getBalance w/ String

    // setters
    public void setBalance(double balance) {
        this.balance = balance;
    } // end setBalance

    // withdraw funds and apply fee when 4 or more withdraws happen 
    public void withdraw(double withdraw) throws InsufficientFunds {
        if (serviceCount >= 4 && ((this.balance + serviceFee) > withdraw)) {
            JOptionPane.showMessageDialog(null, "You have reached your maximum "
                    + "withdraw of 4. \nYou will inquire a fee of $1.50/withdraw");
            if ((this.balance) >= (withdraw + serviceFee)) {
                this.balance = this.balance - (withdraw + serviceFee);
            } else {
                throw new InsufficientFunds(String.format("Current balance %.2f "
                        + "is less than requested amount %.2f", this.balance, withdraw));
            } // end else
        } else if (this.balance >= withdraw && (serviceCount < 4)) {
            this.balance = this.balance - withdraw;
            serviceCount++;
        } else if (this.balance < (withdraw + serviceFee)) {
            throw new InsufficientFunds(String.format("Current balance %.2f "
                    + "is less than requested amount %.2f", this.balance, withdraw));
        } // end else if

    } // end withdraw

    // deposit funds
    public void deposit(double deposit) {
        this.balance += deposit;
    } // end deposit

    /* 
     * transfer funds from account to other
     * acct1 acts for being taken from
     * acct2 acts for funds being transferred to
     * amount acts for funds being transferred
     */
    public void transferTo(Account acct1, Account acct2, double amount) throws InsufficientFunds {
        if (acct1.balance >= amount) {
            acct1.balance -= amount;
            acct2.balance += amount;
        } else {
            throw new InsufficientFunds("Insufficient Funds");
        } // end else
    } // end transferTo
}// end class
