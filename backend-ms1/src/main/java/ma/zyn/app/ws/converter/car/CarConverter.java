package  ma.zyn.app.ws.converter.car;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.beans.BeanUtils;
import ma.zyn.app.zynerator.converter.AbstractConverterHelper;

import java.util.ArrayList;
import java.util.List;

import ma.zyn.app.ws.converter.owner.OwnerConverter;
import ma.zyn.app.bean.core.owner.Owner;



import ma.zyn.app.zynerator.util.StringUtil;
import ma.zyn.app.zynerator.converter.AbstractConverter;
import ma.zyn.app.zynerator.util.DateUtil;
import ma.zyn.app.bean.core.car.Car;
import ma.zyn.app.ws.dto.car.CarDto;

@Component
public class CarConverter {

    @Autowired
    private OwnerConverter ownerConverter ;
    private boolean owner;

    public  CarConverter() {
        initObject(true);
    }

    public Car toItem(CarDto dto) {
        if (dto == null) {
            return null;
        } else {
        Car item = new Car();
            if(StringUtil.isNotEmpty(dto.getId()))
                item.setId(dto.getId());
            if(StringUtil.isNotEmpty(dto.getBrand()))
                item.setBrand(dto.getBrand());
            if(StringUtil.isNotEmpty(dto.getModel()))
                item.setModel(dto.getModel());
            if(StringUtil.isNotEmpty(dto.getYear()))
                item.setYear(dto.getYear());
            if(StringUtil.isNotEmpty(dto.getColor()))
                item.setColor(dto.getColor());
            if(StringUtil.isNotEmpty(dto.getLicensePlate()))
                item.setLicensePlate(dto.getLicensePlate());
            if(this.owner && dto.getOwner()!=null)
                item.setOwner(ownerConverter.toItem(dto.getOwner())) ;




        return item;
        }
    }


    public CarDto toDto(Car item) {
        if (item == null) {
            return null;
        } else {
            CarDto dto = new CarDto();
            if(StringUtil.isNotEmpty(item.getId()))
                dto.setId(item.getId());
            if(StringUtil.isNotEmpty(item.getBrand()))
                dto.setBrand(item.getBrand());
            if(StringUtil.isNotEmpty(item.getModel()))
                dto.setModel(item.getModel());
            if(StringUtil.isNotEmpty(item.getYear()))
                dto.setYear(item.getYear());
            if(StringUtil.isNotEmpty(item.getColor()))
                dto.setColor(item.getColor());
            if(StringUtil.isNotEmpty(item.getLicensePlate()))
                dto.setLicensePlate(item.getLicensePlate());
            if(this.owner && item.getOwner()!=null) {
                dto.setOwner(ownerConverter.toDto(item.getOwner())) ;

            }


        return dto;
        }
    }

    public void init(boolean value) {
        initObject(value);
    }

    public void initObject(boolean value) {
        this.owner = value;
    }
	
    public List<Car> toItem(List<CarDto> dtos) {
        List<Car> items = new ArrayList<>();
        if (dtos != null && !dtos.isEmpty()) {
            for (CarDto dto : dtos) {
                items.add(toItem(dto));
            }
        }
        return items;
    }


    public List<CarDto> toDto(List<Car> items) {
        List<CarDto> dtos = new ArrayList<>();
        if (items != null && !items.isEmpty()) {
            for (Car item : items) {
                dtos.add(toDto(item));
            }
        }
        return dtos;
    }


    public void copy(CarDto dto, Car t) {
		BeanUtils.copyProperties(dto, t, AbstractConverterHelper.getNullPropertyNames(dto));
        if(t.getOwner() == null  && dto.getOwner() != null){
            t.setOwner(new Owner());
        }else if (t.getOwner() != null  && dto.getOwner() != null){
            t.setOwner(null);
            t.setOwner(new Owner());
        }
        if (dto.getOwner() != null)
        ownerConverter.copy(dto.getOwner(), t.getOwner());
    }

    public List<Car> copy(List<CarDto> dtos) {
        List<Car> result = new ArrayList<>();
        if (dtos != null) {
            for (CarDto dto : dtos) {
                Car instance = new Car();
                copy(dto, instance);
                result.add(instance);
            }
        }
        return result.isEmpty() ? null : result;
    }


    public OwnerConverter getOwnerConverter(){
        return this.ownerConverter;
    }
    public void setOwnerConverter(OwnerConverter ownerConverter ){
        this.ownerConverter = ownerConverter;
    }
    public boolean  isOwner(){
        return this.owner;
    }
    public void  setOwner(boolean owner){
        this.owner = owner;
    }
}
