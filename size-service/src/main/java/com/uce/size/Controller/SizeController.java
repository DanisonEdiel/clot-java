package com.uce.size.Controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.uce.size.Model.Size;
import com.uce.size.Service.SizeService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
@RequestMapping("/sizes")
@Tag(name = "Api Rest for sizes use Swagger 3 - Brand")
public class SizeController {

    @Autowired
    private SizeService sizeService;

    @PostMapping
    @Operation(summary = "This endpoint use for create sizes")
    public ResponseEntity<?> saveSize(@RequestBody Size size) {
        try {
            Size savedColor = sizeService.savedSize(size);
            return new ResponseEntity<>(savedColor, HttpStatus.CREATED);
        } catch (DataIntegrityViolationException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping
    @Operation(summary = "This endpoint use for list all sizes")
    public ResponseEntity<?> getAll() {
        return ResponseEntity.ok(sizeService.getAll());
    }

    @GetMapping("/{id}")
    @Operation(summary = "This endpoint use for find by id size")
    private ResponseEntity<?> getBrand(@PathVariable Long id) {
        Size size = sizeService.findById(id);
        if (size != null) {
            return new ResponseEntity<>(size, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

}
