package part1.lesson08.task02;

public class Person {
    private String name;
    private Company company;

    public Person(String name, Company company) {
        this.name = name;
        this.company = company;
    }

    @Override
    public String toString() {
        return "Person{" +
                "name='" + name + '\'' +
                ", company=" + company +
                '}';
    }
}
