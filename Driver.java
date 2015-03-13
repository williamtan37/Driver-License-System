import java.util.*;
public class Driver
{
    private String firstName, lastName, address, gender,eyeColor, classType,SSN,
    username, password, DLN; 
    private Date DOB, expirDate;
    private Boolean glasses, suspension;
    private int violationPoints, height, weight;

    //Normal Features
    public Driver (String driverFirstName, String driverLastName, String driverAddress, String driverGender,
    Date driverDOB, int driverHeight, int driverWeight, String driverEyeColor, Date driverExpirDate,
    String driverClassType, Boolean driverGlasses, String driverSSN, //Excluding Donor and Photo
    //Extra Features
    String driverDLN, String driverUsername, String driverPassword, boolean driverSuspension, int driverViolationPoints)
    {
        //Normal Features
        firstName = driverFirstName;
        lastName =  driverLastName;
        address = driverAddress;
        gender = driverGender;
        DOB = driverDOB;
        height = driverHeight;
        weight = driverWeight;
        eyeColor = driverEyeColor;
        expirDate = driverExpirDate;
        classType = driverClassType;
        glasses = driverGlasses;
        SSN = driverSSN;

        //Extra Features
        DLN = driverDLN;
        username = driverUsername;
        password = driverPassword;       
        suspension = driverSuspension;
        violationPoints = driverViolationPoints;
    }

    public String getUsername()
    {
        return username;
    }

    public String getPassword()
    {
        return password;
    }

     public String getFirstName()
    {
        return firstName;
    }
    public String getLastName()
    {
        return lastName;
    }
    
    public int getWeight()
    {
        return weight;
    }
    
     public int getHeight()
    {
        return height;
    }
    
    public String getGender()
    {
        return gender;
    }
    
    public void reverseSuspension()
    {
        if(suspension == false)
        suspension = true;       
        else
        suspension = false;
    }
    
    public boolean getSuspension()
    {
        return suspension;
    }
    
    public Date getDOB()
    {
        return DOB;
    }
    
     public boolean compare(String username, String password, Driver driver)
    {
        if(username.equals(driver.getUsername()) && password.equals(driver.getPassword()))
            return true;
        else
            return false;

    }
    
        public Date getExpirDate(){
        return expirDate;
    }
    
    public void setExpirDate(Date newDate)
    {
        expirDate = newDate;
    }
    public String toString()
    {
       String result, shortGender;
       int feet, inches;
       
       feet = height / 12;
       inches = height - (feet * 12);
       
       if(gender.equals("Male"))
       shortGender = "M";
       else
       shortGender = "F";
       
       result = "License Number: " + DLN + "\tName: " + firstName + " " + lastName + ".\tGender: " + shortGender + ".\tDOB: " + DOB.toGMTString() + ".\tExp Date: " + expirDate.toGMTString()+ ".\tWeight: " + weight + "lb.\tHeight: " + feet + " ft " + inches + " in.\tSuspended: " + suspension +".\tViolation Points: " + violationPoints + " points.";
       result += "\tAddress: " + address + ".\tGlasses: " + glasses + ".\tEye Color: " + eyeColor + ".\tSSN: XXX - XX - XXXX.";
       
       return result;
    }
    
}