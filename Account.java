
import java.util.ArrayList;

public class Account {

    private String name;
    
   // private double balance;
    
    private String uuid;
    
    //private User holder;
    
    private ArrayList<Transaction> transactions;
    public Account(String name,User holder,Bank theBank){
        //set the account
        this.name=name;
        //this.holder=holder;
        
        // get thr new account uuid
        this.uuid=theBank.getNewAccountUUID();
        
        // init transactions
        this.transactions=new ArrayList<Transaction>();
        
        
        
    }
    public String getUUID(){
       return this.uuid;
   }
    public String getSummryLine(){
      double balance=this.getBalance();
      
      if(balance>=0){
          return String.format("%s : $%.02f :%s",this.uuid,balance,this.name);
      }
      else
      {
        return String.format("%s : $(%.02f) :%s",this.uuid,balance,this.name);   
      }
    }
     public    double getBalance(){
         double balance=0;
         for(Transaction t:transactions){
             balance+=t.getAmount();
         }
         return balance;
     }
     public void printTransferHistory(){
         System.out.printf("\nTransaction history for account %s\n",this.uuid);
         for(int t=this.transactions.size()-1;t>=0;t--){
         System.out.println(this.transactions.get(t).getSummaryLine());
     }
         System.out.println();
     }
     public void addTransaction(double amount,String memo){
         Transaction newTrans=new Transaction(amount,memo,this);
         this.transactions.add(newTrans);
     }
}
