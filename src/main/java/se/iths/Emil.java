package se.iths;

import entity.StudentEntity;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;

import java.util.List;

public class Emil {

    private static EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory("default");
    private static EntityManager entityManager = entityManagerFactory.createEntityManager();
    public static void main(String[] args) {
        saveStudent();
        List<StudentEntity> students = getAllStudents();
        for (StudentEntity student : students) {
            System.out.println(student.getStudentName());
        }

        }

    private static List<StudentEntity> getAllStudents() {
        return entityManager.createQuery("SELECT students FROM StudentEntity students ", StudentEntity.class).getResultList();

    }

    private static void saveStudent () {
        StudentEntity student = new StudentEntity();
        student.setStudentName("Lionel Messi");
        student.setAge(35);
        student.setAddress("Bellamar, Rosario");
        entityManager.getTransaction().begin();
        entityManager.persist(student);
        entityManager.getTransaction().commit();
    }

}

//TODO Student