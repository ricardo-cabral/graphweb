package br.com.rec.graph;

import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;

/**
 * 
 * @author Ricardo Cabral
 *
 *         Dijkstra algorithm is used to calculated the shortest path between
 *         nodes in a Graph.
 *
 *         Basically this algorithm uses breadth first search with greedy
 *         approach to bring the shortest distance.
 *         
 *         It starts from the source node and visit all adjacent nodes having a minimum weight
 *
 */
public class Dijkstra {


	/**
	 * 
	 * @param graph
	 * @param start
	 * @param end
	 * @return shortest route between two towns
	 */
	public static int executeDijkstra(Graph graph, Town start, Town end) {
		Set<Town> unSettledTowns = new HashSet<Town>();
		Set<Town> settledTowns = new HashSet<Town>();

		//get the list of towns
		Set<Town> nodes = graph.getTowns();
		Map<Town, Integer> townDistance = new TreeMap<Town, Integer>();

		//set each town with the max value because we don't have the value yet
		for (Town town : nodes) {
			townDistance.put(town, Integer.MAX_VALUE);
		}

		//include the first town in unsettled set and as it is the first route the distance will be 0
		unSettledTowns.add(start);
		townDistance.put(start, 0);
		
		while (!unSettledTowns.isEmpty()) {


			//find inside the unsettled towns the shortest distance until now
			Town closestTown = null;
			int minDistance = Integer.MAX_VALUE;
			for (Town n : unSettledTowns) {
				Integer dist = townDistance.get(n);
				if (dist < minDistance) {
					closestTown = n;
					minDistance = dist;
				}
			}

			//as the town is now the nearest node and we know the distance, we can check it as settled
			unSettledTowns.remove(closestTown);
			settledTowns.add(closestTown);

			
			for(Town route : closestTown.getRoutes().keySet()){
				if (!settledTowns.contains(route)) {
					// find the ones for which we find a better (shorter) path
					// to the source than the one we had previously calculated
					// for it...
					
					//here the code compare the closest distance with all routes to define which one is the shortest
					int newDistance = townDistance.get(closestTown)
							+ closestTown.getRoutes().get(route);
					Integer dist = townDistance.get(route);
					if (newDistance < dist) {
						// save the new shortest distance for this neighbor
						// and move it to the list of unvisited nodes.
						//as it found a lower distance and now the distance for this neighbor we add to the distance for that town because now we know
						townDistance.put(route, newDistance);
						//here is the trick to go inside the routes using the while
						unSettledTowns.add(route);
					}
				}
			}
			
			// if the destination was visited so we have the shortest route and it is not necessary to continue.
			if (settledTowns.contains(end)) {
				break;
			}
		}
		//returned the shortest path calculated between nodes in a Graph
		return townDistance.get(end);

	}

}
