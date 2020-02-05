package ru.vnevzorov.HumansContainerTask;

import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import java.io.*;
import java.util.*;

@Component
@Profile("workWithHash")
public class HumanContainerHash implements Container {
    private String path = "C:\\IProjects\\HumansContainerTask\\src\\main\\resources\\humans.txt";
    private Map<String, Human> container = new TreeMap<>();

    @PostConstruct
    private void fillAllHumans() {
        try (BufferedReader reader = new BufferedReader(new FileReader(path))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] humanParameters = line.split(",");

                Human human = new Human();
                human.setId(Integer.parseInt(humanParameters[0]));
                human.setName(humanParameters[1]);
                human.setAge(Integer.parseInt(humanParameters[2]));

                container.put(humanParameters[0], human);
            }
        }catch (IOException e) {
            e.printStackTrace();
        }
        printAll();
    }

    @PreDestroy
    private void preDestroy() {
        File oldFile = new File(path);
        File newFile = new File("C:\\IProjects\\HumansContainerTask\\src\\main\\resources\\temp.txt");

        StringBuilder content = new StringBuilder();
        for (Map.Entry<String, Human> entry : container.entrySet()) {
            content.append(entry.getValue().getId() + "," + entry.getValue().getName() + "," + entry.getValue().getAge());
            content.append("\r\n");
        }

        try (FileWriter writer = new FileWriter(newFile)) {
            writer.write(content.toString());
        } catch (IOException e) {
            e.printStackTrace();
        }

        oldFile.delete();
        newFile.renameTo(oldFile);
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
