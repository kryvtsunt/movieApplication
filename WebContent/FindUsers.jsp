<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>

<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Find a User</title>
</head>
<body>
	<form action="findmovies" method="post">
		<h1>Search for a Movie by Title</h1>
		<p>
			<label for="title">FirstName</label>
			<input id="title" name="title" value="${fn:escapeXml(param.title)}">
		</p>
		<p>
			<input type="submit">
			<br/><br/><br/>
			<span id="successMessage"><b>${messages.success}</b></span>
		</p>
	</form>
	<br/>
	<div id="movieCreate"><a href="moviecreate">Create Movie</a></div>
	<br/>
	<h1>Matching Movies</h1>
        <table border="1">
            <tr>
                <th>Title</th>
                <th>ReleaseYear</th>
                <th>Runtime</th>
                <th>Delete Movie</th>
                <th>Update Update</th>
            </tr>
            <c:forEach items="${movies}" var="movie" >
                <tr>
                    <td><c:out value="${movie.getTitle()}" /></td>
                    <td><c:out value="${movie.getReleaseYear()}" pattern="yyyy-MM-dd" /></td>
                    <td><c:out value="${movie.getRuntime()}" /></td>
                    <td><fmt:formatDate value="${blogUser.getDob()}" pattern="yyyy-MM-dd"/></td>
                    <td><a href="userdelete?username=<c:out value="${movie.getMovieId()}"/>">Delete</a></td>
                    <td><a href="userupdate?username=<c:out value="${movie.getMovieId()}"/>">Update</a></td>
                </tr>
            </c:forEach>
       </table>
</body>
</html>
