package com.YC.RidePilot.controllers;

import com.YC.RidePilot.entity.dto.ChauffeurDto;
import com.YC.RidePilot.entity.dto.ChauffeurAnalyticsDto;
import com.YC.RidePilot.exception.Chauffeur.ValidationException;
import com.YC.RidePilot.services.InterfacesServices.ChauffeurInterfaces;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.*;
import io.swagger.v3.oas.annotations.responses.*;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@Slf4j
@RequestMapping("/api/chauffeurs")
@RequiredArgsConstructor
public class ChauffeurController {

    private final ChauffeurInterfaces chauffeurInterfaces;
    @Operation(summary = "Créer une nouvelle  chauffeur")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = " chauffeur créée avec succès", content = @Content(schema = @Schema(implementation = ChauffeurDto.class))),
            @ApiResponse(responseCode = "400", description = "Erreur de validation")
    })
    @PostMapping
    public ResponseEntity<ChauffeurDto> create(@Valid @RequestBody ChauffeurDto chauffeurDto) {
        log.info("Création d'un nouveau chauffeur");
        if (chauffeurDto == null) {
            throw new ValidationException("Les données du chauffeur ne peuvent pas être nulles");
        }
        return ResponseEntity.ok(chauffeurInterfaces.create(chauffeurDto));
    }
    @Operation(summary = "Mettre à jour une  chauffeur existante")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = " chauffeur mise à jour avec succès", content = @Content(schema = @Schema(implementation = ChauffeurDto.class))),
            @ApiResponse(responseCode = "404", description = " chauffeur non trouvée"),
            @ApiResponse(responseCode = "400", description = "Erreur de validation")
    })
    @PutMapping("/{id}")
    public ResponseEntity<ChauffeurDto> update(@PathVariable Long id, @Valid @RequestBody ChauffeurDto chauffeurDto) {
        log.info("Mise à jour du chauffeur avec l'ID: {}", id);
        if (id == null) {
            throw new ValidationException("L'ID du chauffeur ne peut pas être nul");
        }
        if (chauffeurDto == null) {
            throw new ValidationException("Les données du chauffeur ne peuvent pas être nulles");
        }
        chauffeurDto.setId(id);
        return ResponseEntity.ok(chauffeurInterfaces.update(chauffeurDto));
    }
    @Operation(summary = "Supprimer une  chauffeur")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = " chauffeur supprimée avec succès"),
            @ApiResponse(responseCode = "404", description = " chauffeur non trouvée")
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<Map<String, String>> delete(@PathVariable Long id) {
        log.info("Suppression du chauffeur avec l'ID: {}", id);
        if (id == null) {
            throw new ValidationException("L'ID du chauffeur ne peut pas être nul");
        }
        chauffeurInterfaces.delete(id);
        return ResponseEntity.ok(Map.of("message", "L'ID du chauffeur " + id + " Supprimée avec succès"));
    }
    @Operation(summary = "Obtenir toutes les  chauffeurs")
    @ApiResponse(responseCode = "200", description = "Liste de toutes les  chauffeurs", content = @Content(schema = @Schema(implementation = ChauffeurDto.class)))
    @GetMapping
    public ResponseEntity<List<ChauffeurDto>> getAll() {
        log.info("Récupération de tous les chauffeurs");
        return ResponseEntity.ok(chauffeurInterfaces.getAll());
    }
    @Operation(summary = "Obtenir une chauffeur par ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "chauffeur trouvée", content = @Content(schema = @Schema(implementation = ChauffeurDto.class))),
            @ApiResponse(responseCode = "404", description = "chauffeur non trouvée")
    })
    @GetMapping("/{id}")
    public ResponseEntity<ChauffeurDto> getById(@PathVariable Long id) {
        log.info("Récupération du chauffeur avec l'ID: {}", id);
        return chauffeurInterfaces.getById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }
    @Operation(summary = "Rechercher une chauffeurs par nom")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Liste des chauffeurs correspondant au Nom ", content = @Content(schema = @Schema(implementation = ChauffeurDto.class))),
            @ApiResponse(responseCode = "404", description = "nom non trouvée")
    })
    @GetMapping("/search/{nom}")
    public ResponseEntity<List<ChauffeurDto>> searchByNom(@PathVariable  String nom) {
        log.info("Recherche des chauffeurs par nom: {}", nom);
        return ResponseEntity.ok(chauffeurInterfaces.findByNom(nom));
    }

    @GetMapping("/count")
    public ResponseEntity<Long> countChauffeurs() {
        log.info("Comptage du nombre total de chauffeurs");
        return ResponseEntity.ok(chauffeurInterfaces.countChauffeurs());
    }

    @Operation(summary = "Obtenir analytics chaffueurs")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Liste des chauffeurs analytics  ", content = @Content(schema = @Schema(implementation = ChauffeurDto.class))),
            @ApiResponse(responseCode = "404", description = "analytics non trouvée")
    })
    @GetMapping("/analytics")
    public ResponseEntity<ChauffeurAnalyticsDto> getAnalytics() {
        log.info("Récupération des analytics des chauffeurs");
        return ResponseEntity.ok(chauffeurInterfaces.getAnalytics());
    }
}
