package br.com.rec.manager;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;
import java.util.TreeSet;

import org.springframework.stereotype.Component;

import br.com.rec.graph.Route;
import br.com.rec.graph.Town;

@Component
public class RouteManager {

	private List<Route> listOfRoutes = new ArrayList<Route>();
	private Set<Town> listOfTowns = new TreeSet<Town>();
	Map<Town, Town> nodeMap = new TreeMap<Town, Town>();

	/**
	 * 
	 * @param routes setUp the routes based on String given
	 */
	public void createRoutes(String[] routes) {

		if (routes == null) {
			return;
		}
		for (String string : routes) {

			String source = string.substring(0, 1);
			String destination = string.substring(1, 2);
			int distance = Integer.parseInt(string.substring(2, 3));

			Town townSource = getTownByName(source);
			Town townDestination = getTownByName(destination);

			Route route = new Route(townSource, townDestination, distance);

			//townSource.addNeighbor(townDestination, distance);
			townSource.addRoute(townDestination, distance);
			listOfRoutes.add(route);
		}


	}

	/**
	 * 
	 * @param town
	 * @return add and return the current town to a list of towns
	 */
	public Town addTownToList(Town town) {
		if (!listOfTowns.contains(town)) {
			listOfTowns.add(town);
		}

		return town;
	}

	/**
	 * 
	 * @return list of towns
	 */
	public Set<Town> getTowns() {
		return this.listOfTowns;
	}

	/**
	 * 
	 * @return list of routes
	 */
	public List<Route> getRoutes() {
		return this.listOfRoutes;
	}


	/**
	 * 
	 * @param name
	 * @return Town based on name
	 */
	public Town getTownByName(String name) {

		Town tmp = new Town(name);
		Town node = nodeMap.get(tmp);

		if (node == null) {
			node = new Town(name);
			nodeMap.put(node, node);
		}
		this.addTownToList(node);
		return node;
	}


	/**
	 * 
	 * @param towns
	 * @return this method simply go through towns and check the neighbors to see if the route exists or not, throwing an exception in case of route does not exists
	 */
	public int calculateDistanceForSpecificRoutes(List<Town> towns) {
		if (towns == null || towns.size() < 2) {
			return 0;
		}

		int distance = 0;

		for (int i = 0; i < towns.size() - 1; i++) {
			Town currentTown = towns.get(i);
			Town nextTown = towns.get(i + 1);

			Set<Town> tmp = currentTown.getRoutes().keySet();
			if (tmp.contains(nextTown)) {
				distance = distance + currentTown.getRoutes().get(nextTown);
			} else {
				throw new IllegalArgumentException("NO SUCH ROUTE");
			}
		}

		return distance;

	}

	/**
	 * 
	 * @param start
	 * @param end
	 * @param maxStep
	 * @param finalStep
	 * @return
	 */
	public int numTripsByStops(final Town start, final Town end,
			final int maxStep, final int finalStep) {

		if (maxStep < finalStep) {
			throw new IllegalArgumentException(
					"Number max of stops should be greater than finalStop!");
		}
		int startCount = start.equals(end) ? -1 : 0;
		return numTripsByStopsImpl(start, end, 0, maxStep, startCount,
				finalStep);
	}

	/**
	 * 
	 * @param start
	 * @param end
	 * @param step
	 * @param maxStep
	 * @param count
	 * @param finalStep
	 * @return
	 */
	private int numTripsByStopsImpl(Town start, final Town end, int step,
			final int maxStep, int count, int finalStep) {
		
		if (start.equals(end)) {
			if (finalStep > 0) {
				if (finalStep == step) {
					count++;
				}
			} else {
				count++;
			}
		}
		for (Town route : start.getRoutes().keySet()) {
			if (step < maxStep) {
				count = numTripsByStopsImpl(route, end, step + 1, maxStep,
						count, finalStep);
			}
		}

		return count;
	}

	/**
	 * 
	 * @param start
	 * @param end
	 * @return A wrapper to call the recursive method to find the shortest route
	 *         and also to initialize the values needed
	 */
	public int findLengthOfShortestRoute(Town start, Town end) {

		return findLengthOfShortestRouteImpl(start, end, 0, 0);

	}

	/**
	 * 
	 * @param start
	 * @param end
	 * @param weight
	 * @param shortestRoute
	 * @return this returns the shortest distance route based on depth-first search 
	 *         the idea is go
	 *         from start route and pass through all possible routes keeping
	 *         shortest distance each time that the method is called recursively
	 *         and return the minimal value when all the graph is iterated.
	 */
	private int findLengthOfShortestRouteImpl(Town start, Town end,
			int distance, int shortestRoute) {

		start.setVisited(true); // Mark start node as visited
		for (Town route : start.getRoutes().keySet()) {
			if (route.equals(end) || !route.isVisited()) {
				distance += start.getRoutes().get(route);
			}

			if (route.equals(end)) {
				if (shortestRoute == 0 || distance < shortestRoute) {
					shortestRoute = distance;
				}
				start.setVisited(false);
				return shortestRoute;
			} else if (!route.isVisited()) {
				shortestRoute = findLengthOfShortestRouteImpl(route, end,
						distance, shortestRoute);
				distance -= start.getRoutes().get(route);
			}
		}
		start.setVisited(false);
		return shortestRoute;

	}

	/**
	 * 
	 * @param start
	 * @param end
	 * @param maxDistance
	 * @return A wrapper to call the recursive method to find the number of
	 *         Different routes to initialize the values needed
	 */
	public int findNumberOfDifferentWitDistanceLessThanN(final Town start,
			final Town end, final int maxDistance) {

		// set a flag to avoid count one when the start town is the same than
		// the destination
		final int startCount = start.equals(end) ? -1 : 0;

		return findNumberOfDifferentWitDistance(start, end, 0, startCount,
				maxDistance);
	}

	/**
	 * 
	 * @param curRoute
	 * @param toRoute
	 * @param curDistance
	 * @param count
	 * @param maxDistance
	 * @return this method is based on depth-first search recursively, the idea
	 *         of this is go through all existent paths calculating he distances
	 *         until finds a parameter to stop (maxDistance) we could also use
	 *         breadth-first search that uses a queue instead of recursion to
	 *         avoid a stack overflow for example
	 */
	public int findNumberOfDifferentWitDistance(Town start, final Town end,
			int currentDistance, int count, int maxDistance) {
		if (start.equals(end)) {
			count++;
		}

		for (Town route : start.getRoutes().keySet()) {
			int d = start.getRoutes().get(route);
			if (currentDistance + d < maxDistance) {
				count = findNumberOfDifferentWitDistance(route, end,
						currentDistance + d, count, maxDistance);
			}
		}
		return count;
	}

}
