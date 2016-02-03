package br.com.rec.app;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import br.com.rec.graph.Dijkstra;
import br.com.rec.graph.Graph;
import br.com.rec.graph.Town;
import br.com.rec.manager.RouteManager;
import br.com.rec.utils.ConstantsUtil;
import br.com.rec.utils.DataValidator;

/**
 * Handles requests for the application.
 * 
 * @author Ricardo Cabral
 */
@Controller
public class HomeController {

	@Autowired
	private DataValidator dataValidator;

	@Autowired
	private RouteManager routeManager;

	/**
	 * Standard method to initialize the application
	 * 
	 * @param Model
	 *            model
	 * @return home the current page initialized
	 */
	@RequestMapping(value = "/", method = RequestMethod.GET)
	public String requestModelForm(Model model) {
		model.addAttribute("requestModel", new RequestModel());
		return "home";
	}

	/**
	 * 
	 * @param RequestModel
	 *            requestModel
	 * @param Model
	 *            model
	 * @return the result based on the operation selected
	 */
	@RequestMapping(value = "/input", method = RequestMethod.POST)
	public String requestModelSubmit(@ModelAttribute RequestModel requestModel,
			Model model) {

		String[] routes = null;
		StringBuilder builder = null;
		try {
			if (requestModel.getGraphOption() == 0)
				throw new IllegalArgumentException("Please choose and option");
			routes = dataValidator.readTownVertexes(requestModel.getContent());
			routeManager.createRoutes(routes);

			builder = new StringBuilder();

			switch (requestModel.getGraphOption()) {
			case ConstantsUtil.FIND_DISTANCE_BETWEEN_TWO_ROUTES:
				builder = findDistanceBetweenTwoRoutes(builder, requestModel);
				break;
			case ConstantsUtil.FIND_DISTANCE_BETWEEN_THREE_ROUTES:
				builder = findDistanceBetweenThreeRoutes(builder, requestModel);
				break;
			case ConstantsUtil.FIND_DISTANCE_MORE_THAN_THREE_ROUTES:
				builder = findDistanceMoreThanThreeRoutes(builder, requestModel);
				break;
			case ConstantsUtil.FIND_NUMBER_OF_TRIPS_MAX_N_STOPS:
				builder = findNumberOfTripsMaximumNStops(builder, requestModel);
				break;
			case ConstantsUtil.FIND_NUMBER_OF_TRIPS_EXACT_STOPS:
				builder = findNumberOfTripsExactlyStops(builder, requestModel);
				break;
			case ConstantsUtil.FIND_SHORTEST_ROUTE:
				builder = findLengthShortestRoute(builder, requestModel);
				break;
			case ConstantsUtil.FIND_NUMBER_ROUTES_DISTANCE_LESS_THAN_N:
				builder = findNumberOfRoutesWithDistanceLessThanN(builder,
						requestModel);
				break;

			default:
				break;
			}

		} catch (IllegalArgumentException e) {
			requestModel.setError(e.getMessage());
			return "home";
		}

		requestModel.setResult(builder.toString());
		model.addAttribute("requestModel", requestModel);
		return "home";
	}

	/**
	 * 
	 * @param builder
	 * @param model
	 * @return the result of shortest path using dijkstra, should answer
	 *         question 2
	 */
	private StringBuilder findDistanceBetweenTwoRoutes(StringBuilder builder,
			RequestModel model) {

		String[] fromAndTo = dataValidator.readTownVertexes(model
				.getInputTwoTowns());
		Town start = routeManager.getTownByName(fromAndTo[0]);
		Town end = routeManager.getTownByName(fromAndTo[1]);

		Graph graph = new Graph(routeManager.getTowns(),
				routeManager.getRoutes());

		int result = Dijkstra.executeDijkstra(graph, start, end);

		builder.append("Result of Find the distance of the route: ")
				.append(start.getName()).append(" to ").append(end.getName())
				.append(" with result: ").append(result);
		builder.append("</br>");

		return builder;
	}

	/**
	 * 
	 * @param builder
	 * @param model
	 * @return return the route based in three routes, should answer questions 1
	 *         and 3
	 */
	private StringBuilder findDistanceBetweenThreeRoutes(StringBuilder builder,
			RequestModel model) {

		// String[] fromMiddleAndEnd =
		// model.getInputTwoTowns().split("\\s*,\\s*");
		String[] fromMiddleAndEnd = dataValidator.readTownVertexes(model
				.getInputTwoTowns());
		Town start = routeManager.getTownByName(fromMiddleAndEnd[0]);
		Town middle = routeManager.getTownByName(fromMiddleAndEnd[1]);
		Town end = routeManager.getTownByName(fromMiddleAndEnd[2]);

		List<Town> towns = new ArrayList<Town>();
		towns.add(start);
		towns.add(middle);
		towns.add(end);

		int result = routeManager.calculateDistanceForSpecificRoutes(towns);

		builder.append("Result of Find the distance of the route ")
				.append(start.getName()).append(", ").append(middle.getName())
				.append(", ").append(end.getName()).append(" with result : ")
				.append(result);
		builder.append("</br>");

		return builder;
	}

	/**
	 * 
	 * @param builder
	 * @param model
	 * @return return the distance among more then 3 towns, should answer
	 *         questions 4 and 5
	 */
	private StringBuilder findDistanceMoreThanThreeRoutes(
			StringBuilder builder, RequestModel model) {

		String[] townsString = dataValidator.readTownVertexes(model
				.getInputTwoTowns());
		List<Town> towns = new ArrayList<Town>();
		for (int i = 0; i < townsString.length; i++) {
			towns.add(routeManager.getTownByName(townsString[i]));
		}

		int result = routeManager.calculateDistanceForSpecificRoutes(towns);

		builder.append("Find the distance of the route : ")
				.append(towns.toString()).append(" with result: ")
				.append(result);
		builder.append("</br>");

		return builder;
	}

	/**
	 * 
	 * @param builder
	 * @param model
	 * @return return number of trips using a maximum number of stops, should
	 *         answer question 6
	 */
	private StringBuilder findNumberOfTripsMaximumNStops(StringBuilder builder,
			RequestModel model) {

		String[] fromAndTo = dataValidator.readTownVertexes(model
				.getInputTwoTowns());
		int numOfStops = model.getNumMaxOfStops();

		Town start = routeManager.getTownByName(fromAndTo[0]);
		Town end = routeManager.getTownByName(fromAndTo[1]);

		int result = routeManager.numTripsByStops(start, end, numOfStops, -1);
		builder.append("Find the number of trips starting at ")
				.append(start.getName()).append(" and ending at ")
				.append(end.getName()).append(" with a maximum of ")
				.append(numOfStops).append(" and result : ").append(result);
		builder.append("</br>");

		return builder;

	}

	/**
	 * 
	 * @param builder
	 * @param model
	 * @return number of trips with exactly number of stops, should answer
	 *         question 7
	 */
	private StringBuilder findNumberOfTripsExactlyStops(StringBuilder builder,
			RequestModel model) {
		String[] fromAndTo = dataValidator.readTownVertexes(model
				.getInputTwoTowns());
		int numOfStops = model.getNumMaxOfStops();

		Town start = routeManager.getTownByName(fromAndTo[0]);
		Town end = routeManager.getTownByName(fromAndTo[1]);

		int result = routeManager.numTripsByStops(start, end, numOfStops,
				numOfStops);
		builder.append("Find the number of trips starting at ")
				.append(start.getName()).append(" and ending at ")
				.append(end.getName()).append(" with exactly  ")
				.append(numOfStops).append(" and result : ").append(result);
		builder.append("</br>");

		return builder;
	}

	/**
	 * 
	 * @param builder
	 * @param model
	 * @return return the length of the shortest trip, should answer questions 8
	 *         and 9
	 */
	private StringBuilder findLengthShortestRoute(StringBuilder builder,
			RequestModel model) {

		String[] fromAndTo = dataValidator.readTownVertexes(model
				.getInputTwoTowns());

		Town start = routeManager.getTownByName(fromAndTo[0]);
		Town end = routeManager.getTownByName(fromAndTo[1]);
		int result = routeManager.findLengthOfShortestRoute(start, end);
		builder.append(
				"Find the length of the shortest route (in terms of distance to travel) from ")
				.append(start.getName()).append(" to ").append(end.getName())
				.append(" and result : ").append(result);
		builder.append("</br>");

		return builder;
	}

	/**
	 * 
	 * @param builder
	 * @param model
	 * @return return the number of routes with the distance less than N, should
	 *         answer question 10
	 */
	private StringBuilder findNumberOfRoutesWithDistanceLessThanN(
			StringBuilder builder, RequestModel model) {

		String[] fromAndTo = dataValidator.readTownVertexes(model
				.getInputTwoTowns());
		int numOfStops = model.getNumMaxOfStops();

		Town start = routeManager.getTownByName(fromAndTo[0]);
		Town end = routeManager.getTownByName(fromAndTo[1]);
		int result = routeManager.findNumberOfDifferentWitDistanceLessThanN(
				start, end, numOfStops);
		builder.append("Find the number of different routes from ")
				.append(start.getName()).append(" to ").append(end.getName())
				.append(" with a distance less than ").append(numOfStops)
				.append(" and result ").append(result);
		builder.append("</br>"); // return

		return builder;

	}
}
