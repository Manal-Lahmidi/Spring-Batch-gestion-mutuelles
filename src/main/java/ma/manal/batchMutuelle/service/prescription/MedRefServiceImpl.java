package ma.manal.batchMutuelle.service.prescription;

import lombok.RequiredArgsConstructor;
import ma.manal.batchMutuelle.model.MedicamentReferentiel;

import ma.manal.batchMutuelle.repository.MedRefRepository;
import org.hibernate.ResourceClosedException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class MedRefServiceImpl implements MedRefService{
    private final MedRefRepository medRefRepository;

    @Override
    public MedicamentReferentiel addTraitement(MedicamentReferentiel ref) throws IllegalStateException {
        if(medRefRepository.existsByCodeBarre(ref.getCodeBarre())){
            throw new  IllegalStateException("ce medicament est déja exist");
        }
        return medRefRepository.save(ref);
    }

    @Override
    public MedicamentReferentiel getTraitement(String  codeBarre) throws NoSuchElementException {
        return Optional.ofNullable(medRefRepository.findByCodeBarre(codeBarre)).orElse(null);
    }

    @Override
    public MedicamentReferentiel updateTraitement(MedicamentReferentiel traitement, String  id) throws NoSuchElementException {
        MedicamentReferentiel traitement1= getTraitement(id);
        traitement1.setCodeBarre(traitement.getCodeBarre());
        traitement1.setNomMedicament(traitement.getNomMedicament());
        return medRefRepository.save(traitement1);
    }

    @Override
    public void addMultipleMedicament(List<MedicamentReferentiel> medicaments) throws IllegalStateException {
        for(MedicamentReferentiel med: medicaments){
            addTraitement(med);
        }
    }

    @Override
    public List<MedicamentReferentiel> getAllMedicaments() {
        return medRefRepository.findAll();
    }

    @Override
    public void deleteTraitement(String id) {
        medRefRepository.findById(id).ifPresentOrElse(medRefRepository::delete, () ->
            new ResourceClosedException("le medicament avec id"+id+"n'as pas été trouvé")
        );
    }
}
