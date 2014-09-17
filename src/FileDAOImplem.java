
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;


public class FileDAOImplem implements FileDAO{
    private Connection connection;  //a connection to the database
    private Statement statement;    //an SQL statement

    @Override
    public Procedure getProcedure(int procID) throws SQLException{
        String query = "SELECT * FROM capstone.procedure WHERE ProcID=" + procID;
        ResultSet rs = null;
        Procedure proc = new Procedure();
        try {
            //gets a connection and creates a statement
            connection = ConnectionFactory.getConnection();
            statement = connection.createStatement();
            rs = statement.executeQuery(query);
            if(rs.next())
                //builds a procedure from the database information
                proc.setProcedure(rs.getString("ProcDesc"));
                proc.setURL(rs.getString("Path"));
        }catch(SQLException exe){
            System.out.println("Could not get the Procedure. "
                    + exe.toString());
        }finally {
            //close all connections
            DbUtil.close(rs);
            DbUtil.close(statement);
            DbUtil.close(connection);
        }
        return proc;
    }

    @Override
    public boolean isProcedureValid(int procID) throws SQLException {
        String query = "SELECT * FROM capstone.procedure WHERE ProcID= " + procID;
        ResultSet rs = null;
        boolean valid = false;
        try {
            //retrieves a connection with the above statement
            connection = ConnectionFactory.getConnection();
            statement = connection.createStatement();
            rs = statement.executeQuery(query);
            if(rs.next())//if the query return something the staffmember exists
                valid = rs.getString("Path") != null;
        }catch(SQLException exe){
            System.out.println("Could not validate the Procedure. "
                    + exe.toString());
        }finally {
            //close all connections
            DbUtil.close(rs);
            DbUtil.close(statement);
            DbUtil.close(connection);
        }
        return valid;
    }
    
    @Override
    public boolean addProcedure(Procedure proc) throws SQLException{
        String addProcQuery = "INSERT INTO capstone.procedure (ProcID, ProcDesc, "
                    + "Path) VALUES (" + proc.getID() + ",'" + proc.getProcedure() + 
                "','" + proc.getURL() + "')";
        int affected = 0;
        try {
            connection = ConnectionFactory.getConnection();
            PreparedStatement ps;
            ps = connection.prepareStatement(addProcQuery);
            affected = ps.executeUpdate(addProcQuery);
            ps.close();
            DbUtil.close(connection);
                 
        }catch (SQLException exe){
            System.out.println("Could not add the Procedure to the database. "
                    + exe.toString());
        }
        return affected > 0;//executes the statement and if it is successful affected > 0
    }
    
    @Override
    public CapstoneImage getImage(int imageID) throws SQLException{
        String query = "SELECT * FROM capstone.images WHERE ImageID=" + imageID;
        ResultSet rs = null;
        CapstoneImage img = new CapstoneImage();
        try {
            //retrieves a connection with the above statement
            connection = ConnectionFactory.getConnection();
            statement = connection.createStatement();
            rs = statement.executeQuery(query);
            //builds an image from the database information
            if(rs.next()){
                img.setURL(rs.getString("Path"));
                img.setImageName(rs.getString("Image"));
            }
        }catch(SQLException exe){
            System.out.println("Could not get the Image. "
                    + exe.toString());
        }finally {
            //closes all connections
            DbUtil.close(rs);
            DbUtil.close(statement);
            DbUtil.close(connection);
        }
        return img;
    }

    @Override
    public boolean isImageValid(int imageID) throws SQLException {
        String query = "SELECT * FROM capstone.images WHERE ImageID= " + imageID;
        ResultSet rs = null;
        boolean valid = false;
        try {
            connection = ConnectionFactory.getConnection();
            statement = connection.createStatement();
            rs = statement.executeQuery(query);
            if(rs.next())//if the query return something the staffmember exists
                valid = rs.getString("Path") != null;
        }catch(SQLException exe){
            System.out.println("Could not validate the Image. "
                    + exe.toString());
        }finally {
            //closes all connections
            DbUtil.close(rs);
            DbUtil.close(statement);
            DbUtil.close(connection);
        }
        return valid;
    }
    
    @Override
    public boolean addImage(CapstoneImage img) throws SQLException{
        String addImageQuery = "INSERT INTO capstone.images (ImageID, Image, "
                    + "Path) VALUES (" + img.getID() + ",'" + img.getImageName() + 
                "','" + img.getURL() + "')";
        int affected = 0;
        try {
            connection = ConnectionFactory.getConnection();
            PreparedStatement ps;
            ps = connection.prepareStatement(addImageQuery);
            affected = ps.executeUpdate(addImageQuery);
            ps.close();
            DbUtil.close(connection);
                 
        }catch (SQLException exe){
            System.out.println("Could not add the Image to the database. "
                    + exe.toString());
        }
        return affected > 0;//executes the statement and if it is successful affected > 0
    }
    
}
