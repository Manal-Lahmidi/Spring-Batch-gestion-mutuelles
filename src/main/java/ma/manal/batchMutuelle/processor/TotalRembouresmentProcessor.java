package ma.manal.batchMutuelle.processor;

import ma.manal.batchMutuelle.model.DossierMutuelle;
import ma.manal.batchMutuelle.model.RembAssure;
import ma.manal.batchMutuelle.model.Traitement;
import ma.manal.batchMutuelle.repository.RembAssureRepository;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class TotalRembouresmentProcessor implements ItemProcessor<DossierMutuelle, RembAssure> {
    @Autowired
    private RembAssureRepository rembAssureRepository;
    @Override
    public RembAssure process(DossierMutuelle item) throws Exception {
        double totalRembours = item.getRobouresementConsultation();

        System.out.println("Consultation Remboursement: " + totalRembours);

        for(Traitement traitement: item.getTraitements()){
            if(traitement.isExiste()){
                double montantRemboursement = traitement.getMontantRemboursement();
                totalRembours += montantRemboursement;
                System.out.println("Traitement: " + traitement.getNomMedicament() + " - Montant Remboursement: " + montantRemboursement);
                System.out.println("totalRembours: "+totalRembours);
            } else {
                System.out.println("Traitement " + traitement.getNomMedicament() + " n'existe pas dans la base.");
            }
        }
        RembAssure rembAssure = new RembAssure();
        rembAssure.setNomAssure(item.getNomAssure());
        rembAssure.setImmatriculation(item.getImmatriculation());
        rembAssure.setNumeroAffiliation(item.getNumeroAffiliation());
        rembAssure.setTotalRembouresement(totalRembours);

        //rembAssureRepository.save(rembAssure);

        return rembAssure;

    }
}
