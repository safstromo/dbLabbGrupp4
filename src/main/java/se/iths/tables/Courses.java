package se.iths.tables;

import entity.CourseEntity;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;
import jakarta.persistence.Query;

import java.util.List;
import java.util.Scanner;

public class Courses {


	/*
	Todo
	find course -> show table before
	find name -> show table before
	find room -> show table before
	update course -> show table before
	7 -> show table before -> english
	7 -> man tabort med ID, printa innan
	 */

	private static final Scanner scanner = new Scanner(System.in);


	public static void menu() {
		boolean quit = false;
		while (!quit) {
			printActions();
			int action = scanner.nextInt();
			scanner.nextLine();

			switch (action) {
				case 0 -> printActions();
				case 1 -> showTabells(); //check
				case 2 -> addCourse(); // check
				case 3 -> showOneCourse();  //check
				case 4 -> searchingNamn();//check
				case 5 -> searchingRoom();//check
				case 6 -> updateCourseInput(); //check
				case 7 -> deleteCourse(); //check
				case 8 -> countCourse(); //check
				case 9 -> quit = true;
			}
		}
	}

	private static void printActions() {
		System.out.println("""
				    
				    
				    
				Courses
				===============================
				0. Print Courses menu
				1. Show all courses
				2. Add a course
				3. Find course by course ID
				4. Find course by name 
				5. Find course by room
				6. Update a course
				7. Remove a course
				8. Count number of courses
				9. Back to Main Menu.
				===============================
				""");
	}

	private static void showOneCourse() {
		EntityManager entityManager = getEntityManager();
		System.out.println("Skriv ID att se vilken kurs vill du se: ");
		int input = scanner.nextInt();
		CourseEntity courseEntity = entityManager.find(CourseEntity.class, input);

		printCourse(courseEntity);
	}

	private static void printCourse(CourseEntity courseEntity) {
		System.out.print("\nCourse ID: " + courseEntity.getCourseId() + " | ");
		System.out.print("Course Name: " + courseEntity.getCourseName() + " | ");
		System.out.print("room: " + courseEntity.getRoom());

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

	private static void addCourse() {
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
