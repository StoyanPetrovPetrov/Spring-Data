import entities.Address;
import entities.Town;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import java.util.List;
import java.util.Scanner;

public class removeTowns {
    private static final String DATABASE_NAME = "soft_uni";
    private static final String SELECT_ADDRESSES_BY_GIVEN_TOWN = "SELECT a FROM Address a WHERE a.town.name = :town";
    private static final String PRINT_MESSAGE_BY_NONEXISTENT_TOWN = "There is no such town in the database!";
    private static final String GET_TOWN_BY_NAME = "SELECT t FROM Town t WHERE t.name = :town";
    private static final String ADDRESS = "address";
    private static final String ADDRESSES = "addresses";
    private static final String PRINT_RESULT_PATTERN = "%d %s in %s deleted";

    public static void main(String[] args) {

        EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory(DATABASE_NAME);
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        entityManager.getTransaction().begin();

        Scanner scanner = new Scanner(System.in);
        String givenTown = scanner.nextLine();

        List<Address> addressesList = entityManager.createQuery(SELECT_ADDRESSES_BY_GIVEN_TOWN, Address.class)
                .setParameter("town", givenTown)
                .getResultList();

        long numberAddressesForDeleting = addressesList.size();

        if (numberAddressesForDeleting == 0) {
            System.out.println(PRINT_MESSAGE_BY_NONEXISTENT_TOWN);
            entityManager.close();
            return;
        }

        addressesList.forEach(address -> {
            address.getEmployees().forEach(employee -> employee.setAddress(null));
            entityManager.remove(address);
        });

        Town townForDeleting = entityManager.createQuery(GET_TOWN_BY_NAME, Town.class)
                .setParameter("town", givenTown)
                .getSingleResult();

        entityManager.remove(townForDeleting);

        String address = numberAddressesForDeleting == 1 ? ADDRESS : ADDRESSES;
        System.out.printf(PRINT_RESULT_PATTERN, numberAddressesForDeleting, address, givenTown);

        entityManager.getTransaction().commit();
        entityManager.close();
    }
}
