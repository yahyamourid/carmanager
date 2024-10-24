package  ma.zyn.app.dao.specification.core.car;

import ma.zyn.app.dao.criteria.core.car.CarCriteria;
import ma.zyn.app.bean.core.car.Car;
import ma.zyn.app.zynerator.specification.AbstractSpecification;


public class CarSpecification extends  AbstractSpecification<CarCriteria, Car>  {

    @Override
    public void constructPredicates() {
        addPredicateId("id", criteria);
        addPredicate("brand", criteria.getBrand(),criteria.getBrandLike());
        addPredicate("model", criteria.getModel(),criteria.getModelLike());
        addPredicateInt("year", criteria.getYear(), criteria.getYearMin(), criteria.getYearMax());
        addPredicate("color", criteria.getColor(),criteria.getColorLike());
        addPredicate("licensePlate", criteria.getLicensePlate(),criteria.getLicensePlateLike());
        addPredicateFk("owner","id", criteria.getOwner()==null?null:criteria.getOwner().getId());
        addPredicateFk("owner","id", criteria.getOwners());
        addPredicateFk("owner","email", criteria.getOwner()==null?null:criteria.getOwner().getEmail());
    }

    public CarSpecification(CarCriteria criteria) {
        super(criteria);
    }

    public CarSpecification(CarCriteria criteria, boolean distinct) {
        super(criteria, distinct);
    }

}
