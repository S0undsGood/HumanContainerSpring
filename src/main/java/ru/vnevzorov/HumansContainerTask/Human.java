package ru.vnevzorov.HumansContainerTask;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import java.util.Objects;

@Component
@Scope("prototype")
public class Human {
    private String name;
    private int age;
    private int id;

    public Human() {}

    public Human(String name, int age) {
        this.name = name;
        this.age = age;
    }

    /*public void print() {
        System.out.println("id: " + this.id + "\tname: " + this.name + "\t  age: " + this.age);
    }*/

    public String toString() {
        return "id: " + this.id + "\tname: " + this.name + "\t  age: " + this.age;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Human human = (Human) o;
        return age == human.age &&
                id == human.id &&
                Objects.equals(name, human.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, age, id);
    }
}
