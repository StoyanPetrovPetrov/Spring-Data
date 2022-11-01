import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import java.util.List;

public class employeesMaximumSalaries {
    private static final String DATABASE_NAME = "soft_uni";
    private static final String GET_MAX_SALARY_FOR_EVERY_DEPARTMENT =
            "SELECT e.department.name, MAX(e.salary) FROM Employee e " +
                    "GROUP BY e.department.name " +
                    "HAVING MAX(e.salary) NOT BETWEEN 30000 AND 70000";

    private static final String PRINT_RESULT_PATTERN = "%s %s%n";
    public static void main(String[] args) {

        EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory(DATABASE_NAME);
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        entityManager.getTransaction().begin();

        List<Object[]> objectList = entityManager.createQuery(GET_MAX_SALARY_FOR_EVERY_DEPARTMENT, Object[].class).getResultList();

        objectList.forEach(pair -> System.out.printf(PRINT_RESULT_PATTERN, pair[0], pair[1]));

        entityManager.getTransaction().commit();
        entityManager.close();
    }
}
