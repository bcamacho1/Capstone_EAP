package edu.ndnu.capstone.domain;
import java.util.List;
import org.springframework.roo.addon.layers.service.RooService;

@RooService(domainTypes = { edu.ndnu.capstone.domain.Evacuation.class })
public interface EvacuationService {

	public abstract long countAllEvacuations();


	public abstract void deleteEvacuation(Evacuation evacuation);


	public abstract Evacuation findEvacuation(Integer id);


	public abstract List<Evacuation> findAllEvacuations();


	public abstract List<Evacuation> findEvacuationEntries(int firstResult, int maxResults);


	public abstract void saveEvacuation(Evacuation evacuation);


	public abstract Evacuation updateEvacuation(Evacuation evacuation);

}
