import java.util.ArrayList;import java.util.List;
public final class ProducerConsumer {
 static final class Buffer{
  private int value;private boolean full;
  synchronized void put(int next)throws InterruptedException{while(full)wait();value=next;full=true;System.out.println("Produced: "+next);notifyAll();}
  synchronized int take()throws InterruptedException{while(!full)wait();int next=value;full=false;System.out.println("Consumed: "+next);notifyAll();return next;}
 }
 public static void main(String[] args)throws InterruptedException{
  int count=args.length==0?10:Integer.parseInt(args[0]);if(count<0)throw new IllegalArgumentException("Count must be nonnegative");
  Buffer b=new Buffer();List<Integer> consumed=new ArrayList<>();
  Thread p=new Thread(()->{try{for(int i=1;i<=count;i++)b.put(i);}catch(InterruptedException e){Thread.currentThread().interrupt();}},"producer");
  Thread c=new Thread(()->{try{for(int i=0;i<count;i++)consumed.add(b.take());}catch(InterruptedException e){Thread.currentThread().interrupt();}},"consumer");
  p.start();c.start();try{p.join();c.join();}catch(InterruptedException e){p.interrupt();c.interrupt();throw e;}
  if(consumed.size()!=count)throw new AssertionError("Missing items");for(int i=0;i<count;i++)if(consumed.get(i)!=i+1)throw new AssertionError("Out-of-order item");
  System.out.println("Verified "+count+" items; no loss or duplicates");
 }
}
