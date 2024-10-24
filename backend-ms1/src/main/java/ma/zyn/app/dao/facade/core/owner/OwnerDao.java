package ma.zyn.app.dao.facade.core.owner;

import org.springframework.data.jpa.repository.Query;
import ma.zyn.app.zynerator.repository.AbstractRepository;
import ma.zyn.app.bean.core.owner.Owner;
import org.springframework.stereotype.Repository;
import ma.zyn.app.bean.core.owner.Owner;
import java.util.List;


@Repository
public interface OwnerDao extends AbstractRepository<Owner,Long>  {
    Owner findByEmail(String email);
    int deleteByEmail(String email);

    Owner findByUsername(String username);

    @Query("SELECT NEW Owner(item.id,item.email) FROM Owner item")
    List<Owner> findAllOptimized();

}
