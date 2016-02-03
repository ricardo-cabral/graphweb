package br.com.rec.graph;

import java.util.List;
import java.util.Set;

/**
 * 
 * @author Ricardo Cabral
 * 
 * Class Graph that keep all towns and routes(nodes and edges)
 *
 */
public class Graph {

	private final Set<Town> towns;
	private final List<Route> routes;
	
	public Graph(Set<Town> towns, List<Route> routes){
		this.towns = towns;
		this.routes = routes;
	}

	public Set<Town> getTowns() {
		return towns;
	}

	public List<Route> getRoutes() {
		return routes;
	}
}
