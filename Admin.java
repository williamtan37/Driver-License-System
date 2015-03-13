import java.io.*;
import java.text.*;
import java.util.*;
public class Admin implements Serializable
{
    public String  username, password;
    
    public Admin(String adminUsername, String adminPassword)
    {
        username = adminUsername;
        password = adminPassword;
    }
    
    public String getUsername()
    {
        return username;
    }
    
    public String getPassword()
    {
        return password;
    }    
    
    public boolean compare(Admin admin1, Admin admin2){
        if(admin1.getUsername().equals(admin2.getUsername()) && admin1.getPassword().equals(admin2.getPassword()))
        return true;
        else 
        return false;
    }
    
    public String toString(){
        String result = "";
        

        result += "Username: " + username + "\n";
        result += "Password: " + password;

        return result;
    }
}