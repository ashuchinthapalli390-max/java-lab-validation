import java.sql.*;
public final class JdbcInsert {
 public static int insert(Connection c,String username,String email)throws SQLException{try(PreparedStatement s=c.prepareStatement("INSERT INTO users(username,email) VALUES (?,?)")){s.setString(1,username);s.setString(2,email);return s.executeUpdate();}}
 public static void main(String[] args)throws SQLException{String name=args.length>0?args[0]:"newuser",email=args.length>1?args[1]:"newuser@example.com";try(Connection c=Database.connect()){System.out.println("Rows inserted: "+insert(c,name,email));Database.display(c);}catch(SQLException e){System.err.println("Database error: "+e.getMessage());throw e;}}
}
