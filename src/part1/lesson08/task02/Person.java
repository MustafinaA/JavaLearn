package part1.lesson08.task02;

import java.util.List;

public class Person {
    private List<Integer> list;
    private String name;
    private Company company;


    public Person(List<Integer>  list,String name, Company company) {
        this.list = list;
        this.name = name;
        this.company = company;
    }

    @Override
    public String toString() {
        return "Person{" +
                "list=" + list +
                ", name='" + name + '\'' +
                ", company=" + company +
                '}';
    }
}
