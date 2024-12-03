package ma.manal.batchMutuelle.service.prescription;

import ma.manal.batchMutuelle.model.MedicamentReferentiel;

import java.util.List;
import java.util.NoSuchElementException;

public interface MedRefService {
    MedicamentReferentiel addTraitement(MedicamentReferentiel traitement) throws IllegalStateException;
    MedicamentReferentiel getTraitement(String codeBarre) throws NoSuchElementException;
    MedicamentReferentiel updateTraitement(MedicamentReferentiel ref,String codeBarre) throws NoSuchElementException;
    void addMultipleMedicament(List<MedicamentReferentiel> medicaments) throws IllegalStateException;
    List<MedicamentReferentiel> getAllMedicaments();
    void deleteTraitement(String id);
}
