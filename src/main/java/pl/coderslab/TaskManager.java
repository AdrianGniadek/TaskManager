package pl.coderslab;

import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.math.NumberUtils;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

public class TaskManager {
    static String[][] tasks;
    static String[] options = {"add", "remove", "list", "exit"};

    public static void main(String[] args) {
        tasks = loadData("tasks.csv");
        displayOptions(options);

        Scanner scanner = new Scanner(System.in);
        String input;

        do {
            input = scanner.nextLine();

            switch (input) {
                case "add":
                    addTask();
                    saveTabToFile("tasks.csv", tasks);
                    break;
                case "remove":
                    removeTask();
                    saveTabToFile("tasks.csv", tasks);
                    break;
                case "list":
                    printTab(tasks);
                    break;
                case "exit":
                    break;
                default:
                    System.out.println("Please select a correct option.");
            }

            displayOptions(options);
        } while (!input.equals("exit"));
        System.out.println(ConsoleColors.RED + "Bye, bye.");
    }

    public static void displayOptions(String[] tab) {
        System.out.println(ConsoleColors.BLUE + "Please select an option: " + ConsoleColors.RESET);
        for (String option : tab) {
            System.out.println(option);
        }
    }

    public static String[][] loadData(String fileName) {
        Path path = Paths.get(fileName);
        if (!Files.exists(path)) {
            System.out.println("File does not exist.");
            System.exit(0);
        }

        String[][] tab = null;
        try {
            List<String> strings = Files.readAllLines(path);
            if (!strings.isEmpty())
                tab = new String[strings.size()][strings.get(0).split(",").length];

            for (int i = 0; i < strings.size(); i++) {
                String[] split = strings.get(i).split(",");
                if (split.length <= tab[i].length) {
                    System.arraycopy(split, 0, tab[i], 0, split.length);
                } else {
                    System.out.println("Invalid data format in the file.");
                    System.exit(0);
                }
            }
        } catch (IOException e) {
            System.out.println("Error reading file: " + e.getMessage());
            System.exit(0);
        }
        return tab;
    }

    public static void printTab(String[][] tab) {
        for (int i = 0; i < tab.length; i++) {
            System.out.print(i + " : ");
            for (int j = 0; j < tab[i].length; j++) {
                System.out.print(tab[i][j] + " ");
            }
            System.out.println();
        }
    }

    public static void addTask() {
        Scanner scanner = new Scanner(System.in);

        System.out.println("Adding new tasks");
        System.out.print("Add task description: ");
        String taskName = scanner.nextLine();
        System.out.print("Add task due date: ");
        String taskDescription = scanner.nextLine();
        System.out.print("Is your task important? true/false ");
        String taskPriority = scanner.nextLine();
        String[] newTask = {taskName, taskDescription, taskPriority};
        tasks = Arrays.copyOf(tasks, tasks.length + 1);
        tasks[tasks.length - 1] = newTask;
    }

    public static void saveTabToFile(String fileName, String[][] tab) {
        Path path = Paths.get(fileName);
        try {
            List<String> lines = new ArrayList<>();
            for (String[] row : tab) {
                lines.add(String.join(",", row));
            }
            Files.write(path, lines);
        } catch (IOException e) {
            System.out.println("Error saving file: " + e.getMessage());
        }
    }

    public static boolean isNumberGreaterEqualZero(String input) {
        if (NumberUtils.isParsable(input)) {
            return Integer.parseInt(input) >= 0;
        }
        return false;
    }

    private static void removeTask() {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Enter the index of the task to remove: ");
        String input = scanner.nextLine();
        int index = Integer.parseInt(input);

        if (tasks != null && index >= 0 && index < tasks.length) {
            tasks = ArrayUtils.remove(tasks, index);
        } else {
            System.out.println("Invalid index");
        }
    }
}
