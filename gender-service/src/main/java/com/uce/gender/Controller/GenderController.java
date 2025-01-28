package com.uce.gender.Controller;

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

import com.uce.gender.Model.Gender;
import com.uce.gender.Service.GenderService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
@RequestMapping("/genders")
@Tag(name = "Api Rest for genders use Swagger 3 - Brand")
public class GenderController {

    @Autowired
    private GenderService genderService;

    @PostMapping
    @Operation(summary = "This endpoint use for create gender")
    public ResponseEntity<?> saveGender(@RequestBody Gender gender) {
        try {
            Gender savedColor = genderService.savedGender(gender);
            return new ResponseEntity<>(savedColor, HttpStatus.CREATED);
        } catch (DataIntegrityViolationException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping
    @Operation(summary = "This endpoint use for list all genders")
    public ResponseEntity<?> getAll() {
        return ResponseEntity.ok(genderService.getAll());
    }

    @GetMapping("/{id}")
    @Operation(summary = "This endpoint use for find by id gender")
    private ResponseEntity<?> getBrand(@PathVariable Long id) {
        Gender gender = genderService.findById(id);
        if (gender != null) {
            return new ResponseEntity<>(gender, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

}
