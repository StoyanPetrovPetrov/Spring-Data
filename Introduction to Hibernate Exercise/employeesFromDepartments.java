import entities.Employee;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import java.util.List;

public class employeesFromDepartments {
    private static final String DATABASE_NAME = "soft_uni";
    private static final String DEPARTMENT_NAME = "Research and Development";
    private static final String GET_EMPLOYEES_FROM_RESEARCH_AND_DEVELOPMENT_DEPARTMENT = "SELECT e FROM Employee e WHERE e.department.name = :dep ORDER BY e.salary, e.id";
    private static final String PRINT_RESULT = "%s %s from Research and Development - $%.2f%n";

    public static void main(String[] args) {

        EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory(DATABASE_NAME);
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        entityManager.getTransaction().begin();

        List<Employee> employeesList = entityManager.createQuery(GET_EMPLOYEES_FROM_RESEARCH_AND_DEVELOPMENT_DEPARTMENT, Employee.class)
                .setParameter("dep", DEPARTMENT_NAME)
                .getResultList();

        employeesList.forEach(employee -> System.out.printf(PRINT_RESULT, employee.getFirstName(),
                employee.getLastName(), employee.getSalary()));

        entityManager.getTransaction().commit();
        entityManager.close();

    }
}
