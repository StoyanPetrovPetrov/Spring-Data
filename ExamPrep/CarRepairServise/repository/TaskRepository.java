package softuni.exam.repository;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import softuni.exam.models.entity.Task;
import softuni.exam.models.enums.CarType;

@Repository
public interface TaskRepository extends JpaRepository<Task, Long> {

    List<Task> findByCar_CarTypeOrderByPriceDesc(CarType carType);
}
