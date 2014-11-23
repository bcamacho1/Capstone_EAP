package edu.ndnu.capstone.domain;

import java.util.List;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class EvacuationServiceImpl implements EvacuationService {

    public long countAllEvacuations() {
        return Evacuation.countEvacuations();
    }

    public void deleteEvacuation(Evacuation evacuation) {
        evacuation.remove();
    }

    public Evacuation findEvacuation(Integer id) {
        return Evacuation.findEvacuation(id);
    }

    public List<Evacuation> findAllEvacuations() {
        return Evacuation.findAllEvacuations();
    }

    public List<Evacuation> findEvacuationEntries(int firstResult, int maxResults) {
        return Evacuation.findEvacuationEntries(firstResult, maxResults);
    }

    public void saveEvacuation(Evacuation evacuation) {
        evacuation.persist();
    }

    public Evacuation updateEvacuation(Evacuation evacuation) {
        return evacuation.merge();
    }
}
