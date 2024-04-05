<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<head>
    <meta charset="UTF-8">
    <title>Register</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-QWTKZyjpPEjISv5WaRU9OFeRpok6YctnYmDr5pNlyT2bRjXh0JMhjY6hW+ALEwIH" crossorigin="anonymous">
    <link rel="stylesheet" type="text/css" href="<%= request.getContextPath() %>/css/styles.css"></head>
</head>
<body>
<div class="container container-width">
<h2>Update User</h2>

<!-- Display login error message if present -->
    <c:if test="${not empty message}">
        <div class="alert alert-danger" role="alert">${message}</div>
    </c:if>

<form action="/updateUser" method="post">
    <div class="mb-3">
                <label for="username" class="form-label">Username:</label>
                <input type="text" class="form-control" id="username" name="username" value="${user.username}" autocomplete="username" readonly>
            </div>
            <div class="mb-3">
                <label for="firstName" class="form-label">First Name:</label>
                <input type="text" class="form-control" id="firstName" name="firstName" value="${user.firstName}" autocomplete="given-name">
            </div>
            <div class="mb-3">
                <label for="lastName" class="form-label">Last Name:</label>
                <input type="text" class="form-control" id="lastName" name="lastName" value="${user.lastName}" autocomplete="family-name">
            </div>
            <div class="mb-3">
                <label for="email" class="form-label">Email:</label>
                <input type="email" class="form-control" id="email" name="email" value="${user.email}" autocomplete="email">
            </div>
            <div class="mb-3">
                <label for="phone" class="form-label">Phone:</label>
                <input type="tel" class="form-control" id="phone" name="phone" value="${user.phone}" autocomplete="tel">
            </div>
            <div class="mb-3">
                <label for="birthday" class="form-label">Birthday:</label>
                <input type="date" class="form-control" id="birthday" name="birthday" value="${user.birthday}" autocomplete="bday">
            </div>
    <div class="row">
        <div class="col">
            <button type="submit" class="btn btn-primary">Update</button>
        </div>
        <div class="col-3">
            <a href="<%=request.getContextPath()%>/auth/listing" class="w-100 btn btn-danger">Cancel</a>
        </div>
    </div>
</form>
    <c:if test="${not empty message}">
        <p>${message}</p>
    </c:if>
</div>
</body>
</html>
