package se.iths;

import entity.SchoolEntity;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;
import jakarta.persistence.Query;

import java.util.Scanner;

public class Anton {

    // Todo school
    private static final Scanner scanner = new Scanner(System.in);
    private static final EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory("default");

    private static final EntityManager entityManager = entityManagerFactory.createEntityManager();
    // CRUD
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
            System.out.println("Choose 7 to see the alternatives again");
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
        // EntityManager entityManager = entityManagerFactory.createEntityManager();

        Query query = entityManager.createQuery("SELECT school FROM SchoolEntity");
        var list = query.getResultList();
        list.forEach(System.out::println);





    }
    private static void showSchool() {

        //EntityManagerFactory = Persistence.createEntityManagerFactory("default");
        //EntityManager entityManager = entityManagerFactory.createEntityManager();
        SchoolEntity school = entityManager.find(SchoolEntity.class, 1);

        System.out.println(school.getSchoolId() + school.getSchoolName());
    }

    private static void addSchool(String inputName, String inputAdress, int inputID) {


        //EntityManagerFactory = Persistence.createEntityManagerFactory("default");
        //EntityManager entityManager = entityManagerFactory.createEntityManager();
        entityManager.getTransaction().begin();

        SchoolEntity school = new SchoolEntity();
        school.setSchoolName(inputName);
        school.setAddress(inputAdress);
        school.setSchoolId(inputID);

        entityManager.persist(school);
        entitymanagerMethods();
        System.out.println(inputName + "added successfully");


    }
    private static void inputToNewSchool() {

        System.out.println("Write the name of the school");
        String inputName = scanner.nextLine();
        System.out.println("Write the adress of the school");
        String inputAdress = scanner.nextLine();
        System.out.println("Write the ID");
        int inputID = scanner.nextInt();
        addSchool(inputName,inputAdress,inputID);


    }
    private static void updateSchool(String updateName, String updateAdress,int updateId) {

        entityManager.getTransaction().begin();
        SchoolEntity school = entityManager.find( SchoolEntity.class,updateName);
        school.setSchoolName(updateName);
        school.setAddress(updateAdress);
        school.setSchoolId(updateId);
        entityManager.persist(school);
        entitymanagerMethods();
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

        System.out.println("Enter the ID number of the school you are searching for:");
        int searchId = scanner.nextInt();

        Query query = entityManager.createQuery("SELECT school FROM SchoolEntity WHERE school.schoolID =" + searchId);
        var list = query.getResultList();
        list.forEach(System.out::println);


    }
    private static void deleteSchool(int inputId) {

        entityManager.getTransaction().begin();
        SchoolEntity school = entityManager.find(SchoolEntity.class,inputId);
        entityManager.remove(school);
        entitymanagerMethods();



    }
    private static void deleteDetails() {

        System.out.println("write the ID of the school you want to remove");
        int inputId = scanner.nextInt();
        deleteSchool(inputId);


    }
    private static void entitymanagerMethods() {

        entityManager.getTransaction().commit();
        entityManager.close();
        entityManagerFactory.close();
    }


}


