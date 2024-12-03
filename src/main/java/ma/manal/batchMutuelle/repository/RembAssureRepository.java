package ma.manal.batchMutuelle.repository;

import ma.manal.batchMutuelle.model.RembAssure;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RembAssureRepository  extends JpaRepository<RembAssure, Long> {
}
