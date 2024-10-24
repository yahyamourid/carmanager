package  ma.zyn.app.ws.dto.car;

import ma.zyn.app.zynerator.dto.AuditBaseDto;
import com.fasterxml.jackson.annotation.JsonInclude;



import ma.zyn.app.ws.dto.owner.OwnerDto;


@JsonInclude(JsonInclude.Include.NON_NULL)
public class CarDto  extends AuditBaseDto {

    private String brand  ;
    private String model  ;
    private Integer year  = 0 ;
    private String color  ;
    private String licensePlate  ;

    private OwnerDto owner ;



    public CarDto(){
        super();
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


    public OwnerDto getOwner(){
        return this.owner;
    }

    public void setOwner(OwnerDto owner){
        this.owner = owner;
    }






}
