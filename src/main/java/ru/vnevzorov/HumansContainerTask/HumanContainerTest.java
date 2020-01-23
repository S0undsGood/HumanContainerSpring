package ru.vnevzorov.HumansContainerTask;

import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.io.*;
import java.util.Comparator;
import java.util.LinkedHashSet;
import java.util.Set;

@Component
@Profile("test")
public class HumanContainerTest implements Container {
    private Set<Human> container = new LinkedHashSet<>();

    @PostConstruct
    private void fillAllHumans() {
        Human human1 = new Human(2, "Vasya", 20);
        Human human2 = new Human(1, "Masha", 21);
        Human human3 = new Human(5, "Katya", 19);
        Human human4 = new Human(3, "Katya", 25);
        Human human5 = new Human(4, "Victor", 18);

        container.add(human1);
        container.add(human2);
        container.add(human3);
        container.add(human4);
        container.add(human5);

        printAll();
    }

    public void printAll() {
        container.forEach(System.out::println);
    }

    public void addHuman(String name, int age) {
        int nextID = getLastID() + 1;
        Human human = new Human(nextID, name, age);

        printAll();
    }

    private int getLastID() {
        int lastID = 0;
        int currentID;

        for (Human human : container) {
            currentID = human.getId();
            if (lastID < currentID) {
                lastID = currentID;
            }
        }
        return lastID;
    }

    public void printAllById() {
        container.stream().sorted(Comparator.comparing(Human::getId)).forEach(System.out::println);
    }

    public void printAllSorted() {
        container.stream().sorted(Comparator.comparing(Human::getName).thenComparing(Human::getAge)).forEach(System.out::println);
    }

    public void changeHumanParameters(int humansID, String newName, int newAge) {
        for (Human human : container) {
            if (human.getId() == humansID) {
                human.setName(newName);
                human.setAge(newAge);
                break;
            }
        }
        printAll();
    }

    public void deleteHuman(int humansID) {
        for (Human human : container) {
            if (human.getId() == humansID) {
                container.remove(human);
                break;
            }
        }
        printAll();
    }

    public Human getHumanById(int id) throws NullPointerException {
        for (Human human : container) {
            if (human.getId() == id) {
                return human;
            }
        }
        return null;
    }
}
