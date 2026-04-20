package ma.manal.batchMutuelle.repository;

import ma.manal.batchMutuelle.model.MedicamentReferentiel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MedRefRepository extends JpaRepository<MedicamentReferentiel, String> {
    boolean existsByCodeBarre(String codeBarre);

    MedicamentReferentiel findByCodeBarre(String codeBarre);
}
