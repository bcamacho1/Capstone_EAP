

public class Procedure {
    private int id;
    private String path;
    private String proc; //a description of the procedure
    
    public Procedure(){}
    
    public Procedure(int procid, String url, String desc){
        id = procid;
        path = url;
        proc = desc;
    }
    
    public void setID(int procid){
        //sets the id
        id = procid;
    }
    
    public int getID(){
        //gets the id
        return id;
    }
    
    public void setProcedure(String pro){
        //sets the procedure
        proc = pro;
    }
    
    public String getProcedure(){
        //gets the procedure
        return proc;
    }
    
    public void setURL(String pth){
        //sets the url/ path
        path = pth;
    }
    
    public String getURL(){
        //gets the url/ path
        return path;
    }
    
    public String toString(){
        return getURL() + ", " + getProcedure();
    }
}
