package se.iths;

import entity.SchoolEntity;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;
import jakarta.persistence.Query;

import java.util.Scanner;

public class Anton {

    // Todo school
     static Scanner scanner = new Scanner(System.in);
     static EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory("default");

    public static void main(String[] args) {

        schoolmenu();

    }

    private static void schoolmenu() {
        System.out.println("""
                SchoolMenu
                =========
                1. Show all schools
                2. Show one school
                3. Search for a school
                4. Add a new school
                5. Update a school
                6. Delete a school
                7.Show the menu for alternatives
                8.Exit
    """);

        schoolAlternativesToMenu();

    }

    private static void schoolAlternativesToMenu() {
        boolean exist = false;
        while (!exist) {
            System.out.println("Choose 7 to see the alternatives again:");
            int response = scanner.nextInt();

            switch (response) {
                case 1 -> showAllSchools();
                case 2 -> showSchool();
                case 3 -> searchSchools();
                case 4 -> inputToNewSchool();
                case 5 -> inputToUpdateSchool();
                case 6 -> deleteDetails();
                case 7 -> schoolmenu();
                case 8 -> exist = true;


            }

        }

    }

    private static void showAllSchools() {

        //EntityManagerFactory = Persistence.createEntityManagerFactory("default");
        EntityManager entityManager = entityManagerFactory.createEntityManager();

        Query query = entityManager.createQuery("SELECT school FROM SchoolEntity school");
        var list = query.getResultList();
        list.forEach(System.out::println);





    }
    private static void showSchool() {

        EntityManager entityManager = entityManagerFactory.createEntityManager();
        System.out.println("Enter ID of the school you want to see");
        int inputID = scanner.nextInt();
        SchoolEntity schoolEntity = entityManager.find(SchoolEntity.class, inputID);
        System.out.println(schoolEntity.getSchoolId());
        System.out.println(schoolEntity.getSchoolName());
        System.out.println(schoolEntity.getAddress());
    }

    private static void addSchool(String inputName, String inputAdress, int inputID) {


        //EntityManagerFactory = Persistence.createEntityManagerFactory("default");
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        entityManager.getTransaction().begin();

        SchoolEntity schoolEntity = new SchoolEntity();
        schoolEntity.setSchoolName(inputName);
        schoolEntity.setAddress(inputAdress);
        schoolEntity.setSchoolId(inputID);

        entityManager.persist(schoolEntity);
        entitymanagerMethods(entityManager);
        System.out.println(inputName + "added successfully");


    }
    private static void inputToNewSchool() {

        scanner.nextLine();
        System.out.println("Write the name of the school");
        String inputName = scanner.nextLine();
        System.out.println("Write the adress of the school");
        String inputAdress = scanner.nextLine();
        System.out.println("Write the ID");
        int inputID = scanner.nextInt();
        addSchool(inputName,inputAdress,inputID);


    }
    private static void updateSchool(String updateName, String updateAdress,int updateId) {

        EntityManager entityManager = entityManagerFactory.createEntityManager();
        entityManager.getTransaction().begin();
        SchoolEntity schoolEntity = entityManager.find( SchoolEntity.class,updateName);
        schoolEntity.setSchoolName(updateName);
        schoolEntity.setAddress(updateAdress);
        schoolEntity.setSchoolId(updateId);
        entityManager.persist(schoolEntity);
        entitymanagerMethods(entityManager);
    }

    private static void inputToUpdateSchool() {

        System.out.println("Enter the ID number of the school you want to update: ");
        int updateId = scanner.nextInt();
        scanner.nextLine();
        System.out.println("New school: ");
        String updateName = scanner.nextLine();
        System.out.println("New adress: ");
        String updateAdress = scanner.nextLine();
        updateSchool(updateName,updateAdress, updateId);

    }
    private static void searchSchools() {

        EntityManager entityManager = entityManagerFactory.createEntityManager();
        System.out.println("Enter the ID number of the school you are searching for:");
        int searchId = scanner.nextInt();

        Query query = entityManager.createQuery("SELECT school FROM SchoolEntity school WHERE school.schoolId =" + searchId);
        var list = query.getResultList();
        list.forEach(System.out::println);


    }
    private static void deleteSchool(int inputId) {

        EntityManager entityManager = entityManagerFactory.createEntityManager();
        entityManager.getTransaction().begin();
        SchoolEntity schoolEntity = entityManager.find(SchoolEntity.class,inputId);
        entityManager.remove(schoolEntity);
        entitymanagerMethods(entityManager);



    }
    private static void deleteDetails() {

        System.out.println("write the ID of the school you want to remove");
        int inputId = scanner.nextInt();
        deleteSchool(inputId);


    }
    private static void entitymanagerMethods(EntityManager entityManager) {


        entityManager.getTransaction().commit();
        entityManager.close();
        entityManagerFactory.close();
    }


}


