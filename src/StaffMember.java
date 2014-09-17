


public class StaffMember implements Comparable{

    private int employeeID;
    private String firstName;
    private String lastName;
    private String emailAddress;
    private String password;
    private String phone;
    private String position;
    private int isActive;
    
    public StaffMember() {
        employeeID = 1234;
    	firstName = "";
    	lastName = "";
        emailAddress = "";
        password = "1234";
        phone = "5551234";
        position = "staff";
        isActive = 0;
    }
    
    public StaffMember(int id, String first, 
    String last, String passwd, String phoneNumb, String email, String pos) {
        employeeID = id;
    	firstName = first;
    	lastName = last;
        emailAddress = email;
        password = passwd;
        phone = phoneNumb; 
        position = pos;
    }
	
    
    public void setEmployeeID(int id){
        employeeID = id;
    }
	
    /* The setFirstName method sets the employee's first name. */
    public void setFirstName(String fName) {
	firstName = fName;
    }
	
    /* The setLastName method sets the employee's last name. */
    public void setLastName(String lName) {
	lastName = lName;
    }
	
    /* The setEmail method sets the employee's email */
    public void setEmail(String email) {
	emailAddress = email;
    }
	
    public void setPassword(String pword){
        password = pword;
    }
    
    /* The setPhoneNumber method sets the employee's phone number. */
    public void setPhone(String phoneNum) {
	phone = phoneNum;
    }
	
    public void setPosition(String pos){
        position = pos;
    }
    
    public void becomeActive(){
        isActive = 1;
    }
    
    public void becomeInactive(){
        isActive = 0;
    }
	
    public int getEmployeeID(){
        return employeeID;
    }
    /* The getFirstName method gets the employee's first name. */
    public String getFirstName() {
	return firstName;
    }
	
    /* The getLastName method gets the employee's last name. */
    public String getLastName() {
	return lastName;
    }
	
    /* The getEmail method gets the employee's email. */
    public String getEmail() {
    	return emailAddress;
    }
	
    public String getPassword(){
        return password;
    }
    
    /* The getPhoneNumber method gets the employee's phone number. */
    public String getPhone() {
    	return phone;
    }
        
    public String getPosition(){
        return position;
    }
	
    public boolean isActive(){
        return isActive == 1;
    }
    
    @Override
    public String toString(){
        return getLastName() + ", " + getFirstName() + ", " + getPhone() + 
                ", " + getEmail();
    }

    @Override
    public int compareTo(Object o) {
        StaffMember temp = (StaffMember)o;
        return getLastName().compareTo(temp.getLastName());
    }
    
}