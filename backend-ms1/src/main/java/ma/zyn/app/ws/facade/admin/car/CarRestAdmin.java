package  ma.zyn.app.ws.facade.admin.car;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.http.HttpStatus;
import java.util.Arrays;
import java.util.ArrayList;

import ma.zyn.app.bean.core.car.Car;
import ma.zyn.app.dao.criteria.core.car.CarCriteria;
import ma.zyn.app.service.facade.admin.car.CarAdminService;
import ma.zyn.app.ws.converter.car.CarConverter;
import ma.zyn.app.ws.dto.car.CarDto;
import ma.zyn.app.zynerator.controller.AbstractController;
import ma.zyn.app.zynerator.dto.AuditEntityDto;
import ma.zyn.app.zynerator.util.PaginatedList;


import org.springframework.core.io.InputStreamResource;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import ma.zyn.app.zynerator.process.Result;


import org.springframework.web.multipart.MultipartFile;
import ma.zyn.app.zynerator.dto.FileTempDto;

@RestController
@RequestMapping("/api/admin/car/")
public class CarRestAdmin {




    @Operation(summary = "Finds a list of all cars")
    @GetMapping("")
    public ResponseEntity<List<CarDto>> findAll() throws Exception {
        ResponseEntity<List<CarDto>> res = null;
        List<Car> list = service.findAll();
        HttpStatus status = HttpStatus.NO_CONTENT;
            converter.initObject(true);
        List<CarDto> dtos  = converter.toDto(list);
        if (dtos != null && !dtos.isEmpty())
            status = HttpStatus.OK;
        res = new ResponseEntity<>(dtos, status);
        return res;
    }

    @Operation(summary = "Finds an optimized list of all cars")
    @GetMapping("optimized")
    public ResponseEntity<List<CarDto>> findAllOptimized() throws Exception {
        ResponseEntity<List<CarDto>> res = null;
        List<Car> list = service.findAllOptimized();
        HttpStatus status = HttpStatus.NO_CONTENT;
        converter.initObject(true);
        List<CarDto> dtos  = converter.toDto(list);
        if (dtos != null && !dtos.isEmpty())
            status = HttpStatus.OK;
        res = new ResponseEntity<>(dtos, status);
        return res;
    }

    @Operation(summary = "Finds a car by id")
    @GetMapping("id/{id}")
    public ResponseEntity<CarDto> findById(@PathVariable Long id) {
        Car t = service.findById(id);
        if (t != null) {
            converter.init(true);
            CarDto dto = converter.toDto(t);
            return getDtoResponseEntity(dto);
        }
        return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
    }

    @Operation(summary = "Finds a car by licensePlate")
    @GetMapping("licensePlate/{licensePlate}")
    public ResponseEntity<CarDto> findByLicensePlate(@PathVariable String licensePlate) {
	    Car t = service.findByReferenceEntity(new Car(licensePlate));
        if (t != null) {
            converter.init(true);
            CarDto dto = converter.toDto(t);
            return getDtoResponseEntity(dto);
        }
        return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
    }

    @Operation(summary = "Saves the specified  car")
    @PostMapping("")
    public ResponseEntity<CarDto> save(@RequestBody CarDto dto) throws Exception {
        if(dto!=null){
            converter.init(true);
            Car myT = converter.toItem(dto);
            Car t = service.create(myT);
            if (t == null) {
                return new ResponseEntity<>(null, HttpStatus.IM_USED);
            }else{
                CarDto myDto = converter.toDto(t);
                return new ResponseEntity<>(myDto, HttpStatus.CREATED);
            }
        }else {
            return new ResponseEntity<>(dto, HttpStatus.NO_CONTENT);
        }
    }

    @Operation(summary = "Updates the specified  car")
    @PutMapping("")
    public ResponseEntity<CarDto> update(@RequestBody CarDto dto) throws Exception {
        ResponseEntity<CarDto> res ;
        if (dto.getId() == null || service.findById(dto.getId()) == null)
            res = new ResponseEntity<>(HttpStatus.CONFLICT);
        else {
            Car t = service.findById(dto.getId());
            converter.copy(dto,t);
            Car updated = service.update(t);
            CarDto myDto = converter.toDto(updated);
            res = new ResponseEntity<>(myDto, HttpStatus.OK);
        }
        return res;
    }

    @Operation(summary = "Delete list of car")
    @PostMapping("multiple")
    public ResponseEntity<List<CarDto>> delete(@RequestBody List<CarDto> dtos) throws Exception {
        ResponseEntity<List<CarDto>> res ;
        HttpStatus status = HttpStatus.CONFLICT;
        if (dtos != null && !dtos.isEmpty()) {
            converter.init(false);
            List<Car> ts = converter.toItem(dtos);
            service.delete(ts);
            status = HttpStatus.OK;
        }
        res = new ResponseEntity<>(dtos, status);
        return res;
    }

    @Operation(summary = "Delete the specified car")
    @DeleteMapping("id/{id}")
    public ResponseEntity<Long> deleteById(@PathVariable Long id) throws Exception {
        ResponseEntity<Long> res;
        HttpStatus status = HttpStatus.PRECONDITION_FAILED;
        if (id != null) {
            boolean resultDelete = service.deleteById(id);
            if (resultDelete) {
                status = HttpStatus.OK;
            }
        }
        res = new ResponseEntity<>(id, status);
        return res;
    }


    @Operation(summary = "Finds a car and associated list by id")
    @GetMapping("detail/id/{id}")
    public ResponseEntity<CarDto> findWithAssociatedLists(@PathVariable Long id) {
        Car loaded =  service.findWithAssociatedLists(id);
        converter.init(true);
        CarDto dto = converter.toDto(loaded);
        return new ResponseEntity<>(dto, HttpStatus.OK);
    }

    @Operation(summary = "Finds cars by criteria")
    @PostMapping("find-by-criteria")
    public ResponseEntity<List<CarDto>> findByCriteria(@RequestBody CarCriteria criteria) throws Exception {
        ResponseEntity<List<CarDto>> res = null;
        List<Car> list = service.findByCriteria(criteria);
        HttpStatus status = HttpStatus.NO_CONTENT;
        converter.initObject(true);
        List<CarDto> dtos  = converter.toDto(list);
        if (dtos != null && !dtos.isEmpty())
            status = HttpStatus.OK;

        res = new ResponseEntity<>(dtos, status);
        return res;
    }

    @Operation(summary = "Finds paginated cars by criteria")
    @PostMapping("find-paginated-by-criteria")
    public ResponseEntity<PaginatedList> findPaginatedByCriteria(@RequestBody CarCriteria criteria) throws Exception {
        List<Car> list = service.findPaginatedByCriteria(criteria, criteria.getPage(), criteria.getMaxResults(), criteria.getSortOrder(), criteria.getSortField());
        converter.initObject(true);
        List<CarDto> dtos = converter.toDto(list);
        PaginatedList paginatedList = new PaginatedList();
        paginatedList.setList(dtos);
        if (dtos != null && !dtos.isEmpty()) {
            int dateSize = service.getDataSize(criteria);
            paginatedList.setDataSize(dateSize);
        }
        return new ResponseEntity<>(paginatedList, HttpStatus.OK);
    }

    @Operation(summary = "Gets car data size by criteria")
    @PostMapping("data-size-by-criteria")
    public ResponseEntity<Integer> getDataSize(@RequestBody CarCriteria criteria) throws Exception {
        int count = service.getDataSize(criteria);
        return new ResponseEntity<Integer>(count, HttpStatus.OK);
    }
	
	public List<CarDto> findDtos(List<Car> list){
        converter.initObject(true);
        List<CarDto> dtos = converter.toDto(list);
        return dtos;
    }

    private ResponseEntity<CarDto> getDtoResponseEntity(CarDto dto) {
        return new ResponseEntity<>(dto, HttpStatus.OK);
    }




   public CarRestAdmin(CarAdminService service, CarConverter converter){
        this.service = service;
        this.converter = converter;
    }

    private final CarAdminService service;
    private final CarConverter converter;





}
