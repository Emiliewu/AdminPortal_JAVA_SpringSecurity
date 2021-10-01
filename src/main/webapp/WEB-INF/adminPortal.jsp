<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8" isErrorPage="true"%>
<!DOCTYPE html>
<html>
<head>
<link href="https://cdn.jsdelivr.net/npm/bootstrap@5.0.0-beta2/dist/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-BmbxuPwQa2lc/FVzBcNJ7UAyJxM6wuqIj61tLrc4wSX0szH/Ev+nYRRuWlolflfl" crossorigin="anonymous">
<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.0.0-beta2/dist/js/bootstrap.bundle.min.js" integrity="sha384-b5kHyXgcpbZJO/tY9Ul7kGkf1S0CWuKcCD38l8YkeH8z8QjE0GmW1gYU5S9FOnJ0" crossorigin="anonymous"></script>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<meta charset="UTF-8" />
<title>Administration Portal</title>
</head>
<body>
	<div class="container mx-auto" style="width:800px; ">
	<div class="d-flex justify-content-between" style="color:#195f9b">
		<h3 class="p-3">Welcome <c:out value="${ currentUser.firstname }" /></h3>
		<form id="logoutForm" method="POST" action="/logout">
        <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
        <input type="submit" value="Logout!" />
    </form>
	</div>
	
	<div class="container-sm my-3 p-3 col-8" style="border:1px solid black">
			<table class="table table-striped table-hover">
			<thead>
		    <tr>
		      <th scope="col">Name</th>
		      <th scope="col">Email</th>
		      <th scope="col">Action</th>
		    </tr>
			</thead>
			<tbody>
				<c:forEach items="${ allusers }" var="u">
				<tr>
					<td scope="row"><c:out value="${ u.firstname }"/> <c:out value="${ u.lastname }"/></td>
					<td scope="row"><c:out value="${ u.email }"/></td>
					<c:set var="adminrole" scope="session" value="false"/>
					<c:forEach items="${ u.roles }" var="r">
					<c:if test="${ r.name.equals('ROLE_ADMIN') }">
					<c:set var="adminrole" scope="session" value="true"/>
					</c:if>
					</c:forEach>
					<c:choose>				
						<c:when test="${ adminrole.equals('true') }">						
						<td scope="row">admin</td>
						</c:when>
						<c:otherwise>						
						<td scope="row"><a href="/admin/${ u.id }/delete">Delete</a> | <a href="/admin/${ u.id }/new"> Make admin</a></td>
						</c:otherwise>
					</c:choose>				
				</tr>
				</c:forEach>
  			</tbody>
			</table>
		</div>
	</div>
</body>
</html>