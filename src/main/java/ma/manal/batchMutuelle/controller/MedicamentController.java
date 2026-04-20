package ma.manal.batchMutuelle.controller;

import lombok.RequiredArgsConstructor;
import ma.manal.batchMutuelle.model.MedicamentReferentiel;
import ma.manal.batchMutuelle.service.prescription.MedRefService;
import ma.manal.batchMutuelle.response.ApiResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/batch/dossiermutuelle/medicament")
public class MedicamentController {
    private final MedRefService medRefService;


    @GetMapping("/all")
    public ResponseEntity<ApiResponse> getAll(){
        try {
            List<MedicamentReferentiel> refs= medRefService.getAllMedicaments();
            return ResponseEntity.ok(new ApiResponse("successful request", refs));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new ApiResponse("upload failed", null));
        }

    }











    @PostMapping("/add")
    public ResponseEntity<ApiResponse> addMedicament(@RequestBody MedicamentReferentiel ref){
        try {
            MedicamentReferentiel medRef = medRefService.addTraitement(ref);
            return ResponseEntity.ok(new ApiResponse("uploaded successfully", medRef));
        } catch (IllegalStateException e) {
            throw new RuntimeException(e);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new ApiResponse("upload failed", null));

        }
    }

    @PostMapping("/addmultiple")
    public ResponseEntity<ApiResponse> addMultipleMedicament(@RequestBody List<MedicamentReferentiel> refs){
        try {
            medRefService.addMultipleMedicament(refs);
            return ResponseEntity.ok(new ApiResponse("uploaded successfully", null));
        } catch (IllegalStateException e) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body(new ApiResponse("upload failed", null));
        }catch(Exception e){
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new ApiResponse("upload failed", null));
        }
    }
}
