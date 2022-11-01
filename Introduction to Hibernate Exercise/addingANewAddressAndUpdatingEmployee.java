import entities.Address;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import java.util.Scanner;

public class addingANewAddressAndUpdatingEmployee {
    private static final String DATABASE_NAME = "soft_uni";
    private static final String GIVEN_ADDRESS_TEXT = "Vitoshka 15";
    private static final String SET_EMPLOYEE_ADDRESS_TEXT_BY_GIVEN_LAST_NAME = "UPDATE Employee e SET e.address = :add WHERE e.lastName = :ln";

    public static void main(String[] args) {
        EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory(DATABASE_NAME);
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        entityManager.getTransaction().begin();

        Scanner scanner = new Scanner(System.in);
        String givenLastName = scanner.nextLine();

        Address changedAddress = new Address();
        changedAddress.setText(GIVEN_ADDRESS_TEXT);
        entityManager.persist(changedAddress);

        entityManager.createQuery(SET_EMPLOYEE_ADDRESS_TEXT_BY_GIVEN_LAST_NAME)
                .setParameter("add", changedAddress)
                .setParameter("ln", givenLastName)
                .executeUpdate();

        entityManager.getTransaction().commit();
        entityManager.close();
    }
}
