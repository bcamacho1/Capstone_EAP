package test;

import java.util.List;

import org.junit.Assert;
import org.junit.Test;

import edu.ndnu.capstone.domain.Evacuation;
import edu.ndnu.capstone.domain.EvacuationServiceImpl;

public class EvacuationServiceTest {

	@Test
	public void testCountEvacuations() {
		EvacuationServiceImpl esi = new EvacuationServiceImpl();
		Long count = esi.countAllEvacuations();
		Assert.assertTrue(count == esi.countAllEvacuations());
	}
	
	@Test
	public void testDeleteEvacuation() {
		EvacuationServiceImpl esi = new EvacuationServiceImpl();
		Evacuation evacuation = Evacuation.findEvacuation(1);
		esi.deleteEvacuation(evacuation);
		
		List <Evacuation> evacs = esi.findAllEvacuations();
		Assert.assertTrue(!evacs.contains(evacuation));
	}
	
	@Test
	public void testFindEvacuation() {
		EvacuationServiceImpl esi = new EvacuationServiceImpl();
		Evacuation evac = esi.findEvacuation(1);
		
		Assert.assertEquals(evac, esi.findEvacuation(1));
	}
	
	@Test
	public void testSaveEvacuation(Evacuation evac) {
		EvacuationServiceImpl esi = new EvacuationServiceImpl();
		esi.saveEvacuation(evac);
		
		List <Evacuation> evacs = esi.findAllEvacuations();
		Assert.assertTrue(evacs.contains(evac));
	}
	
	@Test
	public void testUpdateEvacuation(Evacuation evac) {
		EvacuationServiceImpl esi = new EvacuationServiceImpl();
		esi.updateEvacuation(evac);
		
		List <Evacuation> evacs = esi.findAllEvacuations();
		Assert.assertTrue(evacs.contains(evac));
	}
}
