<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>

<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Create a Movie</title>
</head>
<body>
	<h1>Create Movie</h1>
	<form action="moviecreate" method="post">
		<p>
			<label for="title">Title</label>
			<input id="title" name="title" value="">
		</p>
		<p>
			<label for="releaseYear">Release Year</label>
			<input id="releaseYear" name="releaseYear" value="">
		</p>
		<p>
			<label for="runtime">Runtime</label>
			<input id="runtime" name="runtime" value="">
		</p>
		<p>
			<input type="submit">
		</p>
	</form>
	<br/><br/>
	<p>
		<span id="successMessage"><b>${messages.success}</b></span>
	</p>
</body>
</html>