import java.util.concurrent.CountDownLatch;
public final class AliveJoinDemo {
 public static void main(String[] args)throws InterruptedException{
  CountDownLatch release=new CountDownLatch(1);Thread t=new Thread(()->{try{release.await();System.out.println("Worker completed");}catch(InterruptedException e){Thread.currentThread().interrupt();}});
  System.out.println("Before start: "+t.isAlive());t.start();System.out.println("After start: "+t.isAlive());release.countDown();t.join();System.out.println("After join: "+t.isAlive());
 }
}
