<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%> 
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Mostrar Evento</title>
<link href="https://cdn.jsdelivr.net/npm/bootstrap@5.0.2/dist/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-EVSTQN3/azprG1Anm3QDgpJLIm9Nao0Yz1ztcQTwFspd3yD65VohhpuuCOmLASjC" crossorigin="anonymous">
</head>
<body>
	<div class="container">
		<div class="row">
			<div class="col">
				<h1>${evento.eventName}</h1>
				<p>
					<b>Planner:</b> ${evento.planner.name}
				</p>
				<p>
					<b>Fecha:</b> ${evento.eventDate}
				</p>
				<p>
					<b>Locación:</b> ${evento.location}
				</p>
				<p>
					<b>Estado:</b> ${evento.state}
				</p>
				<p>
					<b>Cantidad de personas:</b> ${evento.attendees.size()}
				</p>
				<table class="table table-hover">
					<thead>
						<tr>
							<th>Nombre</th>
							<th>Locación</th>
						</tr>
					</thead>
					<tbody>
						<c:forEach items="${evento.attendees}" var="usuario">
							<tr>
								<td>${usuario.name}</td>
								<td>${usuario.location}</td>
							</tr>
						</c:forEach>
					</tbody>
				</table>
			</div>
			<div class="col">
				<h2>Muro de Mensajes</h2>
				<div>
					<c:forEach items="${evento.messages}" var="mensaje">
						<p>
							${mensaje.author.name} dice: ${mensaje.content}
						</p>
					</c:forEach>
				</div>
				<form:form action="/events/message" method="post" modelAttribute="message">
					<div class="form-group">
						<form:label path="content">Agregar Comentario:</form:label>
						<form:textarea path="content" class="form-control" />
						<form:errors path="content" class="text-danger" />
						<form:hidden path="author" value="${user_session.id}" />
						<form:hidden path="event" value="${evento.id}"/>
						<input type="submit" class="btn btn-primary" value="Enviar" />
					</div>
				</form:form>
			</div>
		</div>
	</div>
</body>
</html>