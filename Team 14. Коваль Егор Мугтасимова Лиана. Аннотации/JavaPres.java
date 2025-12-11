import java.lang.annotation.*;
import java.lang.reflect.Method;

// Шаг 1: Объявление аннотации
@Retention(RetentionPolicy.RUNTIME) // Доступна во время выполнения
@Target(ElementType.METHOD) // Можно применять только к методам
@interface MyAnnotation {
    String value() default "default value";
    String description() default "";
    int priority() default 1;
}

// Шаг 3: Класс с аннотированными методами
class MyClass {
    @MyAnnotation(
        value = "important method",
        description = "Этот метод выполняет важную операцию",
        priority = 5
    )
    public void importantMethod() {
        System.out.println("Выполняется важный метод");
    }
    
    @MyAnnotation("обычный метод")
    public void regularMethod() {
        System.out.println("Выполняется обычный метод");
    }
    
    @MyAnnotation // Используем значения по умолчанию
    public void defaultMethod() {
        System.out.println("Выполняется метод с аннотацией по умолчанию");
    }
    
    // Метод без аннотации
    public void normalMethod() {
        System.out.println("Этот метод без аннотации");
    }
}

// Шаг 2: Обработчик аннотации
class AnnotationProcessor {
    public static void processAnnotations(Object obj) {
        Class<?> clazz = obj.getClass();
        // getDeclaredMethods() получаем массив всех методов, объявленных в классе
        Method[] methods = clazz.getDeclaredMethods();
        
        for (Method method : methods) {
            // isAnnotationPresent() возвращает true, если аннотация есть на методе
            if (method.isAnnotationPresent(MyAnnotation.class)) {
                // getAnnotation() возвращает объект аннотации из которого можно получить значения
                MyAnnotation annotation = method.getAnnotation(MyAnnotation.class);
                
                System.out.println("Найден метод с аннотацией: " + method.getName());
                System.out.println("Значение: " + annotation.value());
                System.out.println("Описание: " + annotation.description());
                System.out.println("Приоритет: " + annotation.priority());
                System.out.println("---");
                
                try {
                    // invoke() вызывает метод на указанном объекте
                    // obj = объект, на котором вызывается метод
                    method.invoke(obj);
                } catch (Exception e) {
                    // обрабатываем возможные исключения
                    e.printStackTrace();
                }
                System.out.println();
            }
        }
    }
}

// Главный класс для демонстрации
public class JavaPres {
    public static void main(String[] args) {
        // Создаем экземпляр класса, который содержит аннотированные методы
        MyClass myObject = new MyClass();
        
        System.out.println("=== ОБРАБОТКА АННОТАЦИЙ ===");
        System.out.println();
        
        // Запускаем обработку аннотаций для созданного объекта
        AnnotationProcessor.processAnnotations(myObject);
        
        // Дополнительная демонстрация
        demonstrateReflection();
    }
    
    public static void demonstrateReflection() {
        System.out.println("=== ДОПОЛНИТЕЛЬНАЯ ИНФОРМАЦИЯ ===");
        MyClass obj = new MyClass();
        Class<?> clazz = obj.getClass();
        Method[] methods = clazz.getDeclaredMethods();
        
        System.out.println("Все методы класса MyClass:");
        for (Method method : methods) {
            System.out.println("  - " + method.getName() + 
                (method.isAnnotationPresent(MyAnnotation.class) ? " [АННОТИРОВАН]" : ""));
        }
    }
}