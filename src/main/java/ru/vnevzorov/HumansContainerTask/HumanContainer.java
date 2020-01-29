package ru.vnevzorov.HumansContainerTask;

import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.io.*;
import java.util.Comparator;
import java.util.LinkedHashSet;
import java.util.Set;

@Component
@Profile("production")
public class HumanContainer implements Container {
    private String path = "C:\\IProjects\\HumansContainerTask\\src\\main\\resources\\humans.txt";
    private Set<Human> container = new LinkedHashSet<>();

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

                container.add(human);
            }
        }catch (IOException e) {
            e.printStackTrace();
        }
        printAll();
    }

    public void printAll() {
        container.forEach(System.out::println);
    }

    public void addHuman(String name, int age) {

        try (FileWriter writer = new FileWriter(path, true)) {

            int nextID = getLastID() + 1;
            writer.write("\r\n" + nextID + "," + name + "," + age);

        } catch (IOException e) {
            e.printStackTrace();
        }

        container.clear();

        fillAllHumans();
    }

    private int getLastID() {
        int lastID = 0;
        int currentID;

        try (BufferedReader reader = new BufferedReader(new FileReader(path))) {
            String line;
            while ((line = reader.readLine()) != null) {
                int ageComma = line.indexOf(",");
                currentID = Integer.parseInt(line.substring(0, ageComma));

                if (lastID < currentID) {
                    lastID = currentID;
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
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

        //Читаем файл, вносим изменения и сохраняем содержимое в StringBuilder
        StringBuilder changedContent = new StringBuilder();
        try (BufferedReader reader = new BufferedReader(new FileReader(path))) {
            String line;
            while ((line = reader.readLine()) != null) {
                if (line.contains(humansID + ",")) {
                    int startNameIndex = line.indexOf(",") + 1;
                    int startAgeIndex = line.indexOf(",", startNameIndex);
                    String currentName = line.substring(startNameIndex,startAgeIndex);
                    line = line.replace(currentName, newName);
                    line = line.replace(line.substring(startAgeIndex + 1), "" + newAge);
                }
                changedContent.append(line);
                changedContent.append("\r\n");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        replaceFile(changedContent.toString());

        container.clear();
        fillAllHumans();
    }

    public void deleteHuman(int humansID) {
        StringBuilder changedContent = new StringBuilder();
        try (BufferedReader reader = new BufferedReader(new FileReader(path))) {
            String line;
            while ((line = reader.readLine()) != null) {
                if (!line.contains(humansID + ",")) {
                    changedContent.append(line);
                    changedContent.append("\r\n");
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        replaceFile(changedContent.toString());

        container.clear();
        fillAllHumans();
    }

    private void replaceFile(String content) {
        File oldFile = new File(path);
        File newFile = new File("C:\\IProjects\\HumansContainerTask\\src\\main\\resources\\temp.txt");

        try (FileWriter writer = new FileWriter(newFile)) {
            writer.write(content);
        } catch (IOException e) {
            e.printStackTrace();
        }

        oldFile.delete();
        newFile.renameTo(oldFile);
    }

    public Human getHumanById(int id) {
        for (Human human : container) {
            if (human.getId() == id) {
                return human;
            }
        }
        return null;
    }
}
