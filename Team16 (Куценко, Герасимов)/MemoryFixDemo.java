public class MemoryFixDemo {
    public static void main(String[] args) {
        System.out.println("Fixed memory demo");

        for (int i = 0; i < 20; i++) {
            byte[] data = new byte[1 * 1024 * 1024]; // 1 MB
            // Объект не сохраняется - может быть собран GC
            System.out.println("Allocated " + (i + 1) + " MB");
        }

        System.out.println("Finished fixed demo");
    }
}

