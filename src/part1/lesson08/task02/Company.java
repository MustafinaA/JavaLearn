package part1.lesson08.task02;

public class Company {
    private String name;
    private String address;

    public Company(String name, String address) {
        this.name = name;
        this.address = address;
    }

    @Override
    public String toString() {
        return "Company{" +
                "name='" + name + '\'' +
                ", address='" + address + '\'' +
                '}';
    }
}
