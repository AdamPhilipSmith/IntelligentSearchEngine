<%@ page import="java.util.List"%>
<%@ page import="Search.Search" %>
<%@ page import="java.util.Arrays" %>
<%@ page import="java.util.ArrayList" %>
<%
List<Film> films = (List<Film>) request.getAttribute("films");


//Outputs text formatted to be more readable
for(int i=0; i<films.size(); i++) {
	Film film = films.get(i);
	response.getWriter().println("Title: " + film.getTitle() + "\n<br>" + "Year: " + film.getYear() + "\n<br>" + "Director: " + film.getDirector() + "\n<br>" + "Stars: " + film.getStars() + "\n<br>" + "Review: " + film.getReview());
	response.getWriter().println("<br>------------------<br>");
}


%>