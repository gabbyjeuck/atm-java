/*
 * Filename: InsufficientFunds.java 
 * Author: Gabrielle Jeuck
 * Purpose: Custom event handler for ATM GUI.  If users account doesn't have enough funds,  
 *          for withdraw/transfers then InsufficientFunds is thrown.  
 */
package atm;

public class InsufficientFunds extends RuntimeException {
    // DECLARATIONS
    private String message;
    // constructor for exception
    public InsufficientFunds(String msg) {
        this.message = msg;
    } // end constructor
    
    // calls super for exception returns message created by programmer
    public InsufficientFunds(Throwable cause, String msg){
        super(cause);
        this.message = msg;
    } // end method
    
    @Override
    public String getMessage(){
        return message;
    } // end method getMessage
}// end class
