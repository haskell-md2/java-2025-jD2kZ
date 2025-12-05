import java.util.ArrayList;
import java.util.List;

public class MemoryLeakDemo {
    // Статическая коллекция - объекты никогда не удаляются GC
    private static List<byte[]> leak = new ArrayList<>();
    public static void main(String[] args) {
        System.out.println("Memory leak demo");

        for (int i = 0; i < 20; i++) {
            byte[] data = new byte[1 * 1024 * 1024]; // 1 MB
            leak.add(data); // сохраняем в статическую коллекцию - утечка
            System.out.println("Allocated " + (i + 1) + " MB");
        }

        System.out.println("Finished leak demo");
    }
}
