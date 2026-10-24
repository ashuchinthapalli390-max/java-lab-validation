import java.util.concurrent.CountDownLatch;
public final class DaemonThreadDemo {
 public static void main(String[] args)throws InterruptedException{
  CountDownLatch started=new CountDownLatch(1);
  Thread daemon=new Thread(()->{System.out.println("Daemon thread running: "+Thread.currentThread().isDaemon());started.countDown();try{while(true)Thread.sleep(1000);}catch(InterruptedException e){Thread.currentThread().interrupt();}});
  daemon.setDaemon(true);System.out.println("Daemon status before start: "+daemon.isDaemon());daemon.start();started.await();System.out.println("Main completed; daemon does not keep JVM alive");
 }
}
