package com.example.tourist;

import com.example.tourist.dto.TouristRequest;
import com.example.tourist.entity.Tourist;
import com.example.tourist.repository.TouristRepository;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.Map;

@RestController
@RequestMapping("/api/tourists")
public class TouristController {
    private final TouristRepository repository;

    public TouristController(TouristRepository repository) { this.repository = repository; }

    // Helper endpoint to create a sample record for testing (no token required).
    @PostMapping
    public ResponseEntity<Tourist> createTourist(@Valid @RequestBody TouristRequest request) {
        Tourist tourist = new Tourist(request.getName(), request.getEmail(),
                request.getPhone(), request.getVisitedDestinations());
        return ResponseEntity.ok(repository.save(tourist));
    }

    @GetMapping("/{id}")
    public ResponseEntity<Tourist> getTourist(@PathVariable Long id) {
        return repository.findById(id).map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updateTourist(
            @PathVariable Long id,
            @RequestHeader("X-API-TOKEN") String token,
            @Valid @RequestBody TouristRequest request) {

        Tourist existing = repository.findById(id).orElse(null);

        if (existing == null) {
            return ResponseEntity.status(404)
                    .body(Map.of("error", "Tourist record not found with id " + id));
        }

        existing.setName(request.getName());
        existing.setEmail(request.getEmail());
        existing.setPhone(request.getPhone());
        existing.setVisitedDestinations(request.getVisitedDestinations());

        Tourist updated = repository.save(existing);

        return ResponseEntity.ok(updated);
    }
}
