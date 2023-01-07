<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix = "c" uri = "http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %> 
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Eventos</title>
<link href="https://cdn.jsdelivr.net/npm/bootstrap@5.0.2/dist/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-EVSTQN3/azprG1Anm3QDgpJLIm9Nao0Yz1ztcQTwFspd3yD65VohhpuuCOmLASjC" crossorigin="anonymous">
</head>
<body>

	<div class="container">
		<nav class="d-flex justify-content-between align-items-center">
			<h1>Bienvenid@ ${user_session.name}</h1>
			<a href="/logout" class="btn btn-danger">Cerrar Sesión</a>
		</nav>
		<div class="row">
			<h2>Eventos en mi estado</h2>
			<table class="table table-hover">
				<thead>
					<tr>
						<th>Evento</th>
						<th>Fecha</th>
						<th>Locación</th>
						<th>Estado</th>
						<th>Planner</th>
						<th>Acciones</th>
					</tr>
				</thead>
				<tbody>
					<c:forEach items="${eventos_miestado}" var="evento">
						<tr>
							<td>
								<a href="/events/${evento.id}">${evento.eventName}</a>
							</td>
							<td>${evento.eventDate}</td>
							<td>${evento.location}</td>
							<td>${evento.state}</td>
							<td>${evento.planner.name}</td>
							<td>
								<c:if test="${evento.planner.id == user.id}">
									<button class="btn btn-warning">
										<a href="/events/edit/${evento.id}">Editar</a>
									</button>
									<form action="/events/delete/${evento.id}" method="post">
										<input type="hidden" name="_method" value="DELETE" />
										<input type="submit" value="Eliminar" class="btn btn-danger"/>
									</form>
								</c:if>
								<!-- ACCION DE UNIRSE AL EVENTO -->
								
								<c:if test="${evento.planner.id != user.id}">
									<c:choose>
										<c:when test="${evento.attendees.contains(user)}">
											<span>Asistirá - 
												<a href="/events/remove/${evento.id}" class="btn btn-danger" >Ya no quiero asistir</a>
											</span>
										</c:when>
										<c:otherwise>
											<a href="/events/join/${evento.id}" class="btn btn-info">Asistir</a>
										</c:otherwise>
									</c:choose>
								</c:if>
							</td>
						</tr>
					</c:forEach>
				</tbody>
			</table>
		</div>
		<div class="row">
			<h2>Eventos fuera de mi estado</h2>
			<table class="table table-hover">
				<thead>
					<tr>
						<th>Evento</th>
						<th>Fecha</th>
						<th>Locación</th>
						<th>Estado</th>
						<th>Planner</th>
						<th>Acciones</th>
					</tr>
				</thead>
				<tbody>
					<c:forEach items="${eventos_otrosedos}" var="evento">
						<tr>
							<td>
								<a href="/events/${evento.id}">${evento.eventName}</a>
							</td>
							<td>${evento.eventDate}</td>
							<td>${evento.location}</td>
							<td>${evento.state}</td>
							<td>${evento.planner.name}</td>
							<td>
								<c:if test="${evento.planner.id == user.id}">
									<button class="btn btn-warning">
										<a href="/events/edit/${evento.id}">Editar</a>
									</button>
									<form action="/events/delete/${evento.id}" method="post">
										<input type="hidden" name="_method" value="DELETE" />
										<input type="submit" value="Eliminar" class="btn btn-danger"/>
									</form>
								</c:if>
								<!-- ACCION DE UNIRSE AL EVENTO -->
								
								<c:if test="${evento.planner.id != user.id}">
									<c:choose>
										<c:when test="${evento.attendees.contains(user)}">
											<span>Asistirá - 
												<a href="/events/remove/${evento.id}" class="btn btn-danger" >Ya no quiero asistir</a>
											</span>
										</c:when>
										<c:otherwise>
											<a href="/events/join/${evento.id}" class="btn btn-info">Asistir</a>
										</c:otherwise>
									</c:choose>
								</c:if>
							</td>
						</tr>
					</c:forEach>
				</tbody>
			</table>
		</div>
		<div class="row">
			<h2>Crear evento</h2>
			<form:form action="/events/create" method="post" modelAttribute="event">
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
							<option value="${state}">${state}</option>
						</c:forEach>
					</form:select>
				</div>
				<form:hidden path="planner" value="${user.id}" />
				<input type="submit" value="Crear Evento" class="btn btn-success" />
			</form:form>
		</div>
	</div>

</body>
</html>