package se.iths;

import entity.TeacherEntity;
import jakarta.persistence.*;


import java.util.Scanner;

public class Teacher {
	static Scanner sc = new Scanner(System.in);


	public static void findTeacherByName() {
		System.out.println("Enter the name you want to search for: ");
		String inputName = sc.nextLine();
		EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory("default");
		EntityManager entityManager = entityManagerFactory.createEntityManager();

		Query query = entityManager.createQuery("SELECT teacher FROM TeacherEntity teacher WHERE teacher.teacherName ='" + inputName + "'");
		printQuery(query);

	}

	public static void deleteTeacher() {
		EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory("default");
		EntityManager entityManager = entityManagerFactory.createEntityManager();
		entityManager.getTransaction().begin();
		String input = getTeacherIDFromUser();
		TeacherEntity teacher = entityManager.find(TeacherEntity.class, input);
		System.out.println(teacher.getTeacherName() + " deleted...");
		entityManager.remove(teacher);
		commitSQL(entityManagerFactory, entityManager);


	}

	public static void updateTeacherMenu() {
		String inputId = getTeacherIDFromUser();
		printUpdateSwitch();
		String menuInput = sc.nextLine();
		System.out.println("Enter new value: ");
		String newValue = sc.nextLine();
		updateTeacher(inputId, menuInput, newValue);

	}

	private static String getTeacherIDFromUser() {
		printAllTeachers();
		System.out.println("Enter ID for the teacher: ");
		return sc.nextLine();
	}

	private static void updateTeacher(String teacherId, String menuChoice, String updatedValue) {
		EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory("default");
		EntityManager entityManager = entityManagerFactory.createEntityManager();
		entityManager.getTransaction().begin();
		TeacherEntity teacher = entityManager.find(TeacherEntity.class, teacherId);
		updateChoice(menuChoice, updatedValue, teacher);
		entityManager.persist(teacher);
		commitSQL(entityManagerFactory, entityManager);
		System.out.println(updatedValue + " updated");
	}

	private static void updateChoice(String menuChoice, String updatedValue, TeacherEntity teacher) {
		switch (menuChoice) {
			case "1" -> teacher.setTeacherName(updatedValue);
			case "2" -> teacher.setAddress(updatedValue);
		}
	}


	public static void addTeacher() {
		EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory("default");
		EntityManager entityManager = entityManagerFactory.createEntityManager();
		entityManager.getTransaction().begin();
		TeacherEntity teacher = new TeacherEntity();
		getTeacherValues(teacher);
		entityManager.persist(teacher);
		commitSQL(entityManagerFactory, entityManager);


	}

	private static void getTeacherValues(TeacherEntity teacher) {
		System.out.println("Enter name:");
		String name = sc.nextLine();
		teacher.setTeacherName(name);
		System.out.println("Enter Adress: ");
		String adress = sc.nextLine();
		teacher.setAddress(adress);
		System.out.println(name + " added..");

	}

	public static void printAllTeachers() {
		EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory("default");
		EntityManager entityManager = entityManagerFactory.createEntityManager();

		Query query = entityManager.createQuery("SELECT teachers FROM TeacherEntity teachers");
		printQuery(query);

	}


	private static void commitSQL(EntityManagerFactory entityManagerFactory, EntityManager entityManager) {
		entityManager.getTransaction().commit();
		entityManager.close();
		entityManagerFactory.close();
	}

	private static void printUpdateSwitch() {
		System.out.println("""
				Do you want to update Name or Adress?
				1. Name
				2. Adress
				""");
	}

	private static void printQuery(Query query) {
		var list = query.getResultList();
		if (list.isEmpty())
			System.out.println("Nothing found....");
		else list.forEach(System.out::println);
	}

}
