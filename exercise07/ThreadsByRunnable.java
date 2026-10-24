public final class ThreadsByRunnable {
 public static void main(String[] args)throws InterruptedException{int n=MessageTask.count(args);Thread[] ts={new Thread(new MessageTask("Good Morning",1000,n)),new Thread(new MessageTask("Hello",2000,n)),new Thread(new MessageTask("Welcome",3000,n))};for(Thread t:ts)t.start();for(Thread t:ts)t.join();}
}
