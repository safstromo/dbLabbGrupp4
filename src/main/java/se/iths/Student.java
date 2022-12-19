package se.iths;

import entity.StudentEntity;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;
import jakarta.persistence.Query;

import java.util.List;
import java.util.Scanner;

public class Student {

    private static EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory("default");
    private static EntityManager entityManager = entityManagerFactory.createEntityManager();
    private static Scanner sc = new Scanner(System.in);

    public static void studentMenu() {
        boolean quit = false;
        while (!quit) {
            showStudentMenu();
            int action = sc.nextInt();
            sc.nextLine();

            switch (action) {
                case 0 -> showStudentMenu();
                case 1 -> showAllStudents();
                case 2 -> createStudent();
                case 3 -> findStudent();
                case 4 -> updateStudent();
                case 5 -> deleteStudent();
                case 6 -> numberOfStudents();
                case 7 -> quit = true;

            }

        }
    }


    public static void showStudentMenu() {
        System.out.println();
        System.out.println();
        System.out.println("Student Menu");
        System.out.println("===============================");
        System.out.println("0. Show Student menu");
        System.out.println("1. Show all students");
        System.out.println("2. Add new student");
        System.out.println("3. Find students by name");
        System.out.println("4. Update student information");
        System.out.println("5. Remove a student");
        System.out.println("6. Show number of students");
        System.out.println("7. Back to main menu");
        System.out.println("===============================");
    }

    private static void showAllStudents() {
        List<StudentEntity> students = entityManager.createQuery("SELECT students FROM StudentEntity students ", StudentEntity.class).getResultList();
        print(students);

    }


    private static void print (List<StudentEntity> students) {
        for (StudentEntity student : students ) {
           print(student);
        }
    }
    private static void print (StudentEntity student) {
            System.out.println("ID: " + student.getStudentId() + " |"+
                    " Student Name: " + student.getStudentName() +" |"+
                    " Student Age: " + student.getAge() +" |"+
                    " Adress: " + student.getAddress());
        }

    private static void createStudent() {
        StudentEntity student = new StudentEntity();

        System.out.println("Enter the students name: ");
        String studentName = sc.nextLine();
        student.setStudentName(studentName);

        System.out.println("Enter the students age: ");
        int studentAge = sc.nextInt();
        sc.nextLine();
        student.setAge(studentAge);

        System.out.println("Enter the students address: ");
        String studentAddress = sc.nextLine();
        student.setAddress(studentAddress);
        save(student);
    }

    private static void save(StudentEntity student) {
        entityManager.getTransaction().begin();
        entityManager.persist(student);
        entityManager.getTransaction().commit();
    }

    private static void delete(StudentEntity student) {
        entityManager.getTransaction().begin();
        entityManager.remove(student);
        entityManager.getTransaction().commit();
    }

    private static void findStudent() {
        System.out.println("Enter the name of the student you would like to find: ");
        String studentName = sc.nextLine();
        Query query = entityManager.createQuery("SELECT student FROM StudentEntity student WHERE student.studentName LIKE :nameInput", StudentEntity.class);
        query.setParameter("nameInput", "%" + studentName + "%");
        List<StudentEntity> students = query.getResultList();
        print(students);

    }

    private static void updateStudent(){
        showAllStudents();
        System.out.println("Enter ID of the student: ");
        int studentId = sc.nextInt();
        sc.nextLine();
        StudentEntity student = entityManager.find(StudentEntity.class, studentId);
        if (student == null){
            System.out.println("No student found");
            return;
        }
        print(student);
        System.out.println("Type in the new name of the student or press Enter to skip: ");
        String newName = sc.nextLine();
        if (!newName.isEmpty()) {
            student.setStudentName(newName);
        }

        System.out.println("Type in the new age of the student or press Enter to skip: ");
        String newAge = sc.nextLine();

        if (!newAge.isEmpty()) {
            student.setAge(Integer.parseInt(newAge));
        }

        System.out.println("Type in the new address of the student or press Enter to skip: ");
        String newAddress = sc.nextLine();
        if (!newAddress.isEmpty()) {
            student.setAddress(newAddress);
        }
        save(student);
        System.out.println("Student updated!");


    }

    private static void deleteStudent () {
        showAllStudents();
        System.out.println("Enter ID of the student: ");
        int studentId = sc.nextInt();
        sc.nextLine();
        StudentEntity student = entityManager.find(StudentEntity.class, studentId);
        if(student != null) {
            delete(student);
            System.out.println("Student removed!");
        } else {
            System.out.println("No student found!");
        }
    }

    private static void numberOfStudents () {
        Query query = entityManager.createQuery("SELECT COUNT(student.studentId) FROM StudentEntity student");
        System.out.println("There are " + query.getSingleResult() + " available in our database");
    }

}
