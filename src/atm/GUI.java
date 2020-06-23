/*
 * Filename: GUI.java
 * Author: Gabrielle Jeuck
 * Purpose: Houses main and GUI for ATM Machine.  Provides main functions  
 *          for buttons/clicks and catches numeric, insufficientfunds, and 
 *          validation input.  
 */
package atm;

/**
 *
 * @author gwins
 */
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.DecimalFormat;
import javax.swing.*;

public final class GUI extends JFrame {

    // variable declarations
    private double withdrawAmount;
    private double depositAmount;
    private double transferAmount;
    // frame, buttons, radiobuttons
    private final JFrame frame;
    private JTextField input1;
    private JButton withdraw, deposit, transfer, balance;
    private JRadioButton checking, savings;
    // decimal format 
    static DecimalFormat df = new DecimalFormat("#,###,##0.00");
    // create account objects
    private final Account chkAccount = new Account("Checking", 0);
    private final Account savAccount = new Account("Saving", 0);

    public GUI() {
        //Create and set up the window.
        frame = new JFrame("ATM Machine");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setPreferredSize(new Dimension(450, 200));
        frame.setVisible(true);
        frame.setResizable(false);
        addComponentsToPane(frame.getContentPane());
        frame.pack();
    }

    public void addComponentsToPane(Container pane) {

        // layout settings
        pane.setLayout(new GridBagLayout());
        GridBagConstraints c = new GridBagConstraints();

        // withdraw button 
        withdraw = new JButton("Withdraw");
        c.weightx = 0.5;
        c.fill = GridBagConstraints.HORIZONTAL;
        c.gridx = 0;
        c.gridy = 0;
        c.insets = new Insets(0, 80, 5, 5);
        withdraw.setPreferredSize(new Dimension(70, 30));
        pane.add(this.withdraw, c);
        withdraw.addActionListener(new ButtonListener());

        // deposit button
        deposit = new JButton("Deposit");
        c.fill = GridBagConstraints.HORIZONTAL;
        c.weightx = 0.5;
        c.insets = new Insets(0, 5, 5, 80);
        c.gridx = 1;
        c.gridy = 0;
        deposit.setPreferredSize(new Dimension(70, 30));
        pane.add(deposit, c);
        deposit.addActionListener(new ButtonListener());

        // balance button
        balance = new JButton("Balance");
        c.fill = GridBagConstraints.HORIZONTAL;
        c.weightx = 0.5;
        c.insets = new Insets(5, 5, 5, 80);
        c.gridx = 1;
        c.gridy = 1;
        balance.setPreferredSize(new Dimension(70, 30));
        pane.add(this.balance, c);
        balance.addActionListener(new ButtonListener());

        // transfer button
        transfer = new JButton("Transfer to");
        c.fill = GridBagConstraints.HORIZONTAL;
        c.weightx = 0.0;
        c.gridwidth = 1;
        c.insets = new Insets(5, 80, 5, 5);
        c.gridx = 0;
        c.gridy = 1;
        transfer.setPreferredSize(new Dimension(70, 30));
        pane.add(transfer, c);
        transfer.addActionListener(new ButtonListener());

        // Checking radio button
        checking = new JRadioButton("Checking", true);
        c.fill = GridBagConstraints.HORIZONTAL;
        c.weightx = 0.0;
        c.gridwidth = 1;
        c.insets = new Insets(3, 80, 10, 5);
        c.gridx = 0;
        c.gridy = 2;
        pane.add(checking, c);

        // savings radio button 
        savings = new JRadioButton("Savings");
        c.fill = GridBagConstraints.HORIZONTAL;
        c.ipady = 0;
        c.weightx = 0.0;
        c.gridwidth = 1;
        c.insets = new Insets(3, 10, 10, 100);
        c.gridx = 1;
        c.gridy = 2;
        pane.add(savings, c);

        // button groups for radio buttons so only one is selected at a time
        ButtonGroup buttonGroup = new ButtonGroup();
        buttonGroup.add(savings);
        buttonGroup.add(checking);

        // textfield for user input
        input1 = new JTextField("");
        c.fill = GridBagConstraints.HORIZONTAL;
        c.ipady = 0;
        c.weighty = 0.0;
        c.insets = new Insets(0, 70, 10, 70);
        c.gridx = 0;       //aligned with checking button
        c.gridwidth = 3;   //3 columns wide
        c.gridy = 3;       //third row
        input1.setPreferredSize(new Dimension(90, 20));
        pane.add(input1, c);
    }

    private class ButtonListener implements ActionListener {

        @Override
        // controls for button / radio buttons on clicks
        public void actionPerformed(ActionEvent e) throws NumberFormatException {
            try {
                // withdraw button clicks
                if (e.getSource() == withdraw) {
                    withdrawAmount = Double.parseDouble(input1.getText());
                    if (withdrawAmount % 20 == 0 & withdrawAmount > 0) {

                        try {

                            if (checking.isSelected()) {
                                chkAccount.withdraw(withdrawAmount);
                                JOptionPane.showMessageDialog(null, "Checking withdrawn $"
                                        + df.format(withdrawAmount)
                                        + chkAccount.getBalance("Checking"));
                            } else {
                                savAccount.withdraw(withdrawAmount);
                                JOptionPane.showMessageDialog(null, "Savings withdrawn $"
                                        + df.format(withdrawAmount)
                                        + savAccount.getBalance("Savings"));
                            } // end else
                        } catch (InsufficientFunds ie) {
                            JOptionPane.showMessageDialog(null, "Insuficient Funds!  "
                                    + chkAccount.getBalance("Checking")
                                    + savAccount.getBalance("Savings"));
                        } catch (NumberFormatException ne) {
                            JOptionPane.showMessageDialog(null, "Please enter a valid number!");
                        } // end catch ne
                    } else {
                        JOptionPane.showMessageDialog(null, "You can only withdraw in increments of $20.00! ");
                    } // end else

                    // deposit button clicks
                } else if (e.getSource() == deposit) {
                    try {
                        depositAmount = Double.parseDouble(input1.getText());
                        if (depositAmount > 0) {
                            if (checking.isSelected()) {
                                chkAccount.deposit(depositAmount);
                                JOptionPane.showMessageDialog(null, "Checking Deposit: $"
                                        + df.format(depositAmount)
                                        + chkAccount.getBalance("Checking"));
                            } else {
                                savAccount.deposit(depositAmount);
                                JOptionPane.showMessageDialog(null, "Savings Deposit: $"
                                        + df.format(depositAmount)
                                        + savAccount.getBalance("Savings"));
                            } // end else
                        } else {
                            JOptionPane.showMessageDialog(null, "Positive numbers only");
                        }

                    } catch (NumberFormatException ne) {
                        JOptionPane.showMessageDialog(null, "Please enter a valid number!");
                    } // end catch ne

                    // transfer button clicks
                } else if (e.getSource() == transfer) {
                    try {
                        transferAmount = Double.parseDouble(input1.getText());
                        if (transferAmount > 0) {
                            if (checking.isSelected()) {
                                chkAccount.transferTo(savAccount, chkAccount, transferAmount);
                                JOptionPane.showMessageDialog(null, "You transferred $"
                                        + df.format(transferAmount) + " from Savings to Checking"
                                        + savAccount.getBalance("Savings")
                                        + chkAccount.getBalance("Checking"));
                            } else {
                                savAccount.transferTo(chkAccount, savAccount, transferAmount);
                                JOptionPane.showMessageDialog(null, "You transferred $"
                                        + df.format(transferAmount) + " from Checking to Savings"
                                        + savAccount.getBalance("Savings")
                                        + chkAccount.getBalance("Checking"));
                            } // end else 
                        } else {
                            JOptionPane.showMessageDialog(null, "Positive numbers only");
                        }

                    } catch (NumberFormatException nfd) {
                        JOptionPane.showMessageDialog(null, "Please enter a valid number");
                    } catch (InsufficientFunds ie) {
                        JOptionPane.showMessageDialog(null, "Insuficient Funds!  "
                                + chkAccount.getBalance("Checking")
                                + savAccount.getBalance("Savings"));
                    } // end catch ie

                    // balance button click
                } else if (e.getSource() == balance) {
                    if (checking.isSelected()) {
                        JOptionPane.showMessageDialog(null, chkAccount.getBalance("Checking").replace("\n", ""));
                    } else {
                        JOptionPane.showMessageDialog(null, savAccount.getBalance("Savings").replace("\n", ""));
                    } // end else

                }// end else if
            } catch (NumberFormatException ne) {
                JOptionPane.showMessageDialog(null, "Please enter a valid number");

            } // end catch ne
        } // end actionperformed
    } // end buttonlistener

    public static void main(String args[]) {
        GUI ATM = new GUI();

    } // end main

} // end class 
