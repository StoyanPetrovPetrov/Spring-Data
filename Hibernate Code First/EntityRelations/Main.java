import entitys.Bike;
import entitys.Car;
import entitys.Plane;
import entitys.Vehicle;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;

public class Main {
    public static void main(String[] args) {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("relations");

        EntityManager entityManager = emf.createEntityManager();

        entityManager.getTransaction().begin();

        Vehicle car = new Car("Ford Focus","Petrol",5);
        Vehicle bike = new Bike();
        Vehicle plane = new Plane("Air Buss","Petrol",200);
        entityManager.persist(car);
        entityManager.persist(bike);
        entityManager.persist(plane);

        entityManager.getTransaction().commit();

        entityManager.close();
    }
}
