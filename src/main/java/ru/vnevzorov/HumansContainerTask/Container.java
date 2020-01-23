package ru.vnevzorov.HumansContainerTask;

public interface Container {
    void addHuman(String name, int age);

    void deleteHuman(int id);

    void changeHumanParameters(int id, String name, int age);

    void printAll();

    Human getHumanById(int id);

    void printAllById();

    void printAllSorted();
}
