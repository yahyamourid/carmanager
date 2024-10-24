package  ma.zyn.app.ws.facade.admin.owner;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.http.HttpStatus;
import java.util.Arrays;
import java.util.ArrayList;

import ma.zyn.app.bean.core.owner.Owner;
import ma.zyn.app.dao.criteria.core.owner.OwnerCriteria;
import ma.zyn.app.service.facade.admin.owner.OwnerAdminService;
import ma.zyn.app.ws.converter.owner.OwnerConverter;
import ma.zyn.app.ws.dto.owner.OwnerDto;
import ma.zyn.app.zynerator.controller.AbstractController;
import ma.zyn.app.zynerator.dto.AuditEntityDto;
import ma.zyn.app.zynerator.util.PaginatedList;


import ma.zyn.app.zynerator.security.bean.User;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import ma.zyn.app.zynerator.process.Result;


import org.springframework.web.multipart.MultipartFile;
import ma.zyn.app.zynerator.dto.FileTempDto;

@RestController
@RequestMapping("/api/admin/owner/")
public class OwnerRestAdmin {




    @Operation(summary = "Finds a list of all owners")
    @GetMapping("")
    public ResponseEntity<List<OwnerDto>> findAll() throws Exception {
        ResponseEntity<List<OwnerDto>> res = null;
        List<Owner> list = service.findAll();
        HttpStatus status = HttpStatus.NO_CONTENT;
        List<OwnerDto> dtos  = converter.toDto(list);
        if (dtos != null && !dtos.isEmpty())
            status = HttpStatus.OK;
        res = new ResponseEntity<>(dtos, status);
        return res;
    }

    @Operation(summary = "Finds an optimized list of all owners")
    @GetMapping("optimized")
    public ResponseEntity<List<OwnerDto>> findAllOptimized() throws Exception {
        ResponseEntity<List<OwnerDto>> res = null;
        List<Owner> list = service.findAllOptimized();
        HttpStatus status = HttpStatus.NO_CONTENT;
        List<OwnerDto> dtos  = converter.toDto(list);
        if (dtos != null && !dtos.isEmpty())
            status = HttpStatus.OK;
        res = new ResponseEntity<>(dtos, status);
        return res;
    }

    @Operation(summary = "Finds a owner by id")
    @GetMapping("id/{id}")
    public ResponseEntity<OwnerDto> findById(@PathVariable Long id) {
        Owner t = service.findById(id);
        if (t != null) {
            OwnerDto dto = converter.toDto(t);
            return getDtoResponseEntity(dto);
        }
        return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
    }

    @Operation(summary = "Finds a owner by email")
    @GetMapping("email/{email}")
    public ResponseEntity<OwnerDto> findByEmail(@PathVariable String email) {
	    Owner t = service.findByReferenceEntity(new Owner(email));
        if (t != null) {
            OwnerDto dto = converter.toDto(t);
            return getDtoResponseEntity(dto);
        }
        return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
    }

    @Operation(summary = "Saves the specified  owner")
    @PostMapping("")
    public ResponseEntity<OwnerDto> save(@RequestBody OwnerDto dto) throws Exception {
        if(dto!=null){
            Owner myT = converter.toItem(dto);
            Owner t = service.create(myT);
            if (t == null) {
                return new ResponseEntity<>(null, HttpStatus.IM_USED);
            }else{
                OwnerDto myDto = converter.toDto(t);
                return new ResponseEntity<>(myDto, HttpStatus.CREATED);
            }
        }else {
            return new ResponseEntity<>(dto, HttpStatus.NO_CONTENT);
        }
    }

    @Operation(summary = "Updates the specified  owner")
    @PutMapping("")
    public ResponseEntity<OwnerDto> update(@RequestBody OwnerDto dto) throws Exception {
        ResponseEntity<OwnerDto> res ;
        if (dto.getId() == null || service.findById(dto.getId()) == null)
            res = new ResponseEntity<>(HttpStatus.CONFLICT);
        else {
            Owner t = service.findById(dto.getId());
            converter.copy(dto,t);
            Owner updated = service.update(t);
            OwnerDto myDto = converter.toDto(updated);
            res = new ResponseEntity<>(myDto, HttpStatus.OK);
        }
        return res;
    }

    @Operation(summary = "Delete list of owner")
    @PostMapping("multiple")
    public ResponseEntity<List<OwnerDto>> delete(@RequestBody List<OwnerDto> dtos) throws Exception {
        ResponseEntity<List<OwnerDto>> res ;
        HttpStatus status = HttpStatus.CONFLICT;
        if (dtos != null && !dtos.isEmpty()) {
            List<Owner> ts = converter.toItem(dtos);
            service.delete(ts);
            status = HttpStatus.OK;
        }
        res = new ResponseEntity<>(dtos, status);
        return res;
    }

    @Operation(summary = "Delete the specified owner")
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


    @Operation(summary = "Finds a owner and associated list by id")
    @GetMapping("detail/id/{id}")
    public ResponseEntity<OwnerDto> findWithAssociatedLists(@PathVariable Long id) {
        Owner loaded =  service.findWithAssociatedLists(id);
        OwnerDto dto = converter.toDto(loaded);
        return new ResponseEntity<>(dto, HttpStatus.OK);
    }

    @Operation(summary = "Finds owners by criteria")
    @PostMapping("find-by-criteria")
    public ResponseEntity<List<OwnerDto>> findByCriteria(@RequestBody OwnerCriteria criteria) throws Exception {
        ResponseEntity<List<OwnerDto>> res = null;
        List<Owner> list = service.findByCriteria(criteria);
        HttpStatus status = HttpStatus.NO_CONTENT;
        List<OwnerDto> dtos  = converter.toDto(list);
        if (dtos != null && !dtos.isEmpty())
            status = HttpStatus.OK;

        res = new ResponseEntity<>(dtos, status);
        return res;
    }

    @Operation(summary = "Finds paginated owners by criteria")
    @PostMapping("find-paginated-by-criteria")
    public ResponseEntity<PaginatedList> findPaginatedByCriteria(@RequestBody OwnerCriteria criteria) throws Exception {
        List<Owner> list = service.findPaginatedByCriteria(criteria, criteria.getPage(), criteria.getMaxResults(), criteria.getSortOrder(), criteria.getSortField());
        List<OwnerDto> dtos = converter.toDto(list);
        PaginatedList paginatedList = new PaginatedList();
        paginatedList.setList(dtos);
        if (dtos != null && !dtos.isEmpty()) {
            int dateSize = service.getDataSize(criteria);
            paginatedList.setDataSize(dateSize);
        }
        return new ResponseEntity<>(paginatedList, HttpStatus.OK);
    }

    @Operation(summary = "Gets owner data size by criteria")
    @PostMapping("data-size-by-criteria")
    public ResponseEntity<Integer> getDataSize(@RequestBody OwnerCriteria criteria) throws Exception {
        int count = service.getDataSize(criteria);
        return new ResponseEntity<Integer>(count, HttpStatus.OK);
    }
	
	public List<OwnerDto> findDtos(List<Owner> list){
        List<OwnerDto> dtos = converter.toDto(list);
        return dtos;
    }

    private ResponseEntity<OwnerDto> getDtoResponseEntity(OwnerDto dto) {
        return new ResponseEntity<>(dto, HttpStatus.OK);
    }



    @Operation(summary = "Change password to the specified  utilisateur")
    @PutMapping("changePassword")
    public boolean changePassword(@RequestBody User dto) throws Exception {
        return service.changePassword(dto.getUsername(),dto.getPassword());
    }

   public OwnerRestAdmin(OwnerAdminService service, OwnerConverter converter){
        this.service = service;
        this.converter = converter;
    }

    private final OwnerAdminService service;
    private final OwnerConverter converter;





}
