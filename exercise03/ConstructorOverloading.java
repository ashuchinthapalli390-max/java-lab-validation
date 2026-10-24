public final class ConstructorOverloading {
 static final class Person {
  private final String name;private final int age;
  Person(){this("Unknown",0);}Person(String name){this(name,0);}Person(String name,int age){this.name=name;this.age=age;}
  void displayDetails(){System.out.println("Name: "+name);System.out.println("Age: "+age);}
 }
 public static void main(String[] args){Person[] p={new Person("Alice",30),new Person("Bob"),new Person()};for(int i=0;i<p.length;i++){System.out.println("Person "+(i+1)+":");p[i].displayDetails();System.out.println();}}
}
