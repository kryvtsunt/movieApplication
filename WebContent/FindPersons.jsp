<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>

<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Find a Person</title>
</head>
<body>
	<form action="findpersons" method="post">
		<h1>Search for a Person by First Name</h1>
		<p>
			<label for="firstname">First Name</label>
			<input id="firstname" name="firstname" value="${fn:escapeXml(param.firstname)}">
		</p>
		<p>
			<input type="submit">
			<br/><br/><br/>
			<span id="successMessage"><b>${messages.success}</b></span>
		</p>
	</form>
	<br/>
	<div id="personcreate"><a href="personcreate">Create Person</a></div>
	<br/>
	<h1>Matching People</h1>
        <table border="1">
            <tr>
                <th>First Name</th>
                <th>Last Name</th>
                <th>Date of Birth</th>
                <th>Liked By</th>
                <th>Movies</th>
                <th>Delete Person</th>
                <th>Update Person</th>
            </tr>
            <c:forEach items="${persons}" var="person" >
                <tr>
                    <td><c:out value="${person.getFirstName()}" /></td>
                    <td><c:out value="${person.getLastName()}" /></td>
                    <td><fmt:formatDate value="${person.getDateOfBirth()}" pattern="MM-dd-yyyy"/></td>
                    <td><a href="personlikes?personId=<c:out value="${person.getPersonId()}"/>">Liked By</a></td>
                    <td><a href="personmovies?personId=<c:out value="${person.getPersonId()}"/>">Movies</a></td>
                    <td><a href="persondelete?personId=<c:out value="${person.getPersonId()}"/>">Delete</a></td>
                    <td><a href="personupdate?personId=<c:out value="${person.getPersonId()}"/>">Update</a></td>
                </tr>
            </c:forEach>
       </table>
</body>
</html>
