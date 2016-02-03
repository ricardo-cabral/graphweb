package ie.greefinch.test;

import static org.junit.Assert.assertTrue;

import org.junit.Test;

import br.com.rec.manager.RouteManager;

public class TestRouteManager {

	@Test
	public void testCreateRoutesPositive() {
		RouteManager manager = new RouteManager();
		
		String[] validValues = new String[]{"AB5", "BC4", "CD8", "DC8", "DE6", "AD5", "CE2", "EB3", "AE7"};
		
		manager.createRoutes(validValues);
		
		assertTrue(manager.getRoutes().size() == 9);
		assertTrue(manager.getTowns().size() == 5);
	}
	
	@Test
	public void testCreateRouteNull(){
		RouteManager manager = new RouteManager();
		
		manager.createRoutes(null);
	
		assertTrue(manager.getTowns().size() == 0);
	}
	
	@Test
	public void testCreateRouteEmpty(){
		RouteManager manager = new RouteManager();
		
		String[] validValues = new String[]{};
		manager.createRoutes(validValues);
		
		assertTrue(manager.getTowns().size() == 0);

	}


}
