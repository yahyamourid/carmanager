package ma.zyn.app.dao.facade.core.car;

import org.springframework.data.jpa.repository.Query;
import ma.zyn.app.zynerator.repository.AbstractRepository;
import ma.zyn.app.bean.core.car.Car;
import org.springframework.stereotype.Repository;
import ma.zyn.app.bean.core.car.Car;
import java.util.List;


@Repository
public interface CarDao extends AbstractRepository<Car,Long>  {
    Car findByLicensePlate(String licensePlate);
    int deleteByLicensePlate(String licensePlate);

    List<Car> findByOwnerId(Long id);
    int deleteByOwnerId(Long id);
    long countByOwnerEmail(String email);

    @Query("SELECT NEW Car(item.id,item.licensePlate) FROM Car item")
    List<Car> findAllOptimized();

}
