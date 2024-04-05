<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Login Page</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-QWTKZyjpPEjISv5WaRU9OFeRpok6YctnYmDr5pNlyT2bRjXh0JMhjY6hW+ALEwIH" crossorigin="anonymous">
    <link rel="stylesheet" type="text/css" href="<%= request.getContextPath() %>/css/styles.css">
</head>
<body>

<div class="container container-width">
    <h2>Login</h2>

    <c:if test="${not empty registrationSuccess}">
        <div class="alert alert-success" role="alert">${registrationSuccess}</div>
    </c:if>

    <!-- Display login error message if present -->
    <c:if test="${not empty message}">
        <div class="alert alert-danger" role="alert">${message}</div>
    </c:if>

    <form action="<%=request.getContextPath()%>/login" method="post">
      <div class="mb-3">
        <label for="username" class="form-label">Username:</label>
        <input type="text" class="form-control" id="username" name="username">
      </div>
      <div class="mb-3">
        <label for="password" class="form-label">Password:</label>
        <input type="password" class="form-control" id="password" name="password">
      </div>
      <button type="submit" class="btn btn-primary">Login</button>
    </form>

    <div class="mt-3">
        Don't have an account? <a href="<%=request.getContextPath()%>/register" class="btn btn-secondary">Register here</a>
    </div>
</div>
</body>
</html>
