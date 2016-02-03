package br.com.rec.graph;

/**
 * 
 * @author Ricardo Cabral
 *
 * the edge class
 */
public class Route {

	private final Town source;
	private final Town destination;
	private final int distance;

	public Route(Town source, Town destination, int distance) {
		this.source = source;
		this.destination = destination;
		this.distance = distance;
	}

	public Town getDestination() {
		return destination;
	}

	public int getDistance() {
		return distance;
	}

	public Town getSource() {
		return source;
	}
	//public String toString(){
		//return "Route Object: + [" + source.getName() + ", " + destination.getName() + ", " + destination + "]";
	//}
}
