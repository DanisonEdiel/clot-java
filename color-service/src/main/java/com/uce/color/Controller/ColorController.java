package com.uce.color.Controller;

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

import com.uce.color.Model.Color;
import com.uce.color.Service.ColorService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
@RequestMapping("/colors")
@Tag(name = "Api Rest for colors use Swagger 3 - Color")
public class ColorController {

    @Autowired
    private ColorService colorService;

    @PostMapping
    @Operation(summary = "This endpoint use for created colors")
    public ResponseEntity<?> saveColor(@RequestBody Color color) {
        try {
            Color savedColor = colorService.savedColor(color);
            return new ResponseEntity<>(savedColor, HttpStatus.CREATED);
        } catch (DataIntegrityViolationException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping
    @Operation(summary = "This endpoint use for list all colors")
    public ResponseEntity<?> getAll() {
        return ResponseEntity.ok(colorService.getAll());
    }

    @GetMapping("/{id}")
    @Operation(summary = "This endpoint use for find by id color")
    private ResponseEntity<?> getBrand(@PathVariable Long id) {
        Color color = colorService.findById(id);
        if (color != null) {
            return new ResponseEntity<>(color, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

}
