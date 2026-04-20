package ma.manal.batchMutuelle.controller;

import lombok.RequiredArgsConstructor;
import ma.manal.batchMutuelle.model.RembAssure;
import ma.manal.batchMutuelle.service.rembouresement.RembAssureService;
import ma.manal.batchMutuelle.response.ApiResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
@RestController
@RequiredArgsConstructor(onConstructor_ = {@Autowired})
@RequestMapping("/api/batch/dossiermutuelle/remboursementsAssurés")
public class RembAssureController {
    private final RembAssureService rembAssureService;



    @GetMapping("/all")
    public ResponseEntity<ApiResponse> getAll() {
        try {
            // Récupérer tous les objets RembAssure depuis le service
            List<RembAssure> rembAssures = rembAssureService.getAllRembAssures();
            return ResponseEntity.ok(new ApiResponse("Data fetched successfully", rembAssures));
        } catch (Exception e) {
            // En cas d'erreur, renvoyer une réponse d'erreur
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new ApiResponse("Failed to fetch data", null));
        }
    }
}
