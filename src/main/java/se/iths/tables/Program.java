package se.iths.tables;

import entity.ProgramEntity;
import jakarta.persistence.*;

import java.util.List;
import java.util.Scanner;

public class Program {

	private static final Scanner sc = new Scanner(System.in);
	static EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory("default");

	public static void main(String[] args) {
		menu();
	}

	public static void menu() {
		int inputChoice;
		boolean menu = true;
		while (menu) {
			printMenuOptions();
			inputChoice = sc.nextInt();
			sc.nextLine();

			switch (inputChoice) {
				case 0 -> printMenuOptions();
				case 1 -> showAllClassesWithJoin();
				case 2 -> detailsOfClassToInput();
				case 3 -> detailsOfClassToSearch();
				case 4 -> detailsOfClassToUpdate();
				case 5 -> detailsOfClassToDelete();
				case 6 -> numberOfClasses();
				case 7 -> menu = exitProgram();
				default -> System.out.println("Invalid choice. Try again.");
			}
		}
	}

	private static void printMenuOptions() {
		System.out.println("""
				Program Menu
				===============================
				Choose from the options below
				0. Print Program menu
				1. Show all programs
				2. Add new program
				3. Search for program
				4. Update a program
				5. Remove a program
				6. Show number of programs available
				7. Back to Main Menu
				===============================
				""");
	}

/*	private static void showAllClasses() {
		EntityManager entityManager = entityManagerFactory.createEntityManager();
		Query query = entityManager.createQuery("SELECT classes FROM ProgramEntity classes");

		List<ProgramEntity> listOfClasses = query.getResultList();
		printListOfClasses(listOfClasses);
	}*/

	private static void showAllClassesWithJoin() {
		EntityManager entityManager = entityManagerFactory.createEntityManager();

		Query query = entityManager.createQuery("SELECT classes FROM ProgramEntity classes");


		List<ProgramEntity> list = query.getResultList( );
		System.out.println(list.size());
		for( ProgramEntity p:list ){
			System.out.println("ID: " + p.getClassId() + " | Name: " + p.getClassName() + " | Duration: " + p.getDuration() + " years" + " | School: " + p.getSchoolByClassSchoolIdfk().getSchoolName());
		}

	}

	private static void detailsOfClassToInput() {
		System.out.println("Input name of program: ");
		String inputName = sc.nextLine();
		System.out.println("Input duration of the program: ");
		int durationInput = sc.nextInt();
		sc.nextLine();
		Schools.showAllSchools();
		System.out.println("Which school does the program belong to? Enter the school ID: ");
		int schoolIDInput = sc.nextInt();
		addNewClass(inputName, durationInput, schoolIDInput);
		sc.nextLine();
	}

	private static void addNewClass(String className, int classDuration, int classSchoolID) {
		EntityManager entityManager = entityManagerFactory.createEntityManager();
		entityManager.getTransaction().begin();
		ProgramEntity newClass = new ProgramEntity();

		newClass.setClassName(className);
		newClass.setDuration(classDuration);
		newClass.setClassSchoolIdfk(classSchoolID);

		entityManager.persist(newClass);
		handleEntityManager(entityManager);

		System.out.println("New program successfully added!");
	}

	private static void detailsOfClassToUpdate() {
		EntityManager entityManager = entityManagerFactory.createEntityManager();
		showAllClassesWithJoin();
		int classIdToUpdate = getClassId();

		entityManager.getTransaction().begin();
		ProgramEntity programEntity = entityManager.find(ProgramEntity.class, classIdToUpdate);

		updateSwitch(programEntity);
		entityManager.persist(programEntity);
		handleEntityManager(entityManager);

		System.out.println("Program successfully updated!");

	}

	private static int getClassId() {
		System.out.println("Enter the ID of the program:");
		int classIdToUpdate = sc.nextInt();
		sc.nextLine();
		return classIdToUpdate;
	}

	private static void updateSwitch(ProgramEntity programEntity) {
		int inputChoice = getInputChoice();
		sc.nextLine();


		switch (inputChoice) {
			case 1 -> updateClassName(programEntity);
			case 2 -> updateClassDuration(programEntity);
			case 3 -> updateClassSchoolID(programEntity);
		}
	}

	private static int getInputChoice() {
		System.out.println("""
				What would you like to update?
				1- Program name
				2- Duration of the program
				3- School to which the program belongs""");
		return sc.nextInt();
	}

	private static void updateClassName(ProgramEntity programEntity) {
		System.out.println("Write the new name for the program");
		String newClassName = sc.nextLine();
		programEntity.setClassName(newClassName);

	}

	private static void updateClassDuration(ProgramEntity programEntity) {
		System.out.println("Write the new duration for the program");
		int newClassDuration = sc.nextInt();
		programEntity.setDuration(newClassDuration);
	}

	private static void updateClassSchoolID(ProgramEntity programEntity) {
		System.out.println("Which school does the program belong to? Enter the new school ID: ");
		int newClassSchoolID = sc.nextInt();
		programEntity.setClassSchoolIdfk(newClassSchoolID);
	}

	private static void detailsOfClassToDelete() {
		showAllClassesWithJoin();
		int classIdToDelete = getClassId();


		deleteClass(classIdToDelete);
	}

	private static void deleteClass(int classIdToDelete) {
		EntityManager entityManager = entityManagerFactory.createEntityManager();
		entityManager.getTransaction().begin();
		ProgramEntity programEntity = entityManager.find(ProgramEntity.class, classIdToDelete);
		entityManager.remove(programEntity);
		handleEntityManager(entityManager);

		System.out.println("Program successfully deleted!");
	}

	private static void detailsOfClassToSearch() {
		System.out.println("Input name of program to search");
		String inputClassName = sc.nextLine();
		searchClass(inputClassName);
	}

	private static void searchClass(String inputClassName) {
		EntityManager entityManager = entityManagerFactory.createEntityManager();
		entityManager.getTransaction().begin();
		Query query = entityManager.createQuery("SELECT class FROM ProgramEntity class WHERE class.className = '" + inputClassName + "'");
		var listOfClasses = query.getResultList();
		printListOfClasses(listOfClasses);
	}

	private static void numberOfClasses() {
		EntityManager entityManager = entityManagerFactory.createEntityManager();
		Query query = entityManager.createQuery("SELECT COUNT(class.classId) FROM ProgramEntity class");
		System.out.println("There are " + query.getSingleResult() + " programs available in our database");
	}

	private static void printListOfClasses(List listOfClasses) {
		if (listOfClasses.isEmpty()) {
			System.out.println("No programs available to show");
		} else {
			listOfClasses.forEach(System.out::println);
		}
	}

	private static void handleEntityManager(EntityManager entityManager) {
		entityManager.getTransaction().commit();
		entityManager.close();
	}

	private static boolean exitProgram() {
		entityManagerFactory.close();
		return false;
	}
}
