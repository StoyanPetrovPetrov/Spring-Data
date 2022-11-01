import entities.Employee;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import java.util.List;
import java.util.Scanner;

public class findEmployeesByFirstName {
    private static final String DATABASE_NAME = "soft_uni";
    private static final String GET_ALL_EMPLOYEES_BY_GIVEN_PATTERN = "SELECT e FROM Employee e WHERE firstName LIKE :givenPattern";
    private static final String PRINT_RESULT_PATTERN = "%s %s - %s - ($%.2f)%n";

    public static void main(String[] args) {
        EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory(DATABASE_NAME);
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        entityManager.getTransaction().begin();

        Scanner scanner = new Scanner(System.in);
        String pattern = scanner.nextLine();

        List<Employee> employeeList = entityManager.createQuery(GET_ALL_EMPLOYEES_BY_GIVEN_PATTERN, Employee.class)
                .setParameter("givenPattern", pattern + "%")
                .getResultList();

        employeeList.forEach(employee -> System.out.printf(PRINT_RESULT_PATTERN,
                employee.getFirstName(), employee.getLastName(), employee.getJobTitle(), employee.getSalary()));

        entityManager.getTransaction().commit();
        entityManager.close();
    }
}
