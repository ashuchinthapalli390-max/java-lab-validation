public final class ThreadsByExtending {
 static final class MessageThread extends Thread {
  private final MessageTask task;MessageThread(String message,long interval,int count){super(message);task=new MessageTask(message,interval,count);}
  @Override public void run(){task.run();}
 }
 public static void main(String[] args)throws InterruptedException{int n=MessageTask.count(args);Thread[] ts={new MessageThread("Good Morning",1000,n),new MessageThread("Hello",2000,n),new MessageThread("Welcome",3000,n)};for(Thread t:ts)t.start();for(Thread t:ts)t.join();}
}
