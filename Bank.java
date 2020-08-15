
import java.util.ArrayList;
import java.util.Random;


public class Bank {
 private String name;
 
 private ArrayList<User>users;
 
 private ArrayList<Account>accounts;
 public Bank(String name){
     this.name=name;
     this.users=new ArrayList<User>();
     this.accounts=new ArrayList<Account>();
 }
 
 public String getNewUserUUID(){
     // init
     String uuid;
     Random rng=new Random();
     int len=6;
     boolean nonUnique;
     do{
        // generate the number
        uuid="";
        for(int c=0;c<len;c++){
          uuid+=((Integer)rng.nextInt(10)).toString();
        }
        // check to make sure its unique
        nonUnique=false;
        for(User u:this.users){
          if(uuid.compareTo(u.getUUID())==0) {
              nonUnique=true;
              break;
          } 
        }
         
     }while(nonUnique);
     return uuid;
 }
 
  public String getNewAccountUUID(){
     
// init
 String uuid;
 Random rng=new Random();
 int len=10;
 boolean nonUnique;
 do{
    // generate the number
    uuid=" ";
    for(int c=0;c<len;c++){
      uuid+=((Integer)rng.nextInt(10)).toString();
    }
    // check to make sure its unique
    nonUnique=false;
    for(Account a:this.accounts){
      if(uuid.compareTo(a.getUUID())==0) {
          nonUnique=true;
          break;
      } 
    }

 }while(nonUnique);
 return uuid;    

  }
 public void addAccount(Account anAcct){
     this.accounts.add(anAcct);
 }
 public User addUser(String firstname,String lastname,String pin){
     // creat a new user object & add it to our list
     User newUser=new User(firstname,lastname,pin,this);
     this.users.add(newUser);
     // saving accounts for user
     Account newAccount=new Account("saving",newUser,this);
     
     newUser.addAccount(newAccount);
    this. addAccount(newAccount);
    return newUser;
 }
 public User UserLogin(String userID,String pin){
     // list of userts
     for(User u:this.users){
       // check users id is correct
       if(u.getUUID().compareTo(userID)==0&& u.validatepin(pin)){
         return u;  
       }
     }
     return null;
 }
 public String getName(){
     return this.name;
 }
}
