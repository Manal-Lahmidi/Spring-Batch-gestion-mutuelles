package ma.manal.batchMutuelle.processor;

import ma.manal.batchMutuelle.model.DossierMutuelle;
import ma.manal.batchMutuelle.model.MedicamentReferentiel;
import ma.manal.batchMutuelle.model.Traitement;
import ma.manal.batchMutuelle.service.prescription.MedRefService;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class TraitementMappingProcessor implements ItemProcessor<DossierMutuelle,DossierMutuelle> {
    @Autowired
    private MedRefService medRefService;
    @Override
    public DossierMutuelle process(DossierMutuelle item) throws Exception {

        for(Traitement traitement : item.getTraitements()){
            MedicamentReferentiel ref=medRefService.getTraitement(traitement.getCodeBarre());
            traitement.setMedicamentReferenctiel(ref);
            traitement.setExiste(ref != null);
        }
        return item;
    }
}
