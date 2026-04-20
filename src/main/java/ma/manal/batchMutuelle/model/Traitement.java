package ma.manal.batchMutuelle.model;
import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Traitement {
    private String codeBarre;
    private boolean existe;
    private String nomMedicament;
    private String typeMedicament;
    private double prixMedicament;
    private MedicamentReferentiel medicamentReferenctiel;
    private double montantRemboursement;
}
