package se.iths;

import entity.TeacherEntity;
import jakarta.persistence.*;


import java.util.Scanner;

public class Teacher {
	static Scanner sc = new Scanner(System.in);
	static EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory("default");


	public static void teacherMenu() {
		boolean menu = true;
		while (menu) {
			printTeacherMenu();
			switch (sc.nextLine()) {
				case "0" -> printTeacherMenu();
				case "1" -> printAllTeachers();
				case "2" -> addTeacher();
				case "3" -> findTeacherByName();
				case "4" -> updateTeacherMenu();
				case "5" -> deleteTeacher();
				case "6" -> numberOfTeachers();
				case "7" -> {
					entityManagerFactory.close();
					menu = false;
				}
			}
		}
	}


	public static void findTeacherByName() {
		System.out.println("Enter the name you want to search for: ");
		String inputName = sc.nextLine();
		EntityManager entityManager = entityManagerFactory.createEntityManager();

		Query query = entityManager.createQuery("SELECT teacher FROM TeacherEntity teacher WHERE teacher.teacherName ='" + inputName + "'");
		printQuery(query);

	}

	public static void deleteTeacher() {
		EntityManager entityManager = entityManagerFactory.createEntityManager();
		entityManager.getTransaction().begin();
		String input = getTeacherIDFromUser();
		TeacherEntity teacher = entityManager.find(TeacherEntity.class, input);
		System.out.println(teacher.getTeacherName() + " deleted...");
		entityManager.remove(teacher);
		commitSQL(entityManager);


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
		EntityManager entityManager = entityManagerFactory.createEntityManager();
		entityManager.getTransaction().begin();
		TeacherEntity teacher = entityManager.find(TeacherEntity.class, teacherId);
		updateChoice(menuChoice, updatedValue, teacher);
		entityManager.persist(teacher);
		commitSQL(entityManager);
		System.out.println(updatedValue + " updated");
	}

	private static void updateChoice(String menuChoice, String updatedValue, TeacherEntity teacher) {
		switch (menuChoice) {
			case "1" -> teacher.setTeacherName(updatedValue);
			case "2" -> teacher.setAddress(updatedValue);
		}
	}


	public static void addTeacher() {
		EntityManager entityManager = entityManagerFactory.createEntityManager();
		entityManager.getTransaction().begin();
		TeacherEntity teacher = new TeacherEntity();
		getTeacherValues(teacher);
		entityManager.persist(teacher);
		commitSQL(entityManager);


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
		EntityManager entityManager = entityManagerFactory.createEntityManager();

		Query query = entityManager.createQuery("SELECT teachers FROM TeacherEntity teachers");
		printQuery(query);

	}
	private static void numberOfTeachers() {
		EntityManager entityManager = entityManagerFactory.createEntityManager();
		Query query = entityManager.createQuery("SELECT COUNT(class.teacherId) FROM TeacherEntity class");
		System.out.println("There are " + query.getSingleResult() + " available in our database");
	}


	private static void commitSQL(EntityManager entityManager) {
		entityManager.getTransaction().commit();
		entityManager.close();
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


	private static void printTeacherMenu() {
		System.out.println("""
					
				Teacher menu:
				===============================
				0. Show teacher menu
				1. Show all teachers
				2. Add teacher
				3. Search for teacher
				4. Update teacher
				5. Delete teacher
				6. Show number of teachers
				7. Back to main menu
				===============================
				""");
	}
}
