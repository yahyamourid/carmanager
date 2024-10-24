package  ma.zyn.app.dao.specification.core.owner;

import ma.zyn.app.dao.criteria.core.owner.OwnerCriteria;
import ma.zyn.app.bean.core.owner.Owner;
import ma.zyn.app.zynerator.specification.AbstractSpecification;


public class OwnerSpecification extends  AbstractSpecification<OwnerCriteria, Owner>  {

    @Override
    public void constructPredicates() {
        addPredicateId("id", criteria);
        addPredicate("firstName", criteria.getFirstName(),criteria.getFirstNameLike());
        addPredicate("lastName", criteria.getLastName(),criteria.getLastNameLike());
        addPredicate("phoneNumber", criteria.getPhoneNumber(),criteria.getPhoneNumberLike());
        addPredicateBool("passwordChanged", criteria.getPasswordChanged());
        addPredicateBool("accountNonLocked", criteria.getAccountNonLocked());
        addPredicate("password", criteria.getPassword(),criteria.getPasswordLike());
        addPredicate("email", criteria.getEmail(),criteria.getEmailLike());
        addPredicateBool("enabled", criteria.getEnabled());
        addPredicateBool("credentialsNonExpired", criteria.getCredentialsNonExpired());
        addPredicateBool("accountNonExpired", criteria.getAccountNonExpired());
        addPredicate("username", criteria.getUsername(),criteria.getUsernameLike());
    }

    public OwnerSpecification(OwnerCriteria criteria) {
        super(criteria);
    }

    public OwnerSpecification(OwnerCriteria criteria, boolean distinct) {
        super(criteria, distinct);
    }

}
