package se.iths;

import entity.ClassEntity;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;

import java.util.Scanner;

public class Saher {
    //TODO CLASS
    private static Scanner sc = new Scanner(System.in);

    public static void menu() {

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


}
