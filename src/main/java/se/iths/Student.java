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
            printMenu();
            int action = sc.nextInt();
            sc.nextLine();

            switch (action) {
                case 1 -> createStudent();
                case 2 -> findStudent();
                case 3 -> showAllStudents();
                case 4 -> updateStudent();
                case 5 -> deleteStudent();
                case 6 -> quit = true;

            }

        }
    }


    public static void printMenu() {
        System.out.println();
        System.out.println();
        System.out.println("Student Menu");
        System.out.println("===============================");
        System.out.println("1. Add new student");
        System.out.println("2. Find students by name");
        System.out.println("3. Show all students");
        System.out.println("4. Update student information");
        System.out.println("5. Remove a student");
        System.out.println("6. Exit program");
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
            System.out.println(student.getStudentId() + "|Name: " + student.getStudentName() + ", Age: " + student.getAge() +  ", Address: " + student.getAddress());
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

}

//TODO Student