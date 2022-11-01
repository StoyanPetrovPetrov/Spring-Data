import entities.Employee;
import entities.Project;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import java.util.Comparator;
import java.util.Scanner;

public class getEmployeesWithProject {
    private static final String DATABASE_NAME = "soft_uni";
    private static final String GET_EMPLOYEE_BY_ID = "SELECT e FROM Employee e WHERE e.id = :ID";

    public static void main(String[] args) {

        EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory(DATABASE_NAME);
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        entityManager.getTransaction().begin();

        Scanner scanner = new Scanner(System.in);
        int givenId = Integer.parseInt(scanner.nextLine());

        Employee employee = entityManager.createQuery(GET_EMPLOYEE_BY_ID, Employee.class)
                .setParameter("ID", givenId)
                .getSingleResult();

        System.out.println(employee.getFirstName() + " " +  employee.getLastName() + " - "  + employee.getJobTitle());

        employee.getProjects().stream()
                .sorted(Comparator.comparing(Project::getName))
                .forEach(project -> System.out.println(project.getName()));


        entityManager.getTransaction().commit();
        entityManager.close();

    }
}
