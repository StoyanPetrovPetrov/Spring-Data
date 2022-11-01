import entities.Employee;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import java.util.List;

public class increaseSalaries {
    private static final String DATABASE_NAME = "soft_uni";
    private static final String INCREASE_SALARY_WITH_12_PERCENT = "UPDATE Employee e SET salary = salary * 1.12 WHERE e.department.id IN(1, 2, 4, 11)";
    private static final String SELECT_ALL_EMPLOYEES_WITH_INCREASED_SALARY = "SELECT e FROM Employee e WHERE  e.department.id IN(1, 2, 4, 11)";
    private static final String PRINT_RESULT_PATTERN = "%s %s ($%.2f)%n";

    public static void main(String[] args) {

        EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory(DATABASE_NAME);
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        entityManager.getTransaction().begin();

        entityManager.createQuery(INCREASE_SALARY_WITH_12_PERCENT).executeUpdate();

        List<Employee> employeeList = entityManager.createQuery(SELECT_ALL_EMPLOYEES_WITH_INCREASED_SALARY, Employee.class).getResultList();

        employeeList.forEach(employee -> System.out.printf(PRINT_RESULT_PATTERN, employee.getFirstName(), employee.getLastName(), employee.getSalary()));

        entityManager.getTransaction().commit();
        entityManager.close();
    }
}
