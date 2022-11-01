import entities.Address;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import java.util.List;

public class addressWithEmployeeCount {
    private static final String DATABASE_NAME = "soft_uni";
    private static final String GET_ALL_ADDRESSES_BY_NUMBER_OF_EMPLOYEES = "SELECT a FROM Address a ORDER BY a.employees.size DESC";

    public static void main(String[] args) {

        EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory(DATABASE_NAME);
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        entityManager.getTransaction().begin();

        List<Address> addressList = entityManager.createQuery(GET_ALL_ADDRESSES_BY_NUMBER_OF_EMPLOYEES, Address.class).setMaxResults(10).getResultList();

        addressList.forEach(address -> System.out.println(address));

        entityManager.getTransaction().commit();
        entityManager.close();


    }
}
