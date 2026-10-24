import java.sql.*;
public final class Database {
 public static Connection connect()throws SQLException{return DriverManager.getConnection(System.getenv().getOrDefault("JDBC_URL","jdbc:mysql://localhost:3306/mydatabase"),System.getenv().getOrDefault("JDBC_USER","root"),System.getenv().getOrDefault("JDBC_PASSWORD",""));}
 public static void display(Connection c)throws SQLException{try(Statement s=c.createStatement();ResultSet rows=s.executeQuery("SELECT id,username,email FROM users ORDER BY id")){while(rows.next())System.out.printf("User ID: %d, Username: %s, Email: %s%n",rows.getInt("id"),rows.getString("username"),rows.getString("email"));}}
}
