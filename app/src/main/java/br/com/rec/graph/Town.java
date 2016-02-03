package br.com.rec.graph;

import java.util.HashMap;

/**
 * 
 * @author Ricardo Cabral
 * 
 * this class contains the name of the town, destination and also all the routes and neighbors
 *
 */
public class Town implements Comparable<Town>{
	
	private String name;
	private HashMap<Town, Integer> routes = new HashMap<Town, Integer>();
	private boolean visited;
	
	public Town(String name){
		this.name = name;
	}
	
	public String getName(){
		return name;
	}
	
	public HashMap<Town, Integer> getRoutes() {
		return routes;
	}
	
	public void addRoute(Town town, Integer distance){
		this.routes.put(town, distance);
	}


	@Override
	public int compareTo(Town o) {
		return this.getName().compareTo(o.getName());
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((name == null) ? 0 : name.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Town other = (Town) obj;
		if (name == null) {
			if (other.name != null)
				return false;
		} else if (!name.equals(other.name))
			return false;
		return true;
	}
	

	public boolean isVisited() {
		return visited;
	}

	public void setVisited(boolean visited) {
		this.visited = visited;
	}

}
