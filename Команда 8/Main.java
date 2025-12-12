import java.util.*;

public class Main {
    public static void main(String[] args) throws InterruptedException {
        List<byte[]> memory = new ArrayList<>();

        System.out.println("Starting memory allocation...");

        for (int i = 0; i < 100; i++) {

            memory.add(new byte[5 * 1024 * 1024]);

            double result = 0;
            for (int j = 0; j < 1000000; j++) {
                result += Math.sqrt(j);
            }

            Thread.sleep(100);
            System.out.println("Iteration " + i + " completed");
        }
    }
}