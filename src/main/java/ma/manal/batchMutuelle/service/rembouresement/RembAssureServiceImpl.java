package ma.manal.batchMutuelle.service.rembouresement;

import lombok.RequiredArgsConstructor;
import ma.manal.batchMutuelle.model.RembAssure;
import ma.manal.batchMutuelle.repository.RembAssureRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class RembAssureServiceImpl implements RembAssureService {

    private final RembAssureRepository repo;
    @Override
    public RembAssure addRembAssure(RembAssure rembAssure) {
        return repo.save(rembAssure);
    }
    @Override
    public List<RembAssure> getAllRembAssures() {
        // Récupérer toutes les données de RembAssure depuis le repository
        return repo.findAll();
    }
}

