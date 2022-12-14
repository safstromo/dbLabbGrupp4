package se.iths;

import java.util.Scanner;

public class Saher {
    //TODO CLASS
    private static Scanner sc = new Scanner(System.in);

    public static void menu() {

    }

    private static void detailsOfclassToInput(){
        System.out.println("Input name of class/program: ");
        String inputName = sc.nextLine();
        System.out.println("Input duration of the class/program: ");
        String durationInput = sc.nextLine();
        System.out.println("Which school does the class/program belong to? Enter the school ID: ");
        int schoolIDInput = sc.nextInt();
        addNewClass(inputName, durationInput, schoolIDInput);
        sc.nextLine();
    }

    private static void addNewClass(String inputName, String durationInput, int schoolIDInput) {
    }


}
