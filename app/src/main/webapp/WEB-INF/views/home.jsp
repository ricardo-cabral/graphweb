<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<style>
.error {
	color: #ff0000;
}
</style>

<form:form action="/app/input" modelAttribute="requestModel" method="POST">

   <table>
    <tr>
        <td class="error">${requestModel.error}</td>
    </tr>
	</table>
        <p>
            <label for="content">Please include your graph schema: e.g AB5, BC4, CD8, DC8, DE6, AD5, CE2, EB3, AE7 </label>
            <form:input path="content"/>
        </p>
        
       
        <table>
        <tr><td><form:errors path="graphOption" cssClass="error" /> </tr>
        	<tr>
        	<td> Please Select your option:</td>
        	</tr>
			<tr>
				
				<td><form:radiobutton path="graphOption" value="1" />Find the distance of two given routes e.g A,D</td>
			</tr>
			<tr>
			<td><form:radiobutton path="graphOption" value="2" />Find the distance of three routes e.g.: A,B,C</td>
				<td><form:errors path="graphOption" cssClass="error" /></td>
			</tr>
			<tr>
			<td><form:radiobutton path="graphOption" value="3" />Find the distance of a specific route with more than three routes e.g.: A,E,B,C,D</td>
				<td><form:errors path="graphOption" cssClass="error" /></td>
			</tr>
			<tr>
			<td><form:radiobutton path="graphOption" value="4" />Find the number of trips starting at N and ending at N with a maximum of N stops. e.G. C,C and 3</td>
				<td><form:errors path="graphOption" cssClass="error" /></td>
			</tr>
			<tr>
			<td><form:radiobutton path="graphOption" value="5" />Find the number of trips starting at N and ending at N with a exactly N stops. e.G. A,C and 3</td>
				<td><form:errors path="graphOption" cssClass="error" /></td>
			</tr>
			<tr>
			<td><form:radiobutton path="graphOption" value="6" />Find the length of the shortest route (in terms of distance to travel) e.G. A,C or B,B </td>
				<td><form:errors path="graphOption" cssClass="error" /></td>
			</tr>	
			<tr>
			<td><form:radiobutton path="graphOption" value="7" />Find the number of different routes from N to N with a distance of less than N. e.G. C,C and 30</td>
				<td><form:errors path="graphOption" cssClass="error" /></td>
			</tr>								
			<tr>
			<td> </td>
			</tr>
			
		</table>
		        <table>
        <tr><td><form:errors path="graphOption" cssClass="error" /> </tr>
			<tr>
			<td>
			<label for="inputTwoTowns">Enter the desired route separeted by comma e.g. A,B OR A,C or A,D </label>
            <form:input path="inputTwoTowns" /></td>
            </tr>
            <tr>
            <td>
            <label for="inputTwoTowns">Enter the number to represent the max of stops e.g. 3 </label>
            <form:input path="numMaxOfStops"  /></td>
			</tr>
			</table>
			 <table>
			<tr>
				<td colspan="3"><input type="submit" /></td>
			</tr>
		</table>
        
   <table>
    <tr>
        <td><b>${requestModel.result}</b></td>
    </tr>
	</table>  
    </form:form>

 
     