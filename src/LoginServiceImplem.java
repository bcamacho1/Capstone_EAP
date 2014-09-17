import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class LoginServiceImplem implements LoginService{
    private Connection connection;
    private Statement statement;
    
    public LoginServiceImplem(){}
    
    @Override
    public boolean login(String email, String password) throws SQLException{
        String query = "SELECT * FROM staffmember WHERE Email= '" + email + "'"
                + " AND Password = '" + password + "'";
        ResultSet rs = null;
        try {
            //builds a connection and statement
            connection = ConnectionFactory.getConnection();
            statement = connection.createStatement();
            rs = statement.executeQuery(query);
            //if the statement returns something
            if(rs.next()){
                //and email and password match the database information, return true
                return(email.equals(rs.getString("Email")) 
                        && password.equals(rs.getString("Password")));
            }
        }finally {
            //close all connections
            DbUtil.close(rs);
            DbUtil.close(statement);
            DbUtil.close(connection);
        }return false;
    }
    
    @Override
    public boolean logout(StaffMember staff){
        staff.becomeInactive();
        String addTableSql = "UPDATE staffmember SET Active = 0 " +
                "WHERE StaffID =" + staff.getEmployeeID();
        int affected = 0;
        try {
            connection = ConnectionFactory.getConnection();
            PreparedStatement ps;
            ps = connection.prepareStatement(addTableSql);
            affected = ps.executeUpdate(addTableSql);
            ps.close();
            DbUtil.close(connection);
            DbUtil.close(statement);
               
        }catch (SQLException exe){
            System.out.println("Could not logout the Staff Member. " + exe.toString());
        }
        return affected > 0;
    }
}
