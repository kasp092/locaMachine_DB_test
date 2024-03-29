import DataBase.H2_DB;
import DataBase.H2access;
import entities.*;
import DataBase.DBacces;
import DataBase.FileDB;

import java.sql.SQLException;
import java.util.Scanner;
import java.util.Set;

public class Main {
    private static Scanner scanner;
    private static DBacces access;

    public static void main(String[] args) throws Exception {

        init();

        menu();

    }


    private static void menu() {
        boolean working = true;
        do {
            System.out.println("Select operation:");
            System.out.println("Show entities (1) | Show reports: (2) | Exit (0)");
            String line = scanner.nextLine();
            switch (line) {
                case "1":
                    read();
                    break;
                case "2":
                    getIssues();
                    break;
                case "0":
                    working = false;
                    stop();
                    break;
                default:
                    System.out.println("Wrong input, try again.");
                    break;
            }
        } while (working);

    }

    // получить список ошибок по имени проекта и имени пользователя
    private static void getIssues() {

        System.out.print("\nEnter User name: ");
        String userName = scanner.nextLine();
        System.out.print("Enter Project name: ");
        String projectName = scanner.nextLine();
        Set<Issue> issueList = new DBacces().getList(new Issue());

        issueList.removeIf(issue -> !issue.getProject().equals(projectName)
                || !issue.getUser().equals(userName));

        if (issueList.isEmpty()) {
            System.out.println("\nInformation not found.\n");
        } else {
            System.out.println("Id  -  Description:");
            for (Issue issue : issueList) {
                System.out.println(issue.getId() + "   :  " + issue.getDescription());
            }
            System.out.println("\n");
        }
    }

    private static void read() {
//        получить список наследников класса TableBase
        Set<String> entities = access.getExtended();
        for (String entity : entities) {
            System.out.println(entity);
        }

        System.out.print("\nEnter entity name from the list: ");

//      получить класс по имени
        Class clazz = access.getClazz();

//        получить список сущностей класса
        Set<TableBase> list = null;
        try {
            list = access.getList((TableBase) clazz.newInstance());
            for (TableBase enity : list) {
                System.out.println(enity);
            }
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }

        System.out.println();
    }

    private static void init() {
        FileDB.initData();
        new H2access().init();
        scanner = new Scanner(System.in);
        access = new DBacces();
    }

    private static void stop() {
        scanner.close();
        FileDB.save();
    }
}