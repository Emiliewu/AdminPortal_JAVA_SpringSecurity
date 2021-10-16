<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8" isErrorPage="true"%>
<!DOCTYPE html>
<html>
<head>
<link href="https://cdn.jsdelivr.net/npm/bootstrap@5.0.0-beta2/dist/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-BmbxuPwQa2lc/FVzBcNJ7UAyJxM6wuqIj61tLrc4wSX0szH/Ev+nYRRuWlolflfl" crossorigin="anonymous">
<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.0.0-beta2/dist/js/bootstrap.bundle.min.js" integrity="sha384-b5kHyXgcpbZJO/tY9Ul7kGkf1S0CWuKcCD38l8YkeH8z8QjE0GmW1gYU5S9FOnJ0" crossorigin="anonymous"></script>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<meta charset="UTF-8" />
<title>home</title>
</head>
<body>
	<div class="container mx-auto" style="width:800px; ">
	<div class="d-flex justify-content-between p-3 m-2 col-8" style="color:#195f9b">
		<h3 class="p-3">Welcome <c:out value="${ currentUser.firstname }" /></h3>
		<c:set var="adminrole" scope="session" value="false"/>
		<c:forEach items="${ currentUser.roles }" var="r">
		<c:if test="${ r.name.contains('ROLE_ADMIN') }">
		<c:set var="adminrole" scope="session" value="true"/>
		</c:if>
		</c:forEach>
		<c:if test="${ adminrole.equals('true') }"><a href="/admin">Admin Portal</a></c:if>
		<form id="logoutForm" method="POST" action="/logout">
        <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
        <input type="submit" value="Logout!" />
    </form>
	</div>
	
	<div class="container-sm my-3 p-3 col-10">
		  <div class="row">
		    <div class="col">
		      <p>First Name: </p>
		    </div>
		    <div class="col">
		      <p><c:out value="${ currentUser.firstname }"/> </p>
		    </div>
		  </div>
		  <div class="row">
		    <div class="col">
		      <p>Last Name: </p>
		    </div>
		    <div class="col">
		      <p><c:out value="${ currentUser.lastname }"/> </p>
		    </div>
		  </div>
		   <div class="row">
		    <div class="col">
		      <p>Email: </p>
		    </div>
		    <div class="col">
		      <p><c:out value="${ currentUser.email }"/> </p>
		    </div>
		  </div>
		  <div class="row">
		    <div class="col">
		      <p>Sign up Date: </p>
		    </div>
		    <div class="col">
		      <p><fmt:formatDate type="date" value="${ currentUser.createdat}"/> </p>
		    </div>		  
		  </div>
		  	  <div class="row">
		    <div class="col">
		      <p>Last Login Date: </p>
		    </div>
		    <div class="col">
		      <p><fmt:formatDate type="date" value="${ currentUser.updatedat }"/> </p>
		    </div>		  
		  </div>
		</div>	
	</div>
</body>
</html>