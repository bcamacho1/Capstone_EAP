
import java.util.Collections;
import java.util.LinkedList;

public class ContactList {
 private LinkedList<StaffMember> contacts;
 
  public void ContactList(){
  contacts = new LinkedList();
 }
  
  public void ContactList(ContactList cl){
  contacts = cl.getContacts();
 }
 
  public void add (StaffMember sm)
  {
    contacts.add(sm);
  }
  
  public void remove (StaffMember sm)
  {
    contacts.remove(sm);
  }
  
  public LinkedList getContacts(){
      return contacts;
  }
  
  public StaffMember pop()
  {
    return contacts.pop();
  }
  
  public boolean isEmpty(){
      return contacts.isEmpty();
  }
  
  public void sort(){
      Collections.sort(contacts);
  }
  
  public ContactList clone(){
    return (ContactList) contacts.clone();
    //redo this as well
  }
  
  
  public String toString(){
    return contacts.toString();
  }
}