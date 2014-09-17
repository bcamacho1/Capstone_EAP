import java.sql.SQLException;


public interface LoginService {
    public boolean login(String userName, String password)throws SQLException;
    public boolean logout(StaffMember staff);
}
