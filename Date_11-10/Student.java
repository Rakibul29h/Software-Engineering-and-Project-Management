class Student {
    int age = 20; // Instance variable

    void display() {
        int marks = 85; // Local variable

        System.out.println("Instance Variable (age): " + age);
        System.out.println("Local Variable (marks): " + marks);
    }

    public static void main(String[] args) {
        Student s = new Student();

        System.out.println("Student Object Created");
        System.out.println("Object Identity Hash Code: "
                + System.identityHashCode(s));

        s.display();
    }
}
