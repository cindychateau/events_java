<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ page isErrorPage="true" %>   
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Edición de Evento</title>
<link href="https://cdn.jsdelivr.net/npm/bootstrap@5.0.2/dist/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-EVSTQN3/azprG1Anm3QDgpJLIm9Nao0Yz1ztcQTwFspd3yD65VohhpuuCOmLASjC" crossorigin="anonymous">
</head>
<body>
	<div class="container">
		<h1>Editar Evento</h1>
		<form:form action="/events/update" method="post" modelAttribute="event">
			<input type="hidden" name="_method" value="put">
			<form:hidden path="id" value="${event.id}" />
			<div class="form-group">
				<form:label path="eventName">Nombre</form:label>
				<form:input path="eventName" class="form-control"/>
				<form:errors path="eventName" class="text-danger"/>
			</div>
			<div class="form-group">
				<form:label path="eventDate">Fecha</form:label>
				<form:input type="date" path="eventDate" class="form-control"/>
				<form:errors path="eventDate" class="text-danger"/>
			</div>
			<div class="form-group">
				<form:label path="location">Locación</form:label>
				<form:input path="location" class="form-control"/>
				<form:errors path="location" class="text-danger"/>
			</div>
			<div class="form-group">
				<form:label path="state">Estado</form:label>
				<form:select path="state" class="form-select">
					<c:forEach items="${states}" var="state">
						<c:choose>
							<c:when test="${state.equals(event.state)}">
								<option selected value="${state}">${state}</option>
							</c:when>
							<c:otherwise>
								<option value="${state}">${state}</option>
							</c:otherwise>
						</c:choose>
					</c:forEach>
				</form:select>
			</div>
			<form:hidden path="planner" value="${user.id}" />
			<input type="submit" value="Crear Evento" class="btn btn-success" />
		</form:form>
	</div>
	
</body>
</html>