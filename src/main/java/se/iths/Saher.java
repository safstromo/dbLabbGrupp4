package se.iths;

import entity.ClassEntity;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;
import jakarta.persistence.Query;

import java.util.List;
import java.util.Scanner;

public class Saher {
    //TODO CLASS
    private static Scanner sc = new Scanner(System.in);

    public static void main(String[] args) {
        menu();
    }

    public static void menu() {
        System.out.println("Welcome to the CLASS DATABASE \n" +
                "Choose from the options below \n" +
                "1 - View all the classes/programs available in the database \n" +
                "2 - Add a new class/program \n" +
                "3 - Update an existing class/program \n" +
                "4 - Delete a class/program from database \n" +
                "5 - Search for a class/program \n" +
                "6 - Number of classes/programs in the database");
        int inputChoice = sc.nextInt();
        switch (inputChoice) {
            case 1 -> showAllClasses();
            case 2 -> detailsOfClassToInput();
            case 3 -> updateClass();
            case 4 -> deleteClass();
            case 5 -> searchClass();
            case 6 -> numberOfClasses();
        }
    }

    private static void numberOfClasses() {
        EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory("default");
        EntityManager entityManager = entityManagerFactory.createEntityManager();

        Query query = entityManager.createQuery("SELECT COUNT(class.classId) FROM ClassEntity class");

        System.out.println("There are " + query.getSingleResult() + " available in our database");
    }

    private static void searchClass() {
        System.out.println("Input name of class/program to search");
        String inputClassName = sc.nextLine();

        EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory("default");
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        entityManager.getTransaction().begin();

        Query query = entityManager.createQuery("SELECT class FROM ClassEntity class where class.className = '" + inputClassName + "'");

        List listOfClasses = query.getResultList();
        if (listOfClasses.isEmpty()) {
            System.out.println("No classes available to show");
        } else {
            listOfClasses.forEach(System.out::println);
        }
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
        EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory("default");
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        entityManager.getTransaction().begin();
        ClassEntity newClass = new ClassEntity();

        newClass.setClassName(className);
        newClass.setDuration(classDuration);
        newClass.setClassSchoolIdfk(classSchoolID);


        entityManager.persist(newClass);
        entityManager.getTransaction().commit();
        entityManager.close();
        entityManagerFactory.close();

        System.out.println("New class/program successfully added!");
    }

    private static void showAllClasses() {
        EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory("default");
        EntityManager entityManager = entityManagerFactory.createEntityManager();

        Query query = entityManager.createQuery("SELECT classes FROM ClassEntity classes");

        List listOfClasses = query.getResultList();
        if (listOfClasses.isEmpty()) {
            System.out.println("No classes available to show");
        } else {
            listOfClasses.forEach(System.out::println);
        }
    }

    private static void updateClass() {
        showAllClasses();
        System.out.println("Enter the ID of the class/program to update");
        int classIdToUpdate = sc.nextInt();
        sc.nextLine();

        EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory("default");
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        entityManager.getTransaction().begin();
        ClassEntity classEntity = entityManager.find(ClassEntity.class, classIdToUpdate);

        System.out.println("What would you like to update? \n" +
                "1- Class name \n" +
                "2- Duration of the class \n" +
                "3- School to which the class belongs");
        int inputChoice = sc.nextInt();

        switch (inputChoice) {
            case 1:
                System.out.println("Write the new name for the class");
                String newClassName = sc.nextLine();
                classEntity.setClassName(newClassName);

                break;
            //updateClassName();
            case 2:
                System.out.println("Write the new duration for the class");
                int newClassDuration = sc.nextInt();
                classEntity.setDuration(newClassDuration);

                break; //updateClassDuration();
            case 3:
                System.out.println("Which school does the class/program belong to? Enter the new school ID: ");
                int newClassSchoolID = sc.nextInt();
                classEntity.setClassSchoolIdfk(newClassSchoolID);

                break; //updateClassSchoolID();
        }

        entityManager.persist(classEntity);
        entityManager.getTransaction().commit();
        entityManager.close();
        entityManagerFactory.close();

        System.out.println("Class successfully updated!");

    }

    private static void updateClassSchoolID() {

    }

    private static void updateClassDuration() {

    }

    private static void updateClassName() {

    }

    private static void deleteClass() {
        System.out.println("Enter the ID of the class to delete");
        int classIdToDelete = sc.nextInt();
        sc.nextLine();

        EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory("default");
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        entityManager.getTransaction().begin();
        ClassEntity classEntity = entityManager.find(ClassEntity.class, classIdToDelete);
        entityManager.remove(classEntity);
        entityManager.getTransaction().commit();
        entityManager.close();
        entityManagerFactory.close();
        System.out.println("Class successfully deleted!");
    }
}
