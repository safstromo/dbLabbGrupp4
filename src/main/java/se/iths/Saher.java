package se.iths;

import entity.ClassEntity;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;
import jakarta.persistence.Query;

import java.util.List;
import java.util.Scanner;

public class Saher {

    private static final Scanner sc = new Scanner(System.in);
    static EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory("default");

    public static void main(String[] args) {
        menu();
    }

    public static void menu() {
        int inputChoice;
        System.out.println("Welcome to the CLASS/PROGRAM DATABASE");
        while(true) {
            System.out.println("Press 0 to see menu");
            inputChoice = sc.nextInt();

            switch (inputChoice) {
                case 0 -> printMenuOptions();
                case 1 -> showAllClasses();
                case 2 -> detailsOfClassToInput();
                case 3 -> detailsOfClassToUpdate();
                case 4 -> detailsOfClassToDelete();
                case 5 -> detailsOfClassToSearch();
                case 6 -> numberOfClasses();
                case 7 -> exitProgram();
                default -> System.out.println("Invalid choice. Try again.");
            }
        }
    }

    private static void printMenuOptions() {
        System.out.println("""
                Choose from the options below
                1 - View all the classes/programs available in the database
                2 - Add a new class/program
                3 - Update an existing class/program
                4 - Delete a class/program from database
                5 - Search for a class/program
                6 - Number of classes/programs available in the database""");
    }

    private static void showAllClasses() {
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        Query query = entityManager.createQuery("SELECT classes FROM ClassEntity classes");

        List<ClassEntity> listOfClasses = query.getResultList();
        printListOfClasses(listOfClasses);
    }

    private static void detailsOfClassToInput() {
        System.out.println("Input name of class/program: ");
        String inputName = sc.nextLine();
        System.out.println("Input duration of the class/program: ");
        int durationInput = sc.nextInt();
        sc.nextLine();
        System.out.println("Which school does the class/program belong to? Enter the school ID: ");
        int schoolIDInput = sc.nextInt();
        addNewClass(inputName, durationInput, schoolIDInput);
        sc.nextLine();
    }

    private static void addNewClass(String className, int classDuration, int classSchoolID) {
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        entityManager.getTransaction().begin();
        ClassEntity newClass = new ClassEntity();

        newClass.setClassName(className);
        newClass.setDuration(classDuration);
        newClass.setClassSchoolIdfk(classSchoolID);

        entityManager.persist(newClass);
        handleEntityManager(entityManager);

        System.out.println("New class/program successfully added!");
    }

    private static void detailsOfClassToUpdate() {
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        showAllClasses();
        System.out.println("Enter the ID of the class/program to update");
        int classIdToUpdate = sc.nextInt();
        sc.nextLine();

        entityManager.getTransaction().begin();
        ClassEntity classEntity = entityManager.find(ClassEntity.class, classIdToUpdate);

        int inputChoice = getInputChoice();

        switch (inputChoice) {
            case 1 -> updateClassName(classEntity);
            case 2 -> updateClassDuration(classEntity);
            case 3 -> updateClassSchoolID(classEntity);
        }
        entityManager.persist(classEntity);
        handleEntityManager(entityManager);

        System.out.println("Class successfully updated!");

    }

    private static int getInputChoice() {
        System.out.println("""
                What would you like to update?
                1- Class name
                2- Duration of the class
                3- School to which the class belongs""");
        return sc.nextInt();
    }

    private static void updateClassName(ClassEntity classEntity) {
        System.out.println("Write the new name for the class");
        String newClassName = sc.nextLine();
        classEntity.setClassName(newClassName);

    }

    private static void updateClassDuration(ClassEntity classEntity) {
        System.out.println("Write the new duration for the class");
        int newClassDuration = sc.nextInt();
        classEntity.setDuration(newClassDuration);
    }

    private static void updateClassSchoolID(ClassEntity classEntity) {
        System.out.println("Which school does the class/program belong to? Enter the new school ID: ");
        int newClassSchoolID = sc.nextInt();
        classEntity.setClassSchoolIdfk(newClassSchoolID);
    }

    private static void detailsOfClassToDelete() {
        System.out.println("Enter the ID of the class to delete");
        int classIdToDelete = sc.nextInt();
        sc.nextLine();

        deleteClass(classIdToDelete);
    }

    private static void deleteClass(int classIdToDelete) {
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        entityManager.getTransaction().begin();
        ClassEntity classEntity = entityManager.find(ClassEntity.class, classIdToDelete);
        entityManager.remove(classEntity);
        handleEntityManager(entityManager);

        System.out.println("Class/program successfully deleted!");
    }

    private static void detailsOfClassToSearch() {
        System.out.println("Input name of class/program to search");
        String inputClassName = sc.nextLine();
        sc.nextLine();
        searchClass(inputClassName);
    }

    private static void searchClass(String inputClassName) {
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        entityManager.getTransaction().begin();
        Query query = entityManager.createQuery("SELECT class FROM ClassEntity class WHERE class.className = '" + inputClassName + "'");
        var listOfClasses = query.getResultList();
        printListOfClasses(listOfClasses);
    }

    private static void numberOfClasses() {
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        Query query = entityManager.createQuery("SELECT COUNT(class.classId) FROM ClassEntity class");
        System.out.println("There are " + query.getSingleResult() + " available in our database");
    }

    private static void printListOfClasses(List listOfClasses) {
        if (listOfClasses.isEmpty()) {
            System.out.println("No classes/programs available to show");
        } else {
            listOfClasses.forEach(System.out::println);
        }
    }

    private static void handleEntityManager(EntityManager entityManager) {
        entityManager.getTransaction().commit();
        entityManager.close();
    }

    private static void exitProgram() {
        entityManagerFactory.close();
    }
}
