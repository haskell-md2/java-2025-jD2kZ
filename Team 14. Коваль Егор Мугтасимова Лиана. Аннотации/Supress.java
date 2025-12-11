import java.util.*;

public class Supress {

    @SuppressWarnings("rawtypes")
    public List convertOldData() {
        List rawList = new ArrayList();
        rawList.add("старые данные");
        return rawList;
    }

    @SuppressWarnings("unchecked")
    public List<String> processLegacyData(List rawList) {
        List<String> strings = (List<String>) rawList;
        return strings;
    }

    @SuppressWarnings("deprecation")
    public void useOldMethod() {
        OldClass old = new OldClass();
        old.deprecatedMethod();
    }

    @SuppressWarnings({"rawtypes", "unchecked"})
    public List<String> complexLegacyOperation() {
        List rawData = getRawDataFromLegacySystem();
        List<String> result = (List<String>) rawData;
        return result;
    }

    public void processData() {
        @SuppressWarnings("unchecked")
        List<String> data = (List<String>) getUntypedData();
        
        System.out.println("Обработано: " + data.size() + " элементов");
    }

    private List getRawDataFromLegacySystem() {
        List list = new ArrayList();
        list.add("данные 1");
        list.add("данные 2");
        return list;
    }
    
    private Object getUntypedData() {
        return Arrays.asList("A", "B", "C");
    }
    
    public static void main(String[] args) {
        Supress demo = new Supress();
        
        System.out.println("=== Демонстрация @SuppressWarnings ===\n");

        List rawData = demo.convertOldData();
        System.out.println("1. Raw types: " + rawData);

        List<String> strings = demo.processLegacyData(rawData);
        System.out.println("2. Unchecked: " + strings);

        demo.useOldMethod();

        List<String> result = demo.complexLegacyOperation();
        System.out.println("4. Multiple: " + result);

        demo.processData();
    }
}

class OldClass {
    @Deprecated
    public void deprecatedMethod() {
        System.out.println("3. Устаревший метод выполнен");
    }
    
    public void newMethod() {
        System.out.println("Новый метод");
    }
}