package ru.vnevzorov.HumansContainerTask;

import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.io.*;
import java.util.*;

@Component
@Profile("test")
public class HumanContainerTest implements Container {
    private Map<String, Human> container = new TreeMap<>();

    @PostConstruct
    private void fillAllHumans() {
        Human human1 = new Human("Vasya", 20);
        Human human2 = new Human("Masha", 21);
        Human human3 = new Human("Katya", 19);
        Human human4 = new Human("Katya", 25);
        Human human5 = new Human("Victor", 18);

        addHuman(human1);
        addHuman(human2);
        addHuman(human3);
        addHuman(human4);
        addHuman(human5);

        printAll();
    }

    public void printAll() {
        container.entrySet().iterator().forEachRemaining(System.out::println);
    }

    public void addHuman(Human human) {
        int nextID = getLastID() + 1;
        human.setId(nextID);
        container.put("" + nextID, human);
    }

    private int getLastID() {
        int lastID = 0;
        int currentID;

        for (Map.Entry<String, Human> entry : container.entrySet()) {
            currentID = Integer.parseInt(entry.getKey());
            if (currentID > lastID) {
                lastID = currentID;
            }
        }
        return lastID;
    }

    public void printAllById() {
        for (Map.Entry<String, Human> entry1 : container.entrySet()) {
            System.out.println(entry1.getValue().toString());
        }
    }

    public void printAllSorted() {
        container.values().stream().sorted(Comparator.comparing(Human::getName).thenComparing(Human::getAge)).forEach(System.out::println);
    }

    public void changeHumanParameters(int humansID, String newName, int newAge) throws NullPointerException {
        Human human = container.get("" + humansID);
        if (human == null) {
            throw new NullPointerException();
        } else {
            human.setName(newName);
            human.setAge(newAge);
        }
    }

    public void deleteHuman(int humansID) throws NullPointerException {
        container.remove("" + humansID);
    }

    public Human getHumanById(int id) throws NullPointerException {
        return container.get("" + id);
    }
}
