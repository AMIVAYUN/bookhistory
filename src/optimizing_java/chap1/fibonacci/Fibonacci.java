package optimizingjava.chap1.fibonacci;

public class Fibonacci {
    /**
     * @param args
     *
     * visualvm 가볍게 써보기 위한 피보나치 수열 top down 로직
     */
    public static void main(String[] args) {
        Fibonacci fib = new Fibonacci();
        long n = 50;
        System.out.println("Fibonacci of " + n + " is " + fib.fib(n));
    }

    public long fib(long n) {
        if (n <= 1) return n;
        return fib(n - 1) + fib(n - 2);
    }
}