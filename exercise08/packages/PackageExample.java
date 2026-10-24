import com.example.Calculator;
public final class PackageExample {
 public static void main(String[] args){Calculator c=new Calculator();System.out.println("Addition: "+c.add(5,3));System.out.println("Subtraction: "+c.subtract(5,3));System.out.println("Multiplication: "+c.multiply(5,3));System.out.println("Division: "+c.divide(10,2));try{c.divide(10,0);}catch(ArithmeticException e){System.out.println("Division Error: "+e.getMessage());}}
}
