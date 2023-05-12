package pl.coderslab;


public class TaskManager {
    static String[][] tasks;

    public static void main(String[] args) {
        displayOptions();
    }

    public static void displayOptions() {
        System.out.println(ConsoleColors.BLUE + "Please select an option: " + ConsoleColors.RESET);
        System.out.println("add");
        System.out.println("remove");
        System.out.println("list");
        System.out.println("exit");
    }
}