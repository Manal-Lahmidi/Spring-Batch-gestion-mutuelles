package ma.manal.batchMutuelle.processor;

import ma.manal.batchMutuelle.model.DossierMutuelle;
import ma.manal.batchMutuelle.model.Traitement;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.stereotype.Component;

@Component
public class TraitementRembouresementProcessor implements ItemProcessor  <DossierMutuelle, DossierMutuelle> {

    @Override
    public DossierMutuelle process(DossierMutuelle item) throws Exception {
        for(Traitement traitement:item.getTraitements()){
            if(traitement.isExiste()){
                double prixDeReference=traitement.getMedicamentReferenctiel().getPrixMedicament();
                double TauxRembourecement=traitement.getMedicamentReferenctiel().getTauxRemboursement();
                traitement.setMontantRemboursement(Math.round(prixDeReference * TauxRembourecement * 100.0) / 100.0);

                System.out.println("Traitement: " + traitement.getNomMedicament() + " - Remboursement: " + traitement.getMontantRemboursement());

            } else {
            // Si le médicament n'existe pas, on ne fait rien
            System.out.println("Traitement " + traitement.getNomMedicament() + " n'existe pas dans la base.");
        }
        }
        return item;
    }
}
