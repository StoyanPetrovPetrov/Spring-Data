import entities.Town;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.Query;
import java.util.List;

public class changeCasing {
    private static final String DATABASE_NAME = "soft_uni";
    //private static final String UPDATE_ALL_TOWNS_WITH_LENGTH_MORE_THAN_5 =
           // "UPDATE Town t set t.name = upper(t.name) where length(t.name)>5";
    public static void main(String[] args) {
      final EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory(DATABASE_NAME);
      final EntityManager entityManager = entityManagerFactory.createEntityManager();
        entityManager.getTransaction().begin();
        Query towns = entityManager.createQuery("select t from Town t ", Town.class);

        List<Town>resultList = towns.getResultList();
        for (Town town : resultList) {
            String townName = town.getName();
            if (townName.length()<= 5){
                entityManager.persist(townName.toUpperCase());
            }
            
        }
        
        entityManager.persist(resultList);

        entityManager.getTransaction().commit();
      entityManager.close();


    }
}
