package optimizingjava.chap3;

public class Caching{

    private final int ARR_SIZE = 2 * 1024 * 1024;
    private final int[] testData = new int[ ARR_SIZE ];

    private void run(){
        long warmStart = System.currentTimeMillis();
        System.err.println( "Start: " + warmStart );
        for( int i = 0; i < 15000; i ++ ){
            touchEveryLine();
            touchEveryItem();
        }
        long warmdone = System.currentTimeMillis();
        System.err.println( "Warm up Done: " + warmdone);
        System.err.println( "warm up time == " + ( warmdone - warmStart) );
        System.err.println("Item Line");
        for( int i = 0; i < 100; i ++ ){
            long t0 = System.nanoTime();
            touchEveryLine();
            long t1 = System.nanoTime();
            touchEveryItem();
            long t2 = System.nanoTime();
            long elItem = t2 - t1;
            long elLine = t1 - t0;
            double diff = elItem - elLine;
            System.err.println(elItem+ " " + elLine + " " + ( 100 * diff / elLine ) );
        }
    }
    private void touchEveryItem(){
        for( int i = 0; i < testData.length; i ++ ){
            testData[ i ]++;
        }
    }

    private void touchEveryLine(){
        for( int i = 0; i < testData.length; i += 16 ){
            testData[ i ] ++;
        }
    }

    public static void main(String[] args) {
        Caching c = new Caching();
        c.run();
    }
}