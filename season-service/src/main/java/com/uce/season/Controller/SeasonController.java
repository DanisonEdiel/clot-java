package com.uce.season.Controller;

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

import com.uce.season.Model.Season;
import com.uce.season.Service.SeasonService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
@RequestMapping("/seasons")
@Tag(name = "Api Rest for seasons use Swagger 3 - Season")
public class SeasonController {

    @Autowired
    private SeasonService seasonService;

    @PostMapping
    @Operation(summary = "This endpoint use for created season")
    public ResponseEntity<?> saveSeason(@RequestBody Season season) {
        try {
            Season savedColor = seasonService.savedSeason(season);
            return new ResponseEntity<>(savedColor, HttpStatus.CREATED);
        } catch (DataIntegrityViolationException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping
    @Operation(summary = "This endpoint use for list all seasons")
    public ResponseEntity<?> getAll() {
        return ResponseEntity.ok(seasonService.getAll());
    }

    @GetMapping("/{id}")
    @Operation(summary = "This endpoint use for find by id season")
    private ResponseEntity<?> getBrand(@PathVariable Long id) {
        Season season = seasonService.findById(id);
        if (season != null) {
            return new ResponseEntity<>(season, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

}
