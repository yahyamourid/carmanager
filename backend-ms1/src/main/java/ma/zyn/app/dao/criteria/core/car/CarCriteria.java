package  ma.zyn.app.dao.criteria.core.car;


import ma.zyn.app.dao.criteria.core.owner.OwnerCriteria;

import ma.zyn.app.zynerator.criteria.BaseCriteria;

import java.util.List;

public class CarCriteria extends  BaseCriteria  {

    private String brand;
    private String brandLike;
    private String model;
    private String modelLike;
    private String year;
    private String yearMin;
    private String yearMax;
    private String color;
    private String colorLike;
    private String licensePlate;
    private String licensePlateLike;

    private OwnerCriteria owner ;
    private List<OwnerCriteria> owners ;


    public String getBrand(){
        return this.brand;
    }
    public void setBrand(String brand){
        this.brand = brand;
    }
    public String getBrandLike(){
        return this.brandLike;
    }
    public void setBrandLike(String brandLike){
        this.brandLike = brandLike;
    }

    public String getModel(){
        return this.model;
    }
    public void setModel(String model){
        this.model = model;
    }
    public String getModelLike(){
        return this.modelLike;
    }
    public void setModelLike(String modelLike){
        this.modelLike = modelLike;
    }

    public String getYear(){
        return this.year;
    }
    public void setYear(String year){
        this.year = year;
    }   
    public String getYearMin(){
        return this.yearMin;
    }
    public void setYearMin(String yearMin){
        this.yearMin = yearMin;
    }
    public String getYearMax(){
        return this.yearMax;
    }
    public void setYearMax(String yearMax){
        this.yearMax = yearMax;
    }
      
    public String getColor(){
        return this.color;
    }
    public void setColor(String color){
        this.color = color;
    }
    public String getColorLike(){
        return this.colorLike;
    }
    public void setColorLike(String colorLike){
        this.colorLike = colorLike;
    }

    public String getLicensePlate(){
        return this.licensePlate;
    }
    public void setLicensePlate(String licensePlate){
        this.licensePlate = licensePlate;
    }
    public String getLicensePlateLike(){
        return this.licensePlateLike;
    }
    public void setLicensePlateLike(String licensePlateLike){
        this.licensePlateLike = licensePlateLike;
    }


    public OwnerCriteria getOwner(){
        return this.owner;
    }

    public void setOwner(OwnerCriteria owner){
        this.owner = owner;
    }
    public List<OwnerCriteria> getOwners(){
        return this.owners;
    }

    public void setOwners(List<OwnerCriteria> owners){
        this.owners = owners;
    }
}
