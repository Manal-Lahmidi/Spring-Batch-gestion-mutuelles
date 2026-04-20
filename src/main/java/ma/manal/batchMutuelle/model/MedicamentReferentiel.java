package ma.manal.batchMutuelle.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.*;
import org.hibernate.validator.constraints.Range;

@Getter
@Setter
@Entity
@NoArgsConstructor
@AllArgsConstructor
public class MedicamentReferentiel {
    @Id
    @Column(unique = true, nullable = false)
    private String codeBarre;
    private String nomMedicament;
    private double prixMedicament;
    @Range(min = 0, max = 1)
    private double tauxRemboursement;
}
