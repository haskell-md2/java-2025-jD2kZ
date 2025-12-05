public class GarbageDemo {
    public static void main(String[] args) {
        Object obj1 = new Object(); // Object #1
        Object obj2 = new Object(); // Object #2
        obj1 = obj2; // На Object #1 теперь нет ссылок - он стал мусором
        obj2 = null; // Аналогично для Object #2
        // Object #1 и  Object #2 будут удалены сборщиком мусора
    }
}
