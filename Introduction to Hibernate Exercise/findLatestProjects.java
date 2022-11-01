import entities.Project;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import java.util.Comparator;
import java.util.List;

public class findLatestProjects {
    private static final String DATABASE_NAME = "soft_uni";
    private static final String GET_LATEST_10_PROJECTS_BY_START_DATE_DESC = "SELECT p FROM Project p ORDER BY p.startDate DESC";
    private static final int FIRST_10_RESULTS = 10;
    public static void main(String[] args) {

        EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory(DATABASE_NAME);
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        entityManager.getTransaction().begin();

        List<Project> projectList = entityManager.createQuery(GET_LATEST_10_PROJECTS_BY_START_DATE_DESC, Project.class)
                .setMaxResults(FIRST_10_RESULTS)
                .getResultList();

        projectList.stream().sorted(Comparator.comparing(Project::getName))
                .forEach(System.out::println);

        entityManager.getTransaction().commit();
        entityManager.close();
    }
}
