package ma.manal.batchMutuelle.processor;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import ma.manal.batchMutuelle.model.DossierMutuelle;
import ma.manal.batchMutuelle.model.RembAssure;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.beans.factory.annotation.Autowired;

@NoArgsConstructor
@AllArgsConstructor
public class CompositeItemProcessor implements ItemProcessor<DossierMutuelle, RembAssure> {
    @Autowired
    private   ValidationProcessor validationProcessor;
    @Autowired
    private   CalculationProcessor calculationProcessor;


    @Override
    public RembAssure process(DossierMutuelle item) throws Exception {
        DossierMutuelle dossierMutuelle = validationProcessor.process(item);
        RembAssure rembAssure = calculationProcessor.process(dossierMutuelle);

        System.out.println("RembAssure details: Nom Assure: " + rembAssure.getNomAssure() + ", Total Remboursement: " + rembAssure.getTotalRembouresement());

        return rembAssure;
    }

}
