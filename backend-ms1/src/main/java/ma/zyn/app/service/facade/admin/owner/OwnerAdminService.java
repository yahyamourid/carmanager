package ma.zyn.app.service.facade.admin.owner;

import java.util.List;
import ma.zyn.app.bean.core.owner.Owner;
import ma.zyn.app.dao.criteria.core.owner.OwnerCriteria;
import ma.zyn.app.zynerator.service.IService;



public interface OwnerAdminService {


    Owner findByUsername(String username);
    boolean changePassword(String username, String newPassword);





	Owner create(Owner t);

    Owner update(Owner t);

    List<Owner> update(List<Owner> ts,boolean createIfNotExist);

    Owner findById(Long id);

    Owner findOrSave(Owner t);

    Owner findByReferenceEntity(Owner t);

    Owner findWithAssociatedLists(Long id);

    List<Owner> findAllOptimized();

    List<Owner> findAll();

    List<Owner> findByCriteria(OwnerCriteria criteria);

    List<Owner> findPaginatedByCriteria(OwnerCriteria criteria, int page, int pageSize, String order, String sortField);

    int getDataSize(OwnerCriteria criteria);

    List<Owner> delete(List<Owner> ts);

    boolean deleteById(Long id);

    List<List<Owner>> getToBeSavedAndToBeDeleted(List<Owner> oldList, List<Owner> newList);

    public String uploadFile(String checksumOld, String tempUpladedFile,String destinationFilePath) throws Exception ;

}
