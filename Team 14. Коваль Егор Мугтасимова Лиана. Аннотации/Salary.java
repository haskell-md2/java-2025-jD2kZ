class Employee {
    private String name;
    private double salary;

    public Employee(String name, double salary) {
        this.name = name;
        this.salary = salary;
    }

    // Базовая реализация расчета годового бонуса
    public double calculateAnnualBonus() {
        return salary * 0.1; // 10% от зарплаты
    }

    public String getName() {
        return name;
    }
    
    public double getSalary() {
        return salary;
    }
}

class Manager extends Employee {
    private double teamBonus;

    public Manager(String name, double salary, double teamBonus) {
        super(name, salary);
        this.teamBonus = teamBonus;
    }

    // Менеджер получает бонус по-другому: стандартный бонус + бонус за команду
    @Override
    public double calculateAnnualBonus() {
        return super.calculateAnnualBonus() + teamBonus;
    }
    
    public double getTeamBonus() {
        return teamBonus;
    }
}

public class Salary {
    public static void main(String[] args) {
        Employee employee = new Employee("Иван Петров", 50000);
        System.out.println("Сотрудник: " + employee.getName());
        System.out.println("Зарплата: " + employee.getSalary());
        System.out.println("Годовой бонус: " + employee.calculateAnnualBonus());
        System.out.println();

        Manager manager = new Manager("Анна Сидорова", 80000, 15000);
        System.out.println("Менеджер: " + manager.getName());
        System.out.println("Зарплата: " + manager.getSalary());
        System.out.println("Бонус команды: " + manager.getTeamBonus());
        System.out.println("Годовой бонус: " + manager.calculateAnnualBonus());
        System.out.println();

        Employee[] employees = {
            new Employee("Петр Иванов", 45000),
            new Manager("Мария Козлова", 75000, 12000),
            new Employee("Сергей Смирнов", 48000)
        };
        
        System.out.println("Бонусы всех сотрудников:");
        for (Employee emp : employees) {
            System.out.println(emp.getName() + ": " + emp.calculateAnnualBonus());
        }
    }
}