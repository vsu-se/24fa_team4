package ebay;

public class SystemAdmin {
    String adminPassword;
    String username;
    String password;

    public SystemAdmin(String adminPassword, String username, String password){
        //Password to be able to create an admin user.
        if(adminPassword.equals("Password")){
            this.username = username;
            this.password = password;
        }
    }
    public String getUsername(){
        return username;
    }

    public String getPassword(){
        return password;
    }

    public void setPassword(String password){
        this.password = password;
    }
}
