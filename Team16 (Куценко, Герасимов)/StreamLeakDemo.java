import java.io.FileInputStream;

public class StreamLeakDemo {
    public static void main(String[] args) throws Exception {
        for (int i = 0; i < 1000; i++) {
            FileInputStream fis = new FileInputStream("data.txt"); // открыт файл
            byte[] data = fis.readAllBytes();
            System.out.println("Iteration " + i + ": " + data.length);
            // fis.close();    // - забыли закрыть, утечка дескриптора файла
        }
    }
}
