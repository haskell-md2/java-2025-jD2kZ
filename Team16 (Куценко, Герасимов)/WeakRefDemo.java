import java.lang.ref.WeakReference;

public class WeakRefDemo {
    public static void main(String[] args) {
        // Сильная ссылка
        Object strong = new Object();
        // Слабая ссылка
        WeakReference<Object> weak = new WeakReference<>(new Object());

        System.gc();

        System.out.println("Strong reference alive: " + (strong != null)); // true
        System.out.println("Weak reference alive: " + (weak.get() != null)); // false
    }
}

