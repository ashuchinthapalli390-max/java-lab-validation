import com.example.Calculator;import java.sql.*;
public final class Checks {
 static int count;static void check(boolean ok,String name){if(!ok)throw new AssertionError(name);count++;System.out.println("PASS "+name);}
 public static void main(String[] args)throws Exception{
  int[] a={-5,0,3,3,10};check(BinarySearch.search(a,-5)==0,"binary first");check(BinarySearch.search(a,10)==4,"binary last");check(BinarySearch.search(a,2)==-1,"binary absent");check(BinarySearch.search(new int[0],1)==-1,"binary empty");int i=BinarySearch.search(a,3);check(i>=0&&a[i]==3,"binary duplicates");check(BinarySearch.search(new int[]{7},7)==0,"binary singleton");
  Calculator c=new Calculator();check(c.add(-2,3)==1,"calculator signed add");check(c.subtract(2,3)==-1,"calculator subtract");check(c.multiply(-2,3)==-6,"calculator multiply");check(c.divide(10,2)==5,"calculator divide");try{c.divide(1,0);throw new AssertionError("zero accepted");}catch(ArithmeticException expected){check(true,"calculator zero divisor");}
  check(TipModel.calculate("1000","15","2").perPerson().toPlainString().equals("575.00"),"tip split");check(TipModel.calculate("0","0","1").total().signum()==0,"tip zero");check(TipModel.calculate("10.01","15","3").perPerson().toPlainString().equals("3.84"),"tip rounding");
  for(String[] v:new String[][]{{"1","10","0"},{"-1","10","1"},{"x","10","1"},{"1","-5","1"},{"1","10","1.5"}}){try{TipModel.calculate(v[0],v[1],v[2]);throw new AssertionError("invalid tip accepted");}catch(IllegalArgumentException expected){check(true,"tip rejects "+String.join("/",v));}}
  try(Connection db=DriverManager.getConnection("jdbc:h2:mem:lab;MODE=MySQL;DB_CLOSE_DELAY=-1","sa","")){
   try(Statement s=db.createStatement()){s.execute("CREATE TABLE users(id INT AUTO_INCREMENT PRIMARY KEY,username VARCHAR(100) NOT NULL,email VARCHAR(255) NOT NULL UNIQUE)");}
   check(JdbcInsert.insert(db,"johndoe","johndoe@example.com")==1,"JDBC insert");check(JdbcInsert.insert(db,"x'); DROP TABLE users; --","literal@example.com")==1,"JDBC parameterized literal");
   try(Statement s=db.createStatement();ResultSet r=s.executeQuery("SELECT COUNT(*) FROM users")){r.next();check(r.getInt(1)==2,"JDBC table intact");}
   System.out.println("JDBC SELECT execution (H2 MySQL mode):");Database.display(db);check(JdbcDelete.delete(db,1)==1,"JDBC existing delete");check(JdbcDelete.delete(db,999)==0,"JDBC missing delete");
   db.setAutoCommit(false);JdbcInsert.insert(db,"temporary","temporary@example.com");db.rollback();db.setAutoCommit(true);
   try(Statement s=db.createStatement();ResultSet r=s.executeQuery("SELECT COUNT(*) FROM users WHERE email='temporary@example.com'")){r.next();check(r.getInt(1)==0,"JDBC rollback");}
   try{JdbcInsert.insert(db,"duplicate","literal@example.com");throw new AssertionError("duplicate accepted");}catch(SQLException expected){check(true,"JDBC constraint failure");}
  }
  System.out.println("ALL "+count+" CHECKS PASSED");
 }
}
