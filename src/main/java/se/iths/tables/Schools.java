package se.iths.tables;

import entity.SchoolEntity;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;
import jakarta.persistence.Query;

import java.util.Scanner;

public class Schools {

	// Todo school
	static Scanner scanner = new Scanner(System.in);
	static EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory("default");

	private static void schoolmenu() {

		System.out.println("""
					School Menu
					===============================
					0. Print school menu 
					1. Show all schools 
					2. Add a new school
					3. Search for a school
					4. Update a school
					5. Delete a school
					6. Back to main menu
					===============================
					""");


	}

	public static void schoolAlternativesToMenu() {
		boolean exist = false;
		while (!exist) {
			schoolmenu();
			int response = scanner.nextInt();

			switch (response) {
				case 0 -> schoolmenu();
				case 1 -> showAllSchools();
				case 2 -> inputToNewSchool();
				case 3 -> searchSchools();
				case 4 -> inputToUpdateSchool();
				case 5 -> deleteDetails();
				case 6 -> {
					closeProgram();
					exist = true;
				}


			}

		}

	}

	public static void showAllSchools() {


		EntityManager entityManager = entityManagerFactory.createEntityManager();

		Query query = entityManager.createQuery("SELECT school FROM SchoolEntity school");
		printData(query);



	}

	private static void searchSchools() {

		EntityManager entityManager = entityManagerFactory.createEntityManager();
		System.out.println("Enter the ID number of the school you are searching for:");
		int searchId = scanner.nextInt();

		Query query = entityManager.createQuery("SELECT school FROM SchoolEntity school WHERE school.schoolId =" + searchId);
		printData(query);


	}

	private static void addSchool(String inputName, String inputAdress) {

		EntityManager entityManager = entityManagerFactory.createEntityManager();
		entityManager.getTransaction().begin();

		SchoolEntity schoolEntity = new SchoolEntity();
		schoolEntity.setSchoolName(inputName);
		schoolEntity.setAddress(inputAdress);


		entityManager.persist(schoolEntity);
		entitymanagerMethods(entityManager);
		System.out.println(inputName + " added successfully!");


	}

	private static void inputToNewSchool() {

		scanner.nextLine();
		System.out.println("Type the name of the school");
		String inputName = scanner.nextLine();
		System.out.println("Type the adress of the school");
		String inputAdress = scanner.nextLine();
		addSchool(inputName, inputAdress);



	}

	private static void updateSchool(String updateName, String updateAdress, int updateId) {

		EntityManager entityManager = entityManagerFactory.createEntityManager();
		entityManager.getTransaction().begin();
		SchoolEntity schoolEntity = entityManager.find(SchoolEntity.class, updateId);
		schoolEntity.setSchoolName(updateName);
		schoolEntity.setAddress(updateAdress);
		entityManager.persist(schoolEntity);
		entitymanagerMethods(entityManager);
		System.out.println("The school have been updated to " + updateName + "!");

	}

	private static void inputToUpdateSchool() {

		showAllSchools();

		System.out.println("Enter the ID number of the school you want to update: ");
		int updateId = scanner.nextInt();
		scanner.nextLine();
		System.out.println("New name: ");
		String updateName = scanner.nextLine();
		System.out.println("New adress: ");
		String updateAdress = scanner.nextLine();
		updateSchool(updateName, updateAdress, updateId);

	}

	private static void deleteSchool(int inputId) {

		EntityManager entityManager = entityManagerFactory.createEntityManager();
		entityManager.getTransaction().begin();
		SchoolEntity schoolEntity = entityManager.find(SchoolEntity.class, inputId);

		entityManager.remove(schoolEntity);
		entitymanagerMethods(entityManager);
		System.out.println(schoolEntity.getSchoolName() + " is deleted!");



	}

	private static void deleteDetails() {

		showAllSchools();

		System.out.println("Type the ID of the school you want to remove:");
		int inputId = scanner.nextInt();
		deleteSchool(inputId);


	}

	private static void entitymanagerMethods(EntityManager entityManager) {


		entityManager.getTransaction().commit();
		entityManager.close();

	}

	private static void closeProgram() {

		entityManagerFactory.close();

	}

	private static void printData(Query query) {

		var list = query.getResultList();
		if (list.isEmpty()) {
			System.out.println("That list does not exist!");

		} else list.forEach(System.out::println);

	}

}


