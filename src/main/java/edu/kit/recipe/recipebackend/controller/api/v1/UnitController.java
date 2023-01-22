package edu.kit.recipe.recipebackend.controller.api.v1;



import edu.kit.recipe.recipebackend.dto.UnitDTO;
import edu.kit.recipe.recipebackend.entities.units.Unit;
import edu.kit.recipe.recipebackend.repository.UnitRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/v1/units")
public class UnitController {
    private final UnitRepository unitRepository;

    public UnitController(UnitRepository unitRepository) {
        this.unitRepository = unitRepository;
    }




    @PostMapping
    public ResponseEntity<Unit> addUnit(@RequestBody UnitDTO unit) {
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
}
