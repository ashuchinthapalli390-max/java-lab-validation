final class MessageTask implements Runnable {
 private final String message;private final long interval;private final int repetitions;
 MessageTask(String message,long interval,int repetitions){this.message=message;this.interval=interval;this.repetitions=repetitions;}
 public void run(){for(int i=0;repetitions==0||i<repetitions;i++){if(Thread.currentThread().isInterrupted())return;System.out.println(message);try{Thread.sleep(interval);}catch(InterruptedException e){Thread.currentThread().interrupt();return;}}}
 static int count(String[] args){if(args.length==0)return 0;int n=Integer.parseInt(args[0]);if(n<1)throw new IllegalArgumentException("Count must be positive");return n;}
}
