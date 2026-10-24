import java.sql.*;
public final class JdbcSelect {
 public static void main(String[] args)throws SQLException{try(Connection c=Database.connect()){Database.display(c);}catch(SQLException e){System.err.println("Database error: "+e.getMessage());throw e;}}
}
