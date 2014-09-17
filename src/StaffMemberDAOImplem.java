
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class StaffMemberDAOImplem implements StaffMemberDAO{
    private Connection connection;
    private Statement statement;
 
    public StaffMemberDAOImplem() { }
    
    @Override
    public StaffMember getStaffMember(int employeeId) throws SQLException {
        String query = "SELECT * FROM capstone.staffmember WHERE StaffID = " + employeeId;
        ResultSet rs = null;
        StaffMember staffmember = new StaffMember();
        try {
            connection = ConnectionFactory.getConnection();
            statement = connection.createStatement();
            rs = statement.executeQuery(query);
            if (rs.next()) {//if the staffmember exists
                //build the employee column by colunm
                staffmember = new StaffMember();
                staffmember.setEmployeeID(rs.getInt("StaffID"));
                staffmember.setLastName(rs.getString("LastName"));
                staffmember.setFirstName(rs.getString("FirstName"));
                staffmember.setPhone(rs.getString("Phone"));
                staffmember.setEmail(rs.getString("Email"));
                staffmember.setPosition(rs.getString("Position"));
                if(rs.getInt("Active") == 1)
                    //if the staffmember is active on the database then their
                    //status is set to active
                    staffmember.becomeActive();
                else
                    staffmember.becomeInactive();
                
            }
        }catch(SQLException exe){
            System.out.println("Could not get the Staff Member. "
                    + exe.toString());
        } finally {
            //closes all connections
            DbUtil.close(rs);
            DbUtil.close(statement);
            DbUtil.close(connection);
        }
        return staffmember;
    }

    @Override
    public boolean isStaffMemberValid(String staffName) throws SQLException{
        String query = "SELECT * FROM capstone.staffmember WHERE LastName = '" + staffName + "'";
        ResultSet rs = null;
        boolean valid = false;
        try {
            //retrieves a connection with the above statement
            connection = ConnectionFactory.getConnection();
            statement = connection.createStatement();
            rs = statement.executeQuery(query);
            if(rs.next())//if the query return something the staffmember exists
                valid = rs.getString("Email") != null;
        }catch(SQLException exe){
            System.out.println("Could not validate the Staff Member. "
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
    public StaffMember isStaffMemberAuthenticated(String email, String password) throws SQLException{
        String query = "SELECT * FROM capstone.staffmember WHERE Email = '" + email + "'" 
                + "AND Password = '" + password + "'";
        ResultSet rs = null;
        StaffMember staffmember = new StaffMember();
        try {
            //retrieves a connection with the above statement
            connection = ConnectionFactory.getConnection();
            statement = connection.createStatement();
            rs = statement.executeQuery(query);
            if(rs.next())//if the staffmember exists
                //build the employee column by colunm
                staffmember.setEmployeeID(rs.getInt("StaffID"));
                staffmember.setLastName(rs.getString("LastName"));
                staffmember.setFirstName(rs.getString("FirstName"));
                staffmember.setPassword(rs.getString("Password"));
                staffmember.setPhone(rs.getString("Phone"));
                staffmember.setEmail(rs.getString("Email"));
                staffmember.setPosition(rs.getString("Position"));
                staffmember.becomeActive();//sets staff's status to active
        }catch(SQLException exe){
            System.out.println("Could not authenticate the Staff Member. "
                    + exe.toString());
        }finally {
            //closes all connections
            DbUtil.close(rs);
            DbUtil.close(statement);
            DbUtil.close(connection);
        }
        return staffmember;
    }

    @Override
    public StaffMember fetchByName(String lastName) throws SQLException{
        String query = "SELECT * FROM capstone.staffmember WHERE LastName= '" + lastName + "'";
        ResultSet rs = null;
        StaffMember staffmember = new StaffMember();
        try {
            //retrieves a connection with the above statement
            connection = ConnectionFactory.getConnection();
            statement = connection.createStatement();
            rs = statement.executeQuery(query);
            if(rs.next()){//if the staffmember exists{
                //build the employee column by colunm
                staffmember.setEmployeeID(rs.getInt("StaffID"));
                staffmember.setLastName(rs.getString("LastName"));
                staffmember.setFirstName(rs.getString("FirstName"));
                staffmember.setPhone(rs.getString("Phone"));
                staffmember.setEmail(rs.getString("Email"));
                staffmember.setPosition(rs.getString("Position"));
                if(rs.getInt("Active") == 1)
                    //if the staffmember is active on the database then their
                    //status is set to active
                    staffmember.becomeActive();
                else
                    staffmember.becomeInactive();
            }
        }catch(SQLException exe){
            System.out.println("Could not fetch the Staff Member from the database. "
                    + exe.toString());
        }finally {
            DbUtil.close(rs);
            DbUtil.close(statement);
            DbUtil.close(connection);
        }
        return staffmember;//returns the requested staffmember
    }

    @Override
    public boolean addStaffMember(StaffMember mem) throws SQLException{
        String addTableSql;
        if(mem.isActive())
            addTableSql = "INSERT INTO capstone.staffmember (StaffID, LastName, "
                    + "FirstName, Email, Phone, Position, Password, Active) VALUES ('"
                    + mem.getEmployeeID() + "', '" + mem.getLastName() + "','" +
                 mem.getFirstName() + "','" + mem.getEmail() + "','" + mem.getPhone() +
                    "', '" + mem.getPosition() + "', '" + mem.getPassword()+ "', 1)";
        else 
            addTableSql = "INSERT INTO capstone.staffmember (StaffID, LastName, "
                    + "FirstName, Email, Phone, Position, Password, Active) VALUES ('"
                    + mem.getEmployeeID() + "', '" + mem.getLastName() + "','" +
                 mem.getFirstName() + "','" + mem.getEmail() + "','" + mem.getPhone() +
                    "', '" + mem.getPosition() + "', '" + mem.getPassword()+ "', 0)";
        
        int affected = 0;
        try {
            connection = ConnectionFactory.getConnection();
            PreparedStatement ps;
            ps = connection.prepareStatement(addTableSql);
            affected = ps.executeUpdate(addTableSql);
            ps.close();
            DbUtil.close(connection);
                 
        }catch (SQLException exe){
            System.out.println("Could not add the Staff Member to the database. "
                    + exe.toString());
        }
        return affected > 0;//executes the statement and if it is successful affected > 0
    }

    @Override
    public boolean isActive(String staff)throws SQLException{
        //uses the method fetch by name and if the staffmember is active 
        //on the database they are active
        return fetchByName(staff).isActive();
    }

    @Override
    public boolean update(StaffMember mem) throws SQLException{
        
        String updateTableSql;
        if(mem.isActive())
            updateTableSql = "UPDATE capstone.staffmember SET LastName = '" + mem.getLastName() +
                "', FirstName = '" + mem.getFirstName() + "', Position = '" + 
                mem.getPosition() + "', Phone = '" + mem.getPhone() + "', Active = 1 " +
                "WHERE StaffID =" + mem.getEmployeeID();
        else 
            updateTableSql = "UPDATE capstone.staffmember SET LastName = '" + mem.getLastName() +
                "', FirstName = '" + mem.getFirstName() + "', Position = '" + 
                mem.getPosition() + "', Phone = '" + mem.getPhone() + "', Active = 0 " +
                "WHERE StaffID =" + mem.getEmployeeID();
        int affected = 0;
        try {
            connection = ConnectionFactory.getConnection();
            PreparedStatement ps;
            ps = connection.prepareStatement(updateTableSql);
            affected = ps.executeUpdate(updateTableSql);
            ps.close();
            DbUtil.close(connection);
        }catch (SQLException exe){
            System.out.println("Could not update the Staff Member. " + exe.toString());
        }
        return affected > 0;//executes the statement and if it is successful affected > 0
    }

    @Override
    public boolean changePassword(StaffMember mem, String password) throws SQLException{
        String changePass = "UPDATE capstone.staffmember SET Password = '"+  password +"' WHERE StaffID = "
                + mem.getEmployeeID();
        int affected = 0;
        try {
            //gets a connection and creates a prepared statement
            connection = ConnectionFactory.getConnection();
            PreparedStatement ps = connection.prepareStatement(changePass);
            affected = ps.executeUpdate(changePass);
            //executes the statement and if it is successful affected > 0
            ps.close();
            DbUtil.close(connection);    
        }catch (SQLException exe){
            System.out.println("Could not change the password of the "
                    + "selected Staff Member. " + exe.toString());
        }
        return affected > 0;//true if affected rows is greater than zero
    }
    
    @Override
    public boolean delete(int empID)throws SQLException{
        String addTableSql = "DELETE FROM capstone.staffmember " +
		"WHERE StaffID = "+  empID;
        int affected = 0;
        try {
            //gets a connection and creates a prepared statement
            connection = ConnectionFactory.getConnection();
            PreparedStatement ps;
            ps = connection.prepareStatement(addTableSql);
            //executes the statement and if it is successful affected is 1
            affected = ps.executeUpdate(addTableSql);
            //closes all connections
            ps.close();
            DbUtil.close(connection);
            //returns true if affected > 0.  
        }catch (SQLException exe){
            System.out.println("Could not delete from the database. " + exe.toString());
        }
        return affected > 0;//executes the statement and if it is successful affected > 0
    }   

    @Override
    public void displayAllRows() throws SQLException{
        String query = "SELECT LastName, FirstName, Position, Phone, Email FROM " +
      "capstone.staffmember ORDER BY LastName";
        try{
            Connection conn = ConnectionFactory.getConnection();
            Statement state = conn.createStatement();
            ResultSet result = state.executeQuery(query);
            System.out.println("Staff Member Table: ");
            while (result.next()) {
                System.out.print(result.getInt("StaffID") + ": ");
                System.out.print(result.getString("LastName") +", ");
                System.out.print(result.getString("FirstName") +", ");
                System.out.print(result.getString("Email") + ", ");
                System.out.print(result.getString("Phone") + ", ");
                System.out.print(result.getString("Position"));
                System.out.println("\n");
            }
        } catch (SQLException e) {
             System.out.println("Could not retrieve the rows. " + e.toString());
        }
    }
}
