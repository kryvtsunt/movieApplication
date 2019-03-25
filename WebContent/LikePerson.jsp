<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>

<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Person Likes</title>
</head>
<body>
	<form action="personlikes" method="post">
		<h1>Find User Likes by Person</h1>
		<p>
			<label for="personId">Person ID</label>
			<input id="personId" name="personId" value="${fn:escapeXml(param.personId)}">
		</p>
		<p>
			<input type="submit">
			<br/><br/><br/>
			<span id="successMessage"><b>${messages.success}</b></span>
		</p>
	</form>
<!-- 	<br/>
	<div id="userCreate"><a href="usercreate">Create User</a></div>
	<br/> -->
	<h1>Matching Users</h1>
        <table border="1">
            <tr>
                <th>Username</th>
                <th>Delete Like</th>
            </tr>
            <c:forEach items="${likes}" var="like" >
                <tr>
                    <td><c:out value="${like.getUser().getUserName()}" /></td>
                    <td><a href="deletelikeperson?likePersonId=<c:out value="${like.getLikePersonId()}"/>">Delete</a></td>
                </tr>
            </c:forEach>
       </table>
</body>
</html>