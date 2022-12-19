package se.iths;

import java.util.Scanner;

/*Todo
 * Göra om utskrifter så att allt ser likt ut?
 * Kanske ändra några av show all frågor så att vi kopplar ihop tabellerna.
 *


 */
public class Main {


	//test
	public static void main(String[] args) {
		Scanner sc = new Scanner(System.in);


		int inputChoice;
		System.out.println("Welcome to the PROGRAM DATABASE");
		while (true) {
			printMenuOptions();
			inputChoice = sc.nextInt();
			sc.nextLine();

			switch (inputChoice) {
				case 0 -> printMenuOptions();
				case 1 -> Student.studentMenu();
				case 2 -> Teacher.teacherMenu();
				case 3 -> Program.menu();
				case 4 -> Courses.menu();
				case 5 -> Schools.schoolAlternativesToMenu();
				case 6 -> System.exit(0);
				default -> System.out.println("Invalid choice. Try again.");
			}

		}


	}

	private static void printMenuOptions() {
		System.out.println("""
				===============================
				Choose from the options below
				1. View Students database
				2. View Teachers database 
				3. View Programs database
				4. View Courses database
				5. View Schools database
				6. Exit
				===============================
				""");
	}
}
