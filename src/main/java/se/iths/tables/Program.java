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
				case 1 -> showAllProgramsWithJoin();
				case 2 -> detailsOfProgramToInput();
				case 3 -> detailsOfProgramToSearch();
				case 4 -> detailsOfProgramToUpdate();
				case 5 -> detailsOfProgramToDelete();
				case 6 -> numberOfPrograms();
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

/*	private static void showAllPrograms() {
		EntityManager entityManager = entityManagerFactory.createEntityManager();
		Query query = entityManager.createQuery("SELECT programs FROM ProgramEntity programs");

		List<ProgramEntity> listOfPrograms = query.getResultList();
		printListOfPrograms(listOfPrograms);
	}*/

	private static void showAllProgramsWithJoin() {
		EntityManager entityManager = entityManagerFactory.createEntityManager();

		Query query = entityManager.createQuery("SELECT programs FROM ProgramEntity programs");


		List<ProgramEntity> list = query.getResultList( );
		System.out.println(list.size());
		for( ProgramEntity p:list ){
			System.out.println("ID: " + p.getProgramId() + " | Name: " + p.getProgramName() + " | Duration: " + p.getDuration() + " years" + " | School: " + p.getSchoolByProgramSchoolIdfk().getSchoolName());
		}

	}

	private static void detailsOfProgramToInput() {
		System.out.println("Input name of program: ");
		String inputName = sc.nextLine();
		System.out.println("Input duration of the program: ");
		int durationInput = sc.nextInt();
		sc.nextLine();
		Schools.showAllSchools();
		System.out.println("Which school does the program belong to? Enter the school ID: ");
		int schoolIDInput = sc.nextInt();
		addNewProgram(inputName, durationInput, schoolIDInput);
		sc.nextLine();
	}

	private static void addNewProgram(String programName, int programDuration, int programSchoolID) {
		EntityManager entityManager = entityManagerFactory.createEntityManager();
		entityManager.getTransaction().begin();
		ProgramEntity newProgram = new ProgramEntity();

		newProgram.setProgramName(programName);
		newProgram.setDuration(programDuration);
		newProgram.setProgramSchoolIdfk(programSchoolID);

		entityManager.persist(newProgram);
		handleEntityManager(entityManager);

		System.out.println("New program successfully added!");
	}

	private static void detailsOfProgramToUpdate() {
		EntityManager entityManager = entityManagerFactory.createEntityManager();
		showAllProgramsWithJoin();
		int programIdToUpdate = getProgramId();

		entityManager.getTransaction().begin();
		ProgramEntity programEntity = entityManager.find(ProgramEntity.class, programIdToUpdate);

		updateSwitch(programEntity);
		entityManager.persist(programEntity);
		handleEntityManager(entityManager);

		System.out.println("Program successfully updated!");

	}

	private static int getProgramId() {
		System.out.println("Enter the ID of the program:");
		int programIdToUpdate = sc.nextInt();
		sc.nextLine();
		return programIdToUpdate;
	}

	private static void updateSwitch(ProgramEntity programEntity) {
		int inputChoice = getInputChoice();
		sc.nextLine();


		switch (inputChoice) {
			case 1 -> updateProgramName(programEntity);
			case 2 -> updateProgramDuration(programEntity);
			case 3 -> updateProgramSchoolID(programEntity);
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

	private static void updateProgramName(ProgramEntity programEntity) {
		System.out.println("Write the new name for the program");
		String newProgramName = sc.nextLine();
		programEntity.setProgramName(newProgramName);

	}

	private static void updateProgramDuration(ProgramEntity programEntity) {
		System.out.println("Write the new duration for the program");
		int newProgramDuration = sc.nextInt();
		programEntity.setDuration(newProgramDuration);
	}

	private static void updateProgramSchoolID(ProgramEntity programEntity) {
		System.out.println("Which school does the program belong to? Enter the new school ID: ");
		int newProgramSchoolID = sc.nextInt();
		programEntity.setProgramSchoolIdfk(newProgramSchoolID);
	}

	private static void detailsOfProgramToDelete() {
		showAllProgramsWithJoin();
		int programIdToDelete = getProgramId();


		deleteProgram(programIdToDelete);
	}

	private static void deleteProgram(int programIdToDelete) {
		EntityManager entityManager = entityManagerFactory.createEntityManager();
		entityManager.getTransaction().begin();
		ProgramEntity programEntity = entityManager.find(ProgramEntity.class, programIdToDelete);
		entityManager.remove(programEntity);
		handleEntityManager(entityManager);

		System.out.println("Program successfully deleted!");
	}

	private static void detailsOfProgramToSearch() {
		System.out.println("Input name of program to search");
		String inputProgramName = sc.nextLine();
		searchProgram(inputProgramName);
	}

	private static void searchProgram(String inputProgramName) {
		EntityManager entityManager = entityManagerFactory.createEntityManager();
		entityManager.getTransaction().begin();
		Query query = entityManager.createQuery("SELECT program FROM ProgramEntity program WHERE program.programName = '" + inputProgramName + "'");
		var listOfPrograms = query.getResultList();
		printListOfPrograms(listOfPrograms);
	}

	private static void numberOfPrograms() {
		EntityManager entityManager = entityManagerFactory.createEntityManager();
		Query query = entityManager.createQuery("SELECT COUNT(program.programId) FROM ProgramEntity program");
		System.out.println("There are " + query.getSingleResult() + " programs available in our database");
	}

	private static void printListOfPrograms(List listOfPrograms) {
		if (listOfPrograms.isEmpty()) {
			System.out.println("No programs available to show");
		} else {
			listOfPrograms.forEach(System.out::println);
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
