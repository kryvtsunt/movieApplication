<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>

<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Find a movieNight</title>
</head>
<body>
	<form action="findmovienights" method="post">
		<h1>Search a movieNight by date</h1>
		<p>
			<label for="date">Date</label>
			<input id="date" name="date" value="${fn:escapeXml(param.date)}">
		</p>
		<p>
			<input type="submit">
			<br/><br/><br/>
			<span id="successMessage"><b>${messages.success}</b></span>
		</p>
	</form>
	<br/>
	<div id="movienightcreate"><a href="movienightcreate">Create a movieNight</a></div>
	<br/>
	<h1>Matching movieNights</h1>
        <table border="1">
            <tr>
                <th>MovieNightId</th>
                <th>Movie Title</th>
                <th>Date</th>
                <th>Delete movieNight</th>
            </tr>
            <c:forEach items="${movieNights}" var="movienight" >
                <tr>
					<td><c:out value="${movienight.getMovieNightId()}" /></td>
                    <td><c:out value="${movienight.getMovie().getTitle()}" /></td>
                    <td><c:out value="${movienight.getDate()}"  /></td>
                    <td><a href="movienightdelete?movienightId=<c:out value="${movienight.getMovieNightId()}"/>">Delete</a></td>
                </tr>
            </c:forEach>
       </table>
</body>
</html>
