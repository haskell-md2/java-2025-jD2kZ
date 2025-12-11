class PaymentProcessor {
    public String processDirect(String cardInfo, double amount) {
        return "Платеж " + amount + " обработан (прямой ввод)";
    }
    
    public String processSecure(String token) {
        return "Платеж обработан через токен: " + token;
    }
}

class PaymentResult {
    private String message;
    
    public PaymentResult(String message) {
        this.message = message;
    }
    
    public String getMessage() { return message; }
    
    @Override
    public String toString() {
        return message;
    }
}

class PaymentService {
    private final PaymentProcessor processor;

    public PaymentService(PaymentProcessor processor) {
        this.processor = processor;
    }
    @Deprecated(since = "2.1", forRemoval = true)
    public PaymentResult processCreditCard(String cardNumber, String expiryDate, String cvv, double amount) {
        String cardInfo = "Карта: " + cardNumber.substring(cardNumber.length() - 4) + " " + expiryDate;
        String result = processor.processDirect(cardInfo, amount);
        return new PaymentResult(result);
    }

    public PaymentResult processPayment(String token) {
        String result = processor.processSecure(token);
        return new PaymentResult(result);
    }
}

public class Payment {
    public static void main(String[] args) {
        PaymentProcessor processor = new PaymentProcessor();
        PaymentService paymentService = new PaymentService(processor);
        
        System.out.println("=== Демонстрация deprecated метода ===\n");

        System.out.println("1. Устаревший метод:");
        PaymentResult oldResult = paymentService.processCreditCard("4111111111111111", "12/25", "123", 1000.0);
        System.out.println("Результат: " + oldResult);
        System.out.println();

        System.out.println("2. Новый метод:");
        PaymentResult newResult = paymentService.processPayment("tok_secure_abc123");
        System.out.println("Результат: " + newResult);
        System.out.println();
    }
}