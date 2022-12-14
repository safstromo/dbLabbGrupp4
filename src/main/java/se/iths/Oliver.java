package se.iths;

import jakarta.persistence.*;

public class Oliver {

	public static void main(String[] args) {
		showAllTeachers();
	}
	/// TODO Teacher!

	public static void showAllTeachers(){
		EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory("default");
		EntityManager entityManager = entityManagerFactory.createEntityManager();

		Query query = entityManager.createQuery("SELECT teachers FROM TeacherEntity teachers");
		var list = query.getResultList();

		list.forEach(System.out::println);

	}






}
