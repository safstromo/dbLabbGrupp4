package se.iths;

import entity.ClassEntity;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;
import jakarta.persistence.Query;

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
                "2 - Add a new class/program");
        int inputChoice = sc.nextInt();
        switch (inputChoice) {
            case 1 -> showAllClasses();
        }
    }

    private static void detailsOfclassToInput(){
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

        System.out.println("Class input successful!");
    }

    private static void showAllClasses() {
        EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory("default");
        EntityManager entityManager = entityManagerFactory.createEntityManager();

        Query query = entityManager.createQuery("SELECT classes FROM ClassEntity classes");

        var listOfClasses = query.getResultList();
        if(listOfClasses.isEmpty())
            System.out.println("No classes available to show");
        else listOfClasses.forEach(System.out::println);
    }

    private static void updateClass(){
        System.out.println("Enter the ID of the class to update");
        int classIdToUpdate = sc.nextInt();
        sc.nextLine();
        System.out.println("What would you like to update? \n" +
                "1- Class name \n" +
                "2- Duration of the class \n" +
                "3- School to which the class belongs");
        int inputChoice = sc.nextInt();
        switch (inputChoice) {
            case 1 -> updateClassName();
            case 2 -> updateClassDuration();
            case 3 -> updateClassSchoolID();
        }
        EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory("default");
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        entityManager.getTransaction().begin();
        ClassEntity classEntity = entityManager.find( ClassEntity.class, classIdToUpdate );

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
        System.out.println("Write the new name for the class");
    }

}
