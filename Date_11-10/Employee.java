import java.util.Scanner;

public class Employee {
    int salary;

    void display(int salary, int bonus) {
        this.salary = salary;

        System.out.println("\nInside display() Method:");
        System.out.println("Salary (Instance Variable): " + this.salary);
        System.out.println("Bonus (Local Variable): " + bonus);

        // Local inner class
        class Report {
            void show() {
                System.out.println("Report: Employee salary is " + Employee.this.salary);
            }
        }

        Report r = new Report();

        System.out.println("\nReport Object Created");
        System.out.println("Report Identity Hash Code: "
                + System.identityHashCode(r));

        r.show();
    }

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        System.out.print("Enter salary: ");
        int salary = sc.nextInt();

        System.out.print("Enter bonus: ");
        int bonus = sc.nextInt();

        Employee e = new Employee();

        System.out.println("\nEmployee Object Created");
        System.out.println("Employee Identity Hash Code: "
                + System.identityHashCode(e));

        e.display(salary, bonus);

        System.out.println("\nMemory Concept:");
        System.out.println("Employee object -> Heap");
        System.out.println("Report object -> Heap");
        System.out.println("salary -> Instance field");
        System.out.println("bonus -> Local variable in display()");
        System.out.println("show() -> Executes in its own method frame");

        sc.close();
    }
}
