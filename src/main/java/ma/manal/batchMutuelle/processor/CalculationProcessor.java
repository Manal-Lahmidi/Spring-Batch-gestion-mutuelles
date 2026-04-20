package ma.manal.batchMutuelle.processor;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import ma.manal.batchMutuelle.model.DossierMutuelle;
import ma.manal.batchMutuelle.model.RembAssure;
import ma.manal.batchMutuelle.service.rembouresement.RembAssureService;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@AllArgsConstructor
@NoArgsConstructor
@Component
public class CalculationProcessor implements ItemProcessor<DossierMutuelle, RembAssure> {
    @Autowired
    private ConsultationProcessor consultationProcessor;
    @Autowired
    private TraitementMappingProcessor traitementMappingProcessor;
    @Autowired
    private TraitementRembouresementProcessor traitementRembouresementProcessor;
    @Autowired
    private TotalRembouresmentProcessor totalRembouresmentProcessor;
    @Autowired
    private RembAssureService rembAssureService;

    @Override
    public RembAssure process(DossierMutuelle item) throws Exception {
        DossierMutuelle dossierMutuelle = consultationProcessor.process(item);
        dossierMutuelle = traitementMappingProcessor.process(dossierMutuelle);
        dossierMutuelle = traitementRembouresementProcessor.process(dossierMutuelle);
        RembAssure rembAssure= totalRembouresmentProcessor.process(dossierMutuelle);

        //rembAssureService.saveRembAssure(rembAssure);

        return rembAssure;
    }
}
