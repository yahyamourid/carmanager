package ma.zyn.app.service.facade.admin.car;

import java.util.List;
import ma.zyn.app.bean.core.car.Car;
import ma.zyn.app.dao.criteria.core.car.CarCriteria;
import ma.zyn.app.zynerator.service.IService;



public interface CarAdminService {



    List<Car> findByOwnerId(Long id);
    int deleteByOwnerId(Long id);
    long countByOwnerEmail(String email);




	Car create(Car t);

    Car update(Car t);

    List<Car> update(List<Car> ts,boolean createIfNotExist);

    Car findById(Long id);

    Car findOrSave(Car t);

    Car findByReferenceEntity(Car t);

    Car findWithAssociatedLists(Long id);

    List<Car> findAllOptimized();

    List<Car> findAll();

    List<Car> findByCriteria(CarCriteria criteria);

    List<Car> findPaginatedByCriteria(CarCriteria criteria, int page, int pageSize, String order, String sortField);

    int getDataSize(CarCriteria criteria);

    List<Car> delete(List<Car> ts);

    boolean deleteById(Long id);

    List<List<Car>> getToBeSavedAndToBeDeleted(List<Car> oldList, List<Car> newList);

    public String uploadFile(String checksumOld, String tempUpladedFile,String destinationFilePath) throws Exception ;

}
