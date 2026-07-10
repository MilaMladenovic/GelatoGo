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
import njt.icecreamdeliveryapp.dto.impl.IceCreamDto;
import njt.icecreamdeliveryapp.service.IceCreamService;
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
@RequestMapping("/api/icecream")
public class IceCreamController {

    private final IceCreamService iceCreamService;

    public IceCreamController(IceCreamService iceCreamService) {
        this.iceCreamService = iceCreamService;
    }

    @GetMapping
    @Operation(summary = "Retrieve all IceCream entities.")
    public ResponseEntity<List<IceCreamDto>> getAll() {
        return new ResponseEntity<>(iceCreamService.findAll(), HttpStatus.OK);
        
    }

    @GetMapping("/{id}")
    public ResponseEntity<IceCreamDto> getById(
            @NotNull(message = "Should not be null or empty.")
            @PathVariable(value = "id") Long id) {
        try {
            return new ResponseEntity<>(iceCreamService.findById(id), HttpStatus.OK);
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "IceCreamController exception ");
        }
    }

    @PostMapping
    @Operation(summary = "Create a new IceCreamShop entity.")
    @ApiResponse(responseCode = "201", content = {
        @Content(schema = @Schema(implementation = IceCreamDto.class), mediaType = "applicationJson")
    })
    public ResponseEntity<IceCreamDto> addIceCream(@Valid @RequestBody @NotNull IceCreamDto iceCreamDto) {
        try {
            IceCreamDto saved = iceCreamService.create(iceCreamDto);
            return new ResponseEntity<>(saved, HttpStatus.CREATED);
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Error while saving IceCream entity.");
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> delete(@PathVariable(value = "id") Long id) {
        try {
            iceCreamService.deleteById(id);
            return new ResponseEntity<>("IceCream successfully deleted.", HttpStatus.OK);
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "IceCream does not exist: " + id);
        }
    }

    @PutMapping("/{id}")
    @Operation(summary = "Update an existing IceCream entity.")
    @ApiResponse(responseCode = "200", content = {
        @Content(schema = @Schema(implementation = IceCreamDto.class), mediaType = "application/json")
    })
    public ResponseEntity<IceCreamDto> updateIceCream(
            @PathVariable Long id,
            @Valid @RequestBody IceCreamDto iceCreamDto) {

        System.out.println("PUT controller reached");

        try {
            iceCreamDto.setId(id); // Osiguravamo da se ažurira pravi entitet
            IceCreamDto updated = iceCreamService.update(iceCreamDto);
            return new ResponseEntity<>(updated, HttpStatus.OK);
        } catch (Exception e) {
            throw new ResponseStatusException(
                    HttpStatus.NOT_FOUND,
                    "Error while updating IceCream: " + id
            );
        }
    }

    @GetMapping("/icecreamshop/{iceCreamShopId}")
    @Operation(summary = "Retrieve all ice creams for a given ice cream shop.")
    public ResponseEntity<List<IceCreamDto>> getByIceCreamShop(@PathVariable Long iceCreamShopId) {
        List<IceCreamDto> iceCreams = iceCreamService.findByIceCreamShop(iceCreamShopId);
        return new ResponseEntity<>(iceCreams, HttpStatus.OK);
    }
}
