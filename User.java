

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
//import java.util.logging.Level;
//import java.util.logging.Logger;

public class User {
   private String FirstName;
   
  // private String LastName;
   
   private String uuid;
   
   private byte PinHash[];
   
   private ArrayList<Account>accounts;
   public User(String firstname,String lastname,String pin,Bank theBank){
       
       this.FirstName=firstname;
      // this.LastName=lastname;
       
       try {
           //hashcode the pins md5 hash rather than the orginal value
           //security reasons
           MessageDigest md=MessageDigest.getInstance("MD5");
           this.PinHash=md.digest(pin.getBytes());
       } catch (NoSuchAlgorithmException e) {
           System.err.println("error, cought noSuchAlgorithmException");
           e.printStackTrace();
           System.exit(1);
       }
      // get a new universal id for the user
      this.uuid=theBank.getNewUserUUID();
      
      // creat empty list of accounts
      
     this.accounts=new ArrayList<Account>();
     //print log message
     System.out.printf("new user %s,%s with ID %s created.\n",lastname,firstname,this.uuid);
     
     
   }
   public void addAccount(Account anAcct){
       this.accounts.add(anAcct);
   }
   public String getUUID(){
       return this.uuid;
   }
   public boolean validatepin(String apin){
       try {
           MessageDigest md=MessageDigest.getInstance("MD5");
           return MessageDigest.isEqual(md.digest(apin.getBytes()),this.PinHash);
       } catch (NoSuchAlgorithmException e) {
             System.err.println("error, cought noSuchAlgorithmException");
           e.printStackTrace();
           System.exit(1);
       }
       return false;
   }
   public String getFirstName(){
       return this.FirstName;
   }
   public void printAccountSummary(){
       System.out.printf("\n\n%s's accounts summary\n",this.FirstName);
       for(int a=0;a<this.accounts.size();a++){
           System.out.printf("%d)%s\n",a+1,
            this.accounts.get(a).getSummryLine());
       }
       System.out.println();
   }
   public int numAccounts(){
       return this.accounts.size();
   }
   public void printAcctTransferHistory(int acctIdx){
       this.accounts.get(acctIdx).printTransferHistory();
   }
   public double getAcctBalance(int acctIdx){
       
       return this.accounts.get(acctIdx).getBalance();
   }
   public String getAcctUUID(int acctIdx){
       return this.accounts.get(acctIdx).getUUID();
   }
   public void addAcctTransaction(int acctIdx,double amount,String memo){
       this.accounts.get(acctIdx).addTransaction(amount,memo);
   }
}
