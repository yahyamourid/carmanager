package ma.zyn.app.bean.core.car;






import ma.zyn.app.bean.core.owner.Owner;


import com.fasterxml.jackson.annotation.JsonInclude;
import ma.zyn.app.zynerator.bean.BaseEntity;
import jakarta.persistence.*;
import java.util.Objects;

@Entity
@Table(name = "car")
@JsonInclude(JsonInclude.Include.NON_NULL)
@SequenceGenerator(name="car_seq",sequenceName="car_seq",allocationSize=1, initialValue = 1)
public class Car  extends BaseEntity     {




    @Column(length = 500)
    private String brand;

    @Column(length = 500)
    private String model;

    private Integer year = 0;

    @Column(length = 500)
    private String color;

    @Column(length = 500)
    private String licensePlate;

    private Owner owner ;


    public Car(){
        super();
    }

    public Car(Long id){
        this.id = id;
    }

    public Car(Long id,String licensePlate){
        this.id = id;
        this.licensePlate = licensePlate ;
    }
    public Car(String licensePlate){
        this.licensePlate = licensePlate ;
    }




    @Id
    @Column(name = "id")
    @GeneratedValue(strategy =  GenerationType.SEQUENCE,generator="car_seq")
      @Override
    public Long getId(){
        return this.id;
    }
        @Override
    public void setId(Long id){
        this.id = id;
    }
    public String getBrand(){
        return this.brand;
    }
    public void setBrand(String brand){
        this.brand = brand;
    }
    public String getModel(){
        return this.model;
    }
    public void setModel(String model){
        this.model = model;
    }
    public Integer getYear(){
        return this.year;
    }
    public void setYear(Integer year){
        this.year = year;
    }
    public String getColor(){
        return this.color;
    }
    public void setColor(String color){
        this.color = color;
    }
    public String getLicensePlate(){
        return this.licensePlate;
    }
    public void setLicensePlate(String licensePlate){
        this.licensePlate = licensePlate;
    }
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "owner")
    public Owner getOwner(){
        return this.owner;
    }
    public void setOwner(Owner owner){
        this.owner = owner;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Car car = (Car) o;
        return id != null && id.equals(car.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

}

