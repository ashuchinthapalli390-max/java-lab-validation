import java.sql.*;
public final class JdbcDelete {
 public static int delete(Connection c,int id)throws SQLException{try(PreparedStatement s=c.prepareStatement("DELETE FROM users WHERE id=?")){s.setInt(1,id);return s.executeUpdate();}}
 public static void main(String[] args)throws SQLException{if(args.length!=1)throw new IllegalArgumentException("Usage: JdbcDelete <user-id>");int id=Integer.parseInt(args[0]);try(Connection c=Database.connect()){System.out.println("Rows deleted: "+delete(c,id));Database.display(c);}catch(SQLException e){System.err.println("Database error: "+e.getMessage());throw e;}}
}
