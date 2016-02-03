package ie.greefinch.test;

import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;

import br.com.rec.graph.Dijkstra;
import br.com.rec.graph.Graph;
import br.com.rec.graph.Town;
import br.com.rec.manager.RouteManager;

public class TestRoutes {

	private RouteManager manager = null;
	private String[] validValues = null;
	private Graph graph = null;

	@Before
	public void setUp() {
		manager = new RouteManager();
		validValues = new String[] { "AB5", "BC4", "CD8", "DC8", "DE6", "AD5",
				"CE2", "EB3", "AE7" };
		manager.createRoutes(validValues);
		graph = new Graph(manager.getTowns(), manager.getRoutes());

	}

	@Test
	public void testRouteABC() {

		Town start = manager.getTownByName("A");
		Town end = manager.getTownByName("C");

		assertTrue(Dijkstra.executeDijkstra(graph, start, end) == 9);

	}

	@Test
	public void testRouteAD() {
		Town start = manager.getTownByName("A");
		Town end = manager.getTownByName("D");

		assertTrue(Dijkstra.executeDijkstra(graph, start, end) == 5);

	}

	@Test
	public void testRouteADC() {
		Town start = manager.getTownByName("A");
		Town middle = manager.getTownByName("D");
		Town end = manager.getTownByName("C");

		List<Town> towns = new ArrayList<Town>();
		towns.add(start);
		towns.add(middle);
		towns.add(end);

		assertTrue(manager.calculateDistanceForSpecificRoutes(towns) == 13);

	}

	@Test
	public void testRouteAEBCD() {
		Town a = manager.getTownByName("A");
		Town e = manager.getTownByName("E");
		Town b = manager.getTownByName("B");
		Town c = manager.getTownByName("C");
		Town d = manager.getTownByName("D");
		List<Town> towns = new ArrayList<Town>();
		towns.add(a);
		towns.add(e);
		towns.add(b);
		towns.add(c);
		towns.add(d);

		assertTrue(manager.calculateDistanceForSpecificRoutes(towns) == 22);
	}

	@Test(expected = IllegalArgumentException.class)
	public void testRouteAED() {
		Town start = manager.getTownByName("A");
		Town middle = manager.getTownByName("E");
		Town end = manager.getTownByName("D");

		List<Town> towns = new ArrayList<Town>();
		towns.add(start);
		towns.add(middle);
		towns.add(end);

		manager.calculateDistanceForSpecificRoutes(towns);

	}

	@Test
	public void testFindTripsCtoCwith3Stops() {
		Town start = manager.getTownByName("C");
		Town end = manager.getTownByName("C");
		int numOfStops = 3;

		assertTrue(manager.numTripsByStops(start, end, numOfStops, -1) == 2);

	}

	@Test
	public void testFindTripsAtoCwith4Stops() {
		Town start = manager.getTownByName("A");
		Town end = manager.getTownByName("C");
		int numOfStops = 4;

		assertTrue(manager.numTripsByStops(start, end, numOfStops, numOfStops) == 3);

	}

	@Test
	public void testShortestRouteFromAToC() {
		Town start = manager.getTownByName("A");
		Town end = manager.getTownByName("C");
		assertTrue(manager.findLengthOfShortestRoute(start, end) == 9);

	}

	@Test
	public void testShortestRouteFromBToB() {
		Town start = manager.getTownByName("B");
		Town end = manager.getTownByName("B");

		assertTrue(manager.findLengthOfShortestRoute(start, end) == 9);

	}

	@Test
	public void testNumberOfDifferentRoutes() {
		Town start = manager.getTownByName("C");
		Town end = manager.getTownByName("C");
		int numOfRoutes = 30;
		assertTrue(manager.findNumberOfDifferentWitDistanceLessThanN(start, end, numOfRoutes) == 7);

	}
}
