import javax.persistence.Persistence;

public class main {
    public static void main(String[] args) {
        Persistence.createEntityManagerFactory("soft_uni")
                .createEntityManager();
        
    }
}
