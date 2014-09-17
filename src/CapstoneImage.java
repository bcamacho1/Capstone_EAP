

public class CapstoneImage {
    public int id;
    public String url;
    public String imgName;
    
    public CapstoneImage(){}
    
    public CapstoneImage(int imgid, String path, String name){
        id = imgid;
        url = path;
        imgName = name;
    }
    
    public void setID(int imgid){
        //sets the id
        id = imgid;
    }
    
    public void setImageName(String image){
        //sets the image name
        imgName = image;
    }
    
    public void setURL(String path){
        //sets the path/ url
        url = path;
    }
    
    public String getURL(){
        //gets the url/ path
        return url;
    }
    
    public String getImageName(){
        //gets the image name
        return imgName;
    }
    
    public int getID(){
        //gets the id
        return id;
    }
}
