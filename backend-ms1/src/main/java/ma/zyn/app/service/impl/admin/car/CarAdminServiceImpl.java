package ma.zyn.app.service.impl.admin.car;


import ma.zyn.app.zynerator.exception.EntityNotFoundException;
import ma.zyn.app.bean.core.car.Car;
import ma.zyn.app.dao.criteria.core.car.CarCriteria;
import ma.zyn.app.dao.facade.core.car.CarDao;
import ma.zyn.app.dao.specification.core.car.CarSpecification;
import ma.zyn.app.service.facade.admin.car.CarAdminService;
import ma.zyn.app.zynerator.service.AbstractServiceImpl;
import static ma.zyn.app.zynerator.util.ListUtil.*;

import org.springframework.stereotype.Service;
import java.util.List;
import java.util.ArrayList;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import ma.zyn.app.zynerator.util.RefelexivityUtil;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import ma.zyn.app.service.facade.admin.owner.OwnerAdminService ;
import ma.zyn.app.bean.core.owner.Owner ;

import java.util.List;
@Service
public class CarAdminServiceImpl implements CarAdminService {

    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class, readOnly = false)
    public Car update(Car t) {
        Car loadedItem = dao.findById(t.getId()).orElse(null);
        if (loadedItem == null) {
            throw new EntityNotFoundException("errors.notFound", new String[]{Car.class.getSimpleName(), t.getId().toString()});
        } else {
            dao.save(t);
            return loadedItem;
        }
    }

    public Car findById(Long id) {
        return dao.findById(id).orElse(null);
    }


    public Car findOrSave(Car t) {
        if (t != null) {
            findOrSaveAssociatedObject(t);
            Car result = findByReferenceEntity(t);
            if (result == null) {
                return dao.save(t);
            } else {
                return result;
            }
        }
        return null;
    }

    public List<Car> findAll() {
        return dao.findAll();
    }

    public List<Car> findByCriteria(CarCriteria criteria) {
        List<Car> content = null;
        if (criteria != null) {
            CarSpecification mySpecification = constructSpecification(criteria);
            content = dao.findAll(mySpecification);
        } else {
            content = dao.findAll();
        }
        return content;

    }


    private CarSpecification constructSpecification(CarCriteria criteria) {
        CarSpecification mySpecification =  (CarSpecification) RefelexivityUtil.constructObjectUsingOneParam(CarSpecification.class, criteria);
        return mySpecification;
    }

    public List<Car> findPaginatedByCriteria(CarCriteria criteria, int page, int pageSize, String order, String sortField) {
        CarSpecification mySpecification = constructSpecification(criteria);
        order = (order != null && !order.isEmpty()) ? order : "desc";
        sortField = (sortField != null && !sortField.isEmpty()) ? sortField : "id";
        Pageable pageable = PageRequest.of(page, pageSize, Sort.Direction.fromString(order), sortField);
        return dao.findAll(mySpecification, pageable).getContent();
    }

    public int getDataSize(CarCriteria criteria) {
        CarSpecification mySpecification = constructSpecification(criteria);
        mySpecification.setDistinct(true);
        return ((Long) dao.count(mySpecification)).intValue();
    }

    public List<Car> findByOwnerId(Long id){
        return dao.findByOwnerId(id);
    }
    public int deleteByOwnerId(Long id){
        return dao.deleteByOwnerId(id);
    }
    public long countByOwnerEmail(String email){
        return dao.countByOwnerEmail(email);
    }
    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class, readOnly = false)
	public boolean deleteById(Long id) {
        boolean condition = (id != null);
        if (condition) {
            dao.deleteById(id);
        }
        return condition;
    }




    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class, readOnly = false)
    public List<Car> delete(List<Car> list) {
		List<Car> result = new ArrayList();
        if (list != null) {
            for (Car t : list) {
                if(dao.findById(t.getId()).isEmpty()){
					result.add(t);
				}else{
                    dao.deleteById(t.getId());
                }
            }
        }
		return result;
    }

    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class, readOnly = false)
    public Car create(Car t) {
        Car loaded = findByReferenceEntity(t);
        Car saved;
        if (loaded == null) {
            saved = dao.save(t);
        }else {
            saved = null;
        }
        return saved;
    }

    public Car findWithAssociatedLists(Long id){
        Car result = dao.findById(id).orElse(null);
        return result;
    }

	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class, readOnly = false)
    public List<Car> update(List<Car> ts, boolean createIfNotExist) {
        List<Car> result = new ArrayList<>();
        if (ts != null) {
            for (Car t : ts) {
                if (t.getId() == null) {
                    dao.save(t);
                } else {
                    Car loadedItem = dao.findById(t.getId()).orElse(null);
                    if (isEligibleForCreateOrUpdate(createIfNotExist, t, loadedItem)) {
                        dao.save(t);
                    } else {
                        result.add(t);
                    }
                }
            }
        }
        return result;
    }


    private boolean isEligibleForCreateOrUpdate(boolean createIfNotExist, Car t, Car loadedItem) {
        boolean eligibleForCreateCrud = t.getId() == null;
        boolean eligibleForCreate = (createIfNotExist && (t.getId() == null || loadedItem == null));
        boolean eligibleForUpdate = (t.getId() != null && loadedItem != null);
        return (eligibleForCreateCrud || eligibleForCreate || eligibleForUpdate);
    }









    public Car findByReferenceEntity(Car t){
        return t==null? null : dao.findByLicensePlate(t.getLicensePlate());
    }
    public void findOrSaveAssociatedObject(Car t){
        if( t != null) {
            t.setOwner(ownerService.findOrSave(t.getOwner()));
        }
    }



    public List<Car> findAllOptimized() {
        return dao.findAllOptimized();
    }

    @Override
    public List<List<Car>> getToBeSavedAndToBeDeleted(List<Car> oldList, List<Car> newList) {
        List<List<Car>> result = new ArrayList<>();
        List<Car> resultDelete = new ArrayList<>();
        List<Car> resultUpdateOrSave = new ArrayList<>();
        if (isEmpty(oldList) && isNotEmpty(newList)) {
            resultUpdateOrSave.addAll(newList);
        } else if (isEmpty(newList) && isNotEmpty(oldList)) {
            resultDelete.addAll(oldList);
        } else if (isNotEmpty(newList) && isNotEmpty(oldList)) {
			extractToBeSaveOrDelete(oldList, newList, resultUpdateOrSave, resultDelete);
        }
        result.add(resultUpdateOrSave);
        result.add(resultDelete);
        return result;
    }

    private void extractToBeSaveOrDelete(List<Car> oldList, List<Car> newList, List<Car> resultUpdateOrSave, List<Car> resultDelete) {
		for (int i = 0; i < oldList.size(); i++) {
                Car myOld = oldList.get(i);
                Car t = newList.stream().filter(e -> myOld.equals(e)).findFirst().orElse(null);
                if (t != null) {
                    resultUpdateOrSave.add(t); // update
                } else {
                    resultDelete.add(myOld);
                }
            }
            for (int i = 0; i < newList.size(); i++) {
                Car myNew = newList.get(i);
                Car t = oldList.stream().filter(e -> myNew.equals(e)).findFirst().orElse(null);
                if (t == null) {
                    resultUpdateOrSave.add(myNew); // create
                }
            }
	}

    @Override
    public String uploadFile(String checksumOld, String tempUpladedFile, String destinationFilePath) throws Exception {
        return null;
    }







    @Autowired
    private OwnerAdminService ownerService ;

    public CarAdminServiceImpl(CarDao dao) {
        this.dao = dao;
    }

    private CarDao dao;
}
