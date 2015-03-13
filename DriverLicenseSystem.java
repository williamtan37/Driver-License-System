import java.io.*;
import java.util.*;
import java.text.*;
public class DriverLicenseSystem 
{
    private ListNode adminList, driverList;
    private LinkedList<Driver> driverLinkedList = new LinkedList();
    private LinkedList<Admin> adminLinkedList = new LinkedList();

    public DriverLicenseSystem()
    {
        adminList = null;
        driverList = null;
    }

    public void inputAdminStream() throws IOException, ClassNotFoundException
    {
        String username = "" , password = "", str = "";
        boolean done = false, addAdmin = true;
        int delimiterIndex = 0;
        String letter;

        File file = new File("adminList.txt");    
        BufferedReader input = new BufferedReader(new FileReader(file));

        while((str = input.readLine()) != null){
            if(!str.equals("Username,Password")){//Seperate the title from actual data.

                while(done == false){  
                    try{//find the comma
                        letter = str.charAt(delimiterIndex) + "";
                        if(letter.equals(","))
                            done = true;
                        else
                            delimiterIndex++;
                    }
                    catch(IndexOutOfBoundsException e){//because the last line will return 0, resulting in an error
                        addAdmin = false;//prevents adding of useless data
                        break;                      
                    }
                }
                //use comma index to retrieve data before it
                for(int i = 0; i < delimiterIndex; i++) 
                    username += str.charAt(i);

                //use comma index to retrieve data after it
                for(int i = delimiterIndex + 1; i < str.length(); i++)
                    password += str.charAt(i);

                Admin admin = new Admin(username, password);

                if(addAdmin == true)
                    addAdmin(admin);

                //reset values for next line
                delimiterIndex = 0;    
                username = "";
                password = "";
                done = false;
            }  
        }    
    }

    public void inputDriverStream() throws IOException, ClassNotFoundException, ParseException
    {
        String str, firstName, lastName, address, gender, eyeColor, classType, SSN, DLN, username, password;
        Date DOB, expDate;
        int height, weight, violationPoints;
        boolean hasGlasses, isSuspended;
        Driver driver;
        DateFormat df = new SimpleDateFormat("EEE MMM dd kk:mm:ss z yyyy", Locale.ENGLISH);

        File file = new File("driverList.txt");    
        BufferedReader input = new BufferedReader(new FileReader(file));

        while((str = input.readLine()) != null){
            if(!str.equals("First Name,Last Name,Address,Gender,DOB,Height,Weight,Eye Color,EXP Date,Class Type,Glasses,SSN,DLN,Username,Password,Suspension,Violation Points")){
                String[] parts = str.split(",");

                firstName = parts[0];
                lastName = parts[1];
                address = parts[2];
                gender = parts[3];
                DOB = df.parse(parts[4]);
                height = Integer.parseInt(parts[5]);
                weight = Integer.parseInt(parts[6]);
                eyeColor = parts[7];
                expDate = df.parse(parts[8]);
                classType = parts[9];
                hasGlasses = Boolean.parseBoolean(parts[10]);
                SSN = parts[11];
                DLN = parts[12];
                username = parts[13];
                password = parts[14];
                isSuspended = Boolean.parseBoolean(parts[15]);
                violationPoints = Integer.parseInt(parts[16]);

                driver = new Driver(firstName, lastName, address, gender, DOB, height, weight, eyeColor, expDate,
                    classType, hasGlasses, SSN, DLN, username, password, isSuspended, violationPoints);

                addDriver(driver);
            }
        }
    }

    public void storeLinkedList()
    {
        ListNode current = adminList;
        while(current != null)
        {
            Admin admin = (Admin)current.getValue();
            adminLinkedList.add(admin);
            current = current.getNext();
        }

        current = driverList;
        while(current != null)
        {
            Driver driver = (Driver)current.getValue();
            driverLinkedList.add(driver);
            current = current.getNext();
        }        
    }

    public void addAdmin(Admin admin)
    {
        ListNode node = new ListNode(admin, null);
        ListNode current;

        if(adminList == null)
            adminList = node;
        else{
            current = adminList;
            while(current.getNext() !=null)
                current = current.getNext();
            current.setNext(node);
        }
    }

    public void addDriver(Driver driver)
    {
        ListNode node = new ListNode(driver, null);
        ListNode current;

        if(driverList == null)
            driverList = node;
        else{
            current = driverList;
            while(current.getNext() !=null)
                current = current.getNext();
            current.setNext(node);
        }
    }

    public void start() throws Exception
    {
        String input;

        //read information from .txt files
        inputAdminStream();
        inputDriverStream();
        storeLinkedList();

        System.out.println("---------------WELCOME TO WILLIAM'S DRIVER LICENSE SYSTEM----------------");
        System.out.print("Are you an (A)Admininstrator or a (D)Driver?: ");
        input = Keyboard.readString();
        System.out.println();

        if(input.equalsIgnoreCase("A"))
            adminLogIn();
        else if(input.equalsIgnoreCase("D"))
            driverLogIn();
        else
            System.out.println("Invalid input");        
    }

    public void adminLogIn() throws Exception
    {
        String username, password;
        ListNode current = adminList;
        Admin tempAdmin;
        Admin fillerAdmin = new Admin(null, null);
        Boolean isMatch = false;

        System.out.println("---------------------------------ADMIN LOGIN---------------------------------");
        System.out.print("Username: ");
        username = Keyboard.readString();
        System.out.print("Password: ");
        password = Keyboard.readString();         

        tempAdmin = new Admin(username, password);

        while(current != null)
        {
            if(tempAdmin.compare(tempAdmin, (Admin)current.getValue()) == true){
                isMatch = true;
                break;
            }
            else
                current = current.getNext();
        }

        if(isMatch == true){
            adminInterface();   
        }
        else
            System.out.println("Incorrect username or password!");

    }

    public void adminInterface() throws Exception
    {
        String input;
        Driver newDriver;
        boolean done = false;
        DateFormat df = new SimpleDateFormat("EEE MMM dd kk:mm:ss z yyyy", Locale.ENGLISH);

        System.out.println("-----------------------------------WELCOME!-----------------------------------");
        System.out.print("(1)Un/Suspend Drivers, (2)Add Drivers, (3)List and Sort drivers, (4)Print Driver List, (5) Check Messages From Drivers, (6)Quit: ");
        input = Keyboard.readString();

        while(done == false){
            if(input.equals("1")){
                suspendDriver();
            }

            else if(input.equals("2")){
                try{
                    System.out.print("Please Add the driver in comma form: ");
                    input = Keyboard.readString();

                    String[] parts = input.split(",");

                    String firstName = parts[0];
                    String lastName = parts[1];
                    String address = parts[2];
                    String gender = parts[3];
                    Date DOB = df.parse(parts[4]);
                    int height = Integer.parseInt(parts[5]);
                    int weight = Integer.parseInt(parts[6]);
                    String eyeColor = parts[7];
                    Date expDate = df.parse(parts[8]);
                    String classType = parts[9];
                    boolean hasGlasses = Boolean.parseBoolean(parts[10]);
                    String SSN = parts[11];
                    String DLN = parts[12];
                    String username = parts[13];
                    String password = parts[14];
                    boolean isSuspended = Boolean.parseBoolean(parts[15]);
                    int violationPoints = Integer.parseInt(parts[16]);

                    newDriver = new Driver(firstName, lastName, address, gender, DOB, height, weight, eyeColor, expDate,
                        classType, hasGlasses, SSN, DLN, username, password, isSuspended, violationPoints);
                    driverLinkedList.add(newDriver);
                }
                catch(Exception e){
                    System.out.println("Invalid input.");
                }
            }

            else if(input.equals("3")){
                sortDrivers();
            }
            else if(input.equals("4")){
                System.out.println();

                System.out.println("-------------------------------------DRIVER LIST--------------------------------------");
                for (int i = 0; i < driverLinkedList.size(); i++) {
                    System.out.println((Driver)driverLinkedList.get(i));
                }

                System.out.println();
            }

            else if(input.equals("5")){
                inputMessage();
            }
            else
                done = true;

            if(done == false){
                System.out.print("(1)Un/Suspend Drivers, (2)Add Drivers, (3)List and Sort drivers, (4)Print Driver List, (5) Check Messages From Drivers, (6)Quit: ");
                input = Keyboard.readString();
            }

        }
    }

    public void sortDrivers(){
        String input;
        boolean done = false;
        System.out.println();
        System.out.println("------------------------HOW WOULD YOU LIKE TO SORT YOUR DRIVERS?------------------------");
        System.out.print("(1)Last Name, (2)Gender, (3)DOB, (4)Weight, (5)Height, (6)Print Driver List, (7)Back: ");
        input = Keyboard.readString();

        while(done == false){
            if(input.equals("1")){
                Collections.sort(driverLinkedList, new Comparator<Driver>() {
                        public int compare(Driver driver1, Driver driver2) {
                            String lastDriver1 = driver1.getLastName();
                            String lastDriver2 = driver2.getLastName();
                            if (lastDriver1.compareTo(lastDriver2) > 0)
                                return 1;
                            else if (lastDriver1.compareTo(lastDriver2) < 0)
                                return -1;
                            else
                                return 0;
                        }
                    });
                System.out.println("SORT COMPLETED!");
            }
            else if(input.equals("2")){
                Collections.sort(driverLinkedList, new Comparator<Driver>() {
                        public int compare(Driver driver1, Driver driver2) {
                            String genderDriver1 = driver1.getGender();
                            String genderDriver2 = driver2.getGender();
                            if (genderDriver1.compareTo(genderDriver2) > 0)
                                return 1;
                            else if (genderDriver1.compareTo(genderDriver2) < 0)
                                return -1;
                            else
                                return 0;
                        }
                    });
                System.out.println("SORT COMPLETED!");
            }
            else if(input.equals("3")){
                Collections.sort(driverLinkedList, new Comparator<Driver>() {
                        public int compare(Driver driver1, Driver driver2) {
                            Date DOBDriver1 = driver1.getDOB();
                            Date DOBDriver2 = driver2.getDOB();
                            if (DOBDriver1.after(DOBDriver2) == true)
                                return 1;
                            else if (DOBDriver1.before(DOBDriver2) == true)
                                return -1;
                            else
                                return 0;
                        }
                    });
                System.out.println("SORT COMPLETED!");
            }
            else if(input.equals("4")){
                Collections.sort(driverLinkedList, new Comparator<Driver>() {
                        public int compare(Driver driver1, Driver driver2) {
                            int weightDriver1 = driver1.getWeight();
                            int weightDriver2 = driver2.getWeight();
                            if (weightDriver1 > weightDriver2)
                                return 1;
                            else if (weightDriver1 < weightDriver2)
                                return -1;
                            else
                                return 0;
                        }
                    });
                System.out.println("SORT COMPLETED!");
            }

            else if(input.equals("5")){
                Collections.sort(driverLinkedList, new Comparator<Driver>() {
                        public int compare(Driver driver1, Driver driver2) {
                            int heightDriver1 = driver1.getHeight();
                            int heightDriver2 = driver2.getHeight();
                            if (heightDriver1 > heightDriver2)
                                return 1;
                            else if (heightDriver1 < heightDriver2)
                                return -1;
                            else
                                return 0;
                        }
                    });
                System.out.println("SORT COMPLETED!");
            }

            else if(input.equals("6")){
                System.out.println();

                System.out.println("-------------------------------------DRIVER LIST--------------------------------------");
                for (int i = 0; i < driverLinkedList.size(); i++) {
                    System.out.println((Driver)driverLinkedList.get(i));
                }

                System.out.println();
            }

            else{
                System.out.println("-----------------------------------MAIN MENU-----------------------------------");
                done = true;
            }

            if(done == false){
                System.out.print("(1)Last Name, (2)Gender, (3)DOB, (4)Weight, (5)Height, (6)Print Driver List, (7)Go Back: ");
                input = Keyboard.readString();        
            }
        }
    }

    public void suspendDriver(){
        int input;
        boolean done = false;
        Driver targetDriver;

        System.out.println();
        System.out.println("-----------------------SUSPEND DRIVER-----------------------");
        System.out.print("(1)Select driver to Un/Suspend, (2)Print Driver List, (3) Back: ");
        input = Keyboard.readInt();

        while(done == false){
            if(input == 1){
                System.out.print("Which Driver?: ");
                input = Keyboard.readInt();
                targetDriver = driverLinkedList.get(input);
                driverLinkedList.remove(input);
                targetDriver.reverseSuspension();
                driverLinkedList.add(input,targetDriver);   

                if(targetDriver.getSuspension() == false)            
                    System.out.println(targetDriver.getFirstName() + " has been unsuspended!");

                else               
                    System.out.println(targetDriver.getFirstName() + " has been suspended!");          
            }

            else if(input == 2){
                System.out.println();

                System.out.println("-------------------------------------DRIVER LIST--------------------------------------");
                for (int i = 0; i < driverLinkedList.size(); i++) {
                    System.out.println((Driver)driverLinkedList.get(i));
                }

                System.out.println();
            }

            else{
                System.out.println("-----------------------------------MAIN MENU-----------------------------------");
                done = true;
            }

            if(done == false){
                System.out.print("(1)Select driver to Un/Suspend, (2)Print Driver List, (3) Back: ");
                input = Keyboard.readInt();        
            }
        }
    }

    public void driverLogIn() throws Exception
    {
        String username, password;
        int index = -1;
        Driver driver = (Driver)driverLinkedList.get(0);

        System.out.println("------------------------------DRIVER LOGIN------------------------------");
        System.out.print("Username: ");
        username = Keyboard.readString();
        System.out.print("Password: ");
        password = Keyboard.readString();                 

        for(int i = 0; i < driverLinkedList.size(); i++)
        {
            if(driver.compare(username,password, (Driver)driverLinkedList.get(i)) == true)
                index = i;
        }

        if(index == -1)
        System.out.println("Incorrect pasword or username.");
        else if(driverLinkedList.get(index).getSuspension() == true)
            System.out.println("Your account is locked!");
        else 
            driverInterface(index);
       
            

    }

    public void driverInterface(int index) throws Exception
    {        
        boolean done = false;
        String input;

        System.out.println();
        System.out.println("-------------------------------WELCOME!-------------------------------");
        System.out.print("(1)View Profile, (2)Renew License,(3)Send Message to Administrator (4)Quit: ");
        input = Keyboard.readString();

        while(done == false)
        {
            if(input.equals("1")){
                System.out.println();
                System.out.println("-----------------------------YOUR PROFILE-----------------------------");
                System.out.println((Driver)driverLinkedList.get(index));
                System.out.println();
            }

            else if(input.equals("2"))
                renewLicense(index);

            else if(input.equals("3"))
                outputMessage();
            else
                done = true;

            if(done == false){
                System.out.print("(1)View Profile, (2)Renew License,(3)Send Message to Administrator, (4)Quit: ");
                input = Keyboard.readString();
            }           
        }
    }

    public void outputMessage() throws Exception
    {
        String body, title;
        System.out.println("-----------------------------FEED BACK-----------------------------");
        System.out.print("Please title your message: ");
        title = Keyboard.readString();
        System.out.print("Please write your message: ");
        body = Keyboard.readString();

        byte[] bytes = body.getBytes();
        OutputStream os = new FileOutputStream(title + ".txt");
        os.write(bytes);
        os.close();
        os.flush();
        System.out.println("Your message has been sent to the Administrators!");
    }

    public void inputMessage() throws Exception
    {
        LinkedList<String> targetFiles = new LinkedList();
        String[] allFiles;
        int numTargetFiles = 0;
        File directory, targetFile;
        String fileName, input,str;
        boolean done = false;
        
        directory = new File(System.getProperty("user.dir"));
        
        System.out.println();
        System.out.println("-----------------------------MESSAGES-----------------------------");
        System.out.print("(1)Check Messages,(2)Select Message, (3)Delete Message (4)Back: ");
        input = Keyboard.readString();
        while(done == false)
        {
            if(input.equals("1")){
               allFiles = directory.list();
                
                targetFiles.clear();
                for(int i = 0; i < allFiles.length; i++)
                {
                    fileName = allFiles[i];

                    if(fileName.contains(".txt") && !fileName.equals("driverList.txt") && !fileName.equals("adminList.txt")&& !fileName.equals("README.txt")){
                        targetFiles.add(allFiles[i]);
                        numTargetFiles++;
                    }
                }

                System.out.println();
                System.out.println("---------------------------NEW MESSAGES---------------------------");
                for (int i = 0; i < targetFiles.size(); i++) {
                    System.out.println(targetFiles.get(i));
                }
                System.out.println();
            }

            else  if(input.equals("2")){                
                System.out.print("Which message would you like to read?: ");
                input = Keyboard.readString();

                for(int i = 0; i < targetFiles.size(); i ++){
                    if(input.equals(targetFiles.get(i))){
                        targetFile = new File(input);    
                        BufferedReader inputReader = new BufferedReader(new FileReader(targetFile));
                        System.out.println();
                        System.out.println("-------------------------" + input + "--------------------------");
                        while((str = inputReader.readLine()) != null){
                            System.out.println(str);
                        }
                        System.out.println();
                        inputReader.close();
                    }                    
                }
            }

            else if(input.equals("3")){             
                System.out.print("Which message would you like to delete?: ");
                input = Keyboard.readString();
                for(int i = 0; i < targetFiles.size(); i ++){
                    if(input.equals(targetFiles.get(i))){
                        targetFile = new File(input);
                        targetFile.setWritable(true);
                        targetFile.delete();
                        System.out.println(input + " was deleted!");
                    }
                }
            }
            else {
                System.out.println("-----------------------------------MAIN MENU-----------------------------------");
                done = true;
            }

            if(done == false){
                System.out.print("(1)Check Messages,(2)Select Message, (3)Delete Message (4)Back: ");
                input = Keyboard.readString();
            }
        }      
    }

    public void renewLicense(int index)
    {     
        int pastHours, newHours, renewalHours, input, pastMinute, pastSecond;
        Date pastDate, newDate = new Date();

        pastDate = driverLinkedList.get(index).getExpirDate();
        Calendar c = Calendar.getInstance();
        c.setTime(pastDate);

        System.out.println("--------------------------RENEW YOUR LICNESE--------------------------");      
        System.out.print("How many years would you like to renew your license by?: ");
        input = Keyboard.readInt();

        c.add(Calendar.YEAR, input);
        newDate = c.getTime();

        driverLinkedList.get(index).setExpirDate(newDate);  

        System.out.println("You renewed your license " + input + " years from " + pastDate + " to " + newDate+ "!");
    }
}