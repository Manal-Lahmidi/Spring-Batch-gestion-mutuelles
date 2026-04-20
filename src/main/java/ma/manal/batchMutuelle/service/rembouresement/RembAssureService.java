package ma.manal.batchMutuelle.service.rembouresement;

import ma.manal.batchMutuelle.model.RembAssure;

import java.util.List;

public interface RembAssureService {
    RembAssure addRembAssure(RembAssure rembAssure);
    public List<RembAssure> getAllRembAssures();

}
