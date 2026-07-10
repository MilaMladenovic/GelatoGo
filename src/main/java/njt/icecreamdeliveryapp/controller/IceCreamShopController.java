/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package njt.icecreamdeliveryapp.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import java.util.List;
import njt.icecreamdeliveryapp.dto.impl.IceCreamShopDto;
import njt.icecreamdeliveryapp.service.IceCreamShopService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

/**
 *
 * @author milam
 */
@CrossOrigin(origins = "http://localhost:3000") 
@RestController 
@RequestMapping("/api/icecreamshop")
public class IceCreamShopController {
    private final IceCreamShopService iceCreamShopService;

    public IceCreamShopController(IceCreamShopService iceCreamShopService) {
        this.iceCreamShopService = iceCreamShopService;
    }
    
    @GetMapping 
    @Operation(summary = "Retrieve all IceCreamShop entities.")
    @ApiResponse(responseCode = "200", content = {
        @Content(schema = @Schema(implementation = IceCreamShopDto.class), mediaType = "applicationJson")
    })
    public ResponseEntity<List<IceCreamShopDto>> getAll() {
        return new ResponseEntity<>(iceCreamShopService.findAll(), HttpStatus.OK);
    }
    
    @GetMapping("/{id}")
    public ResponseEntity<IceCreamShopDto> getById(
            @NotNull(message = "Should not be null or empty.")
            @PathVariable(value = "id") Long id) {
        try {
            return new ResponseEntity<>(iceCreamShopService.findById(id), HttpStatus.OK);
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "IceCreamShopController exception ");
        }
    }
    
    @PostMapping 
    @Operation(summary = "Create a new IceCreamShop entity.")
    @ApiResponse(responseCode = "201", content = {
        @Content(schema = @Schema(implementation = IceCreamShopDto.class), mediaType = "applicationJson")
    })
    public ResponseEntity<IceCreamShopDto> addIceCreamShop(@Valid @RequestBody @NotNull IceCreamShopDto iceCreamShopDto) {
        try {
            IceCreamShopDto saved = iceCreamShopService.create(iceCreamShopDto);
            return new ResponseEntity<>(saved, HttpStatus.CREATED);
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Error while saving IceCreamShop entity.");
        }
    }
    @DeleteMapping("/{id}")
    public ResponseEntity<String> delete(@PathVariable(value = "id") Long id){
        try {
            iceCreamShopService.deleteById(id);
            return new ResponseEntity<>("IceCreamShop successfully deleted.", HttpStatus.OK);
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "IceCreamShop does not exist: "+id);
        }
    }
    
    @PutMapping("/{id}")
    @Operation(summary = "Update an existing IceCreamShop entity.")
    @ApiResponse(responseCode = "200", content = {
        @Content(schema = @Schema(implementation = IceCreamShopDto.class), mediaType = "applicationJson")
    })
    public ResponseEntity<IceCreamShopDto> updateIceCreamShop(
            @PathVariable Long id, 
            @Valid @RequestBody IceCreamShopDto iceCreamShopDto){
        System.out.println("PUT controller reached");
        try {
            iceCreamShopDto.setId(id); // Osiguravamo da se azurira pravi entitet
            IceCreamShopDto updated = iceCreamShopService.update(iceCreamShopDto);
            return new ResponseEntity<>(updated, HttpStatus.OK);
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Error while updating IceCreamShop: "+id);
        }
    }
}
