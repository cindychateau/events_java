<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix = "c" uri = "http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Registrarme e Iniciar Sesión</title>
<link href="https://cdn.jsdelivr.net/npm/bootstrap@5.0.2/dist/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-EVSTQN3/azprG1Anm3QDgpJLIm9Nao0Yz1ztcQTwFspd3yD65VohhpuuCOmLASjC" crossorigin="anonymous">
</head>
<body>
	<div class="container">
		<div class="row">
			<div class="col">
				<h2>Regístra tu Usuario</h2>
				<!-- Mi FORM FORM ya me crea el objeto. BindingResult solo funciona con form:form -->
				<form:form action="/register" method="post" modelAttribute="nuevoUsuario">
					<div class="form-group">
						<form:label path="name">Nombre</form:label>
						<form:input path="name" class="form-control" />
						<form:errors path="name" class="text-danger" />
					</div>
					<div class="form-group">
						<form:label path="email">E-mail</form:label>
						<form:input path="email" class="form-control" />
						<form:errors path="email" class="text-danger" />
					</div>
					
					<div class="form-group">
						<form:label path="location">Locación</form:label>
						<form:input path="location" class="form-control" />
						<form:errors path="location" class="text-danger" />
					</div>
					<div class="form-group">
						<form:label path="state">Estado</form:label>
						<form:select path="state" class="form-select">
							<c:forEach items="${states}" var="state">
								<option value="${state}">${state}</option>
							</c:forEach>
						</form:select>
					</div>
					
					<div class="form-group">
						<form:label path="password">Password</form:label>
						<form:password path="password" class="form-control" />
						<form:errors path="password" class="text-danger" />
					</div>
					<div class="form-group">
						<form:label path="confirm">Confirmación</form:label>
						<form:password path="confirm" class="form-control" />
						<form:errors path="confirm" class="text-danger" />
					</div>
					<input type="submit" value="Registrarme" class="btn btn-primary" />
				</form:form>
			</div>
			<div class="col">
				<h2>Inicia Sesión</h2>
				<p class="text-danger">
					<!-- ERRORES -->
					${error_login}
				</p>
				<form action="/login" method="post">
					<div class="form-group">
						<label>E-mail</label>
						<input type="email" class="form-control" name="email" />
					</div>
					<div class="form-group">
						<label>Password</label>
						<input type="password" class="form-control" name="password" />
					</div>
					<input type="submit" value="Iniciar Sesión" class="btn btn-info" />
				</form>
			</div>
		</div>
	</div>
</body>
</html>