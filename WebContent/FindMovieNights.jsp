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
	<form action="findmovienightnights" method="post">
		<h1>Search a movieNight by id</h1>
		<p>
			<label for="id">Id</label>
			<input id="id" name="id" value="${fn:escapeXml(param.MovieNightId)}">
		</p>
		<p>
			<input type="submit">
			<br/><br/><br/>
			<span id="successMessage"><b>${messages.success}</b></span>
		</p>
	</form>
	<br/>
	<div id="movienightNightCreate"><a href="movienightnightcreate">Create movienightNight</a></div>
	<br/>
	<h1>Matching movieNights</h1>
        <table border="1">
            <tr>
                <th>Title</th>
                <th>Date</th>
                <th>Delete movieNight</th>
                <th>Update movieNight</th>
            </tr>
            <c:forEach items="${movieNights}" var="movienight" >
                <tr>
                    <td><c:out value="${movienight.getMovie().getTitle()}" /></td>
                    <td><c:out value="${movienight.getReleaseYear()}"  /></td>
                    <td><a href="movienightdelete?movienightId=<c:out value="${movienight.getmovienightId()}"/>">Delete</a></td>
                    <td><a href="movienightupdate?movienightId=<c:out value="${movienight.getmovienightId()}"/>">Update</a></td>
                </tr>
            </c:forEach>
       </table>
</body>
</html>
