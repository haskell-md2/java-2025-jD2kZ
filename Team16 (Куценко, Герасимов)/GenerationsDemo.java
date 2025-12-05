import java.util.ArrayList;
import java.util.List;

public class GenerationsDemo {
    public static void main(String[] args) {
        List<byte[]> list = new ArrayList<>();

        // Создаём небольшие объекты
        for (int i = 0; i < 30; i++) {
            list.add(new byte[400 * 1024]); // 400 KB
        }

        System.out.println("Finished allocation.");
    }
}
