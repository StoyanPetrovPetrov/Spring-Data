import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.Query;
import java.util.List;

public class emloyeesWithSalaryOver50000 {
    private static final String DATABASE_NAME = "soft_uni";
    public static void main(String[] args) {
        EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory(DATABASE_NAME);
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        entityManager.getTransaction().begin();

        Query getEmployeesWithBigSalary = entityManager.createQuery("SELECT e.firstName FROM Employee e WHERE e.salary > 50000");
        List<String> getEmployeesWithBigSalaryList = getEmployeesWithBigSalary.getResultList();

        getEmployeesWithBigSalaryList.forEach(System.out::println);

        entityManager.getTransaction().commit();
        entityManager.close();
    }
}
