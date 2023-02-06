package edu.kit.recipe.recipebackend.controller.api.v1.units;


import edu.kit.recipe.recipebackend.dto.UnitDTO;
import edu.kit.recipe.recipebackend.entities.units.Unit;
import edu.kit.recipe.recipebackend.repository.UnitRepository;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1/units")
public class UnitController {
    private final UnitRepository unitRepository;

    public UnitController(UnitRepository unitRepository) {
        this.unitRepository = unitRepository;
    }




    @PostMapping
    public ResponseEntity<Unit> addUnit(@RequestBody @Valid UnitDTO unit) {
        if (unit.name() == null || unit.name().isEmpty()) {
            return ResponseEntity.badRequest().build();
        }
        Unit newUnit = new Unit();
        newUnit.setName(unit.name());
        Optional<Unit> found =  unitRepository.findByName(
                unit.name()
            );
        if (found.isPresent()) {
            return ResponseEntity.status(HttpStatus.CONFLICT).build();
        }
        return ResponseEntity.ok(unitRepository.save(newUnit));
    }

    @GetMapping
    public ResponseEntity<List<Unit>> getUnits() {
        return ResponseEntity.ok(unitRepository.findAll());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteUnit(@PathVariable String id) {
        Optional<Unit> unit = unitRepository.findById(UUID.fromString(id));
        if (unit.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
        unitRepository.delete(unit.get());
        return ResponseEntity.ok("Deleted");
    }
}
