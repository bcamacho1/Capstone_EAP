
import java.sql.SQLException;


public interface StaffMemberDAO {
    
    public StaffMember getStaffMember(int employeeId) throws SQLException;
    public boolean isStaffMemberValid(String staffName) throws SQLException;
    public StaffMember isStaffMemberAuthenticated(String email, String password)throws SQLException;
    public StaffMember fetchByName(String name)throws SQLException;
    public boolean addStaffMember(StaffMember staff)throws SQLException;
    public boolean isActive(String staff)throws SQLException;
    public boolean update(StaffMember mem)throws SQLException;
    public boolean changePassword(StaffMember user, String password)throws SQLException;
    public boolean delete(int empID)throws SQLException;
    public void displayAllRows() throws SQLException;
       
}
