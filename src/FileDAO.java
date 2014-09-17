
import java.sql.SQLException;


public interface FileDAO {
    public boolean addProcedure(Procedure proc)throws SQLException;
    public Procedure getProcedure(int procID)throws SQLException;
    public boolean isProcedureValid(int procID)throws SQLException;
    public boolean addImage(CapstoneImage img)throws SQLException;
    public CapstoneImage getImage(int imageID)throws SQLException;
    public boolean isImageValid(int procID)throws SQLException;
}
