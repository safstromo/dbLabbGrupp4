package se.iths;

import entity.CourseEntity;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;
import jakarta.persistence.Query;

import java.util.List;
import java.util.Scanner;

public class Robert {

    private static final Scanner scanner = new Scanner(System.in);


    public static void main(String[] args) {

        boolean quit = false;
        printActions();
        while (!quit) {
            System.out.println("\nVälj (9 för att visa val):");
            int action = scanner.nextInt();
            scanner.nextLine();

            switch (action) {
                case 0 -> {
                    System.out.println("\nStänger ner...");
                    quit = true;
                }
                case 1 -> showTabells(); //check
                case 2 -> showOneCourse();  //check
                case 3 ->  searchingNamn();//check
                case 4 -> searchingRoom();//check
                case 5 -> newCourse(); // check
                case 6 -> updateCourseInput(); //check
                case 7 -> deleteCourse(); //check
                case 8 -> countCourse(); //check
                case 9 -> printActions();
            }
        }
    }

    private static void printActions() {
        System.out.println("\nVälj:\n");
        System.out.println("""
                0  - Stäng av
                1  - Visa alla tabeller
                2  - Visa en kurs
                3  - Söka kurs genom namn
                4  - Söka kurs genom room
                5  - Lägg till en kurs
                6  - Uppdatera en kurs
                7  - Ta bort en kurs
                8  - Räkna hur många kurs finns
                9  - Visa en lista över alla val.""");
    }

    private static void showOneCourse() {
        EntityManager entityManager = getEntityManager();

        System.out.println("Skriv ID att se vilken kurs vill du se: ");
        int input = scanner.nextInt();
        CourseEntity courseEntity = entityManager.find(CourseEntity.class, input);

        printCourse(courseEntity);
    }

    private static void printCourse(CourseEntity courseEntity) {
        System.out.println("Course ID = " + courseEntity.getCourseId());
        System.out.println("Course Name = " + courseEntity.getCourseName());
        System.out.println("room = " + courseEntity.getRoom());
        System.out.println("----------------------------");
    }

    private static EntityManager getEntityManager() {
        EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory("default");
        return entityManagerFactory.createEntityManager();
    }

    private static void searchingNamn() {

        EntityManager entityManager = getEntityManager();

        System.out.println("Skriv vilken namn vill du söka: ");
        String input = scanner.nextLine();

        Query query = entityManager.createQuery("SELECT course FROM CourseEntity course WHERE course.courseName ='" + input + "'");

        List<CourseEntity> list = query.getResultList();

        for (CourseEntity b : list) {
            printCourse(b);
        }
    }

    private static void searchingRoom() {
        EntityManager entityManager = getEntityManager();

        System.out.println("Skriv vilken room vill du söka: ");
        String input = scanner.nextLine();
        Query query = entityManager.createQuery("SELECT course FROM CourseEntity course WHERE course.room ='" + input + "'");

        List<CourseEntity> list = query.getResultList();

        for (CourseEntity b : list) {
            printCourse(b);
        }
    }

    private static void showTabells() {
        EntityManager entityManager = getEntityManager();

        Query query = entityManager.createQuery("SELECT course FROM CourseEntity course ");

        List<CourseEntity> list = query.getResultList();

        for (CourseEntity b : list) {
            printCourse(b);
        }

    }

    private static void newCourse() {
        System.out.println("Skriv kurs namn: ");
        String inputName = scanner.nextLine();

        System.out.println("Skriv in kurs rum: ");
        String inputRum = scanner.nextLine();

        newCourseInput(inputName, inputRum);
    }


    private static void newCourseInput(String inputName, String inputRum) {
        EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory("default");
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        entityManager.getTransaction().begin();


        CourseEntity courseEntity = new CourseEntity();


        courseEntity.setCourseName(inputName);
        courseEntity.setRoom(inputRum);


        entityManager.persist(courseEntity);
        entityManager.getTransaction().commit();
        entityManager.close();
        entityManagerFactory.close();

        System.out.println("Du har lagt till en courseEntity");

    }


    private static void updateCourseInput() {

        System.out.println("Skriv vilken kurs vill du uppdatera");
        int updateID = scanner.nextInt();
        scanner.nextLine();
        updateCourse(updateID);
    }

    private static void updateCourse(int updateID) {
        EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory("default");
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        entityManager.getTransaction().begin();

        CourseEntity courseEntity = entityManager.find(CourseEntity.class, updateID);

        System.out.println("Skriv kurs namn:");
        String updateName = scanner.nextLine();
        courseEntity.setCourseName(updateName);


        System.out.println("Skriv in kurs rum: ");
        String updateRum = scanner.nextLine();
        courseEntity.setRoom(updateRum);

        entityManager.persist(courseEntity);
        entityManager.getTransaction().commit();
        entityManager.close();
        entityManagerFactory.close();

        System.out.println("Du har uppdaterat kurs");

    }

    private static void deleteCourse() {
        EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory("default");
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        entityManager.getTransaction().begin();

        System.out.println("Vilken kurs vill du ta bort");
        int deleteInput = scanner.nextInt();

        CourseEntity courseEntity = entityManager.find(CourseEntity.class, deleteInput);

        entityManager.remove(courseEntity);
        entityManager.getTransaction().commit();
        entityManager.close();
        entityManagerFactory.close();

        System.out.println("Du har tagit bort kurs");
    }

    private static void countCourse() {

        EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory("default");
        EntityManager entityManager = entityManagerFactory.createEntityManager();

        Query query = entityManager.createQuery("SELECT COUNT(b.courseId) FROM CourseEntity b");

        System.out.println("Så mycket finns kurserna finns: \n" + query.getSingleResult());

    }

}
