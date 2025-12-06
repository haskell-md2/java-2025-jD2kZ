import java.io.FileInputStream;

public class StreamFixDemo {
    public static void main(String[] args) throws Exception {

        for (int i = 0; i < 1000; i++) {
            try (FileInputStream fis = new FileInputStream("data.txt")) {
                byte[] data = fis.readAllBytes();
                System.out.println("Iteration " + i + ": " + data.length);
            }
            // fis.close() вызван автоматически,
            // даже если внутри блока произошло исключение
        }
    }
}

