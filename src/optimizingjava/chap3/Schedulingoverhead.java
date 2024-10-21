package optimizingjava.chap3;

public class Schedulingoverhead {
    public static void main(String[] args) throws InterruptedException {
        long start = System.currentTimeMillis();
        for( int i = 0; i < 1000; i ++ ){
            Thread.sleep(1);
        }
        long end = System.currentTimeMillis();
        System.out.println("Millis elapsed: " + ( end - start ) );
    }
}
