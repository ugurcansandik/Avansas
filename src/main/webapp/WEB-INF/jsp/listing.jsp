<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>User Listing</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css" rel="stylesheet"
          integrity="sha384-QWTKZyjpPEjISv5WaRU9OFeRpok6YctnYmDr5pNlyT2bRjXh0JMhjY6hW+ALEwIH"
          crossorigin="anonymous">
    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/js/bootstrap.bundle.min.js" integrity="sha384-YvpcrYf0tY3lHB60NNkmXc5s9fDVZLESaAA55NDzOxhy9GkcIdslK1eN7N6jIeHz" crossorigin="anonymous"></script>
    <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.7.1/jquery.min.js"></script>
    <script src="<%= request.getContextPath() %>/js/main.js"></script>
</head>
<body>
<%
    String role = response.getHeader("role");
%>
<div class="container mt-4">
    <div class="row">
        <div class="col">
            <h2>User Listing</h2>
        </div>
        <div class="col justify-content-end d-flex">
            <a href="<%=request.getContextPath()%>/addUser" class="btn btn-primary">Add User</a>
        </div>
        <div class="col-1 justify-content-end d-flex">
            <form action="logout" method="post">
                <button type="submit" class="btn btn-danger">Logout</button>
            </form>
        </div>
    </div>
    <div class="table-responsive">
        <table class="table table-striped">
            <thead>
            <tr>
                <th>Username</th>
                <th>First Name</th>
                <th>Last Name</th>
                <th>Email</th>
                <th>Phone</th>
                <th>Birthday</th>
                <% if ("ADMIN".equals(role)) { %>
                    <th>Actions</th>
                <% } %>
            </tr>
            </thead>
            <tbody>
            <c:forEach items="${users}" var="user" varStatus="loop">
                <tr id="userRow_${user.username}">
                    <td>${user.username}</td>
                    <td>${user.firstName}</td>
                    <td>${user.lastName}</td>
                    <td>${user.email}</td>
                    <td>${user.phone}</td>
                    <td>${user.birthday}</td>
                    <% if ("ADMIN".equals(role)) { %>
                        <td>
                            <a href="<%=request.getContextPath()%>/update/${user.username}" class="btn btn-primary">Update</a>
                            <button type="button" class="btn btn-danger deleteBtn" data-bs-toggle="modal" data-bs-target="#deleteModel" data-username="${user.username}">Delete</button>
                        </td>
                    <% } %>
                </tr>
            </c:forEach>
            </tbody>
        </table>

        <c:if test="${not empty registrationSuccess}">
            <div class="alert alert-success" role="alert">${registrationSuccess}</div>
        </c:if>

    </div>
    <!-- Delete Modal -->
    <div class="modal fade" id="deleteModel" tabindex="-1" aria-labelledby="deleteModelLabel" aria-hidden="true">
      <div class="modal-dialog">
        <div class="modal-content">
          <div class="modal-header">
            <h1 class="modal-title fs-5" id="deleteModelLabel">Delete User</h1>
            <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
          </div>
          <div class="modal-body">
            Are you sure you want to delete this user?
          </div>
          <div class="modal-footer">
            <button type="button" class="btn btn-primary" id="deleteButton" data-bs-dismiss="modal">Delete</button>
            <button type="button" class="btn btn-danger" data-bs-dismiss="modal">Close</button>
          </div>
        </div>
      </div>
    </div>
</div>

<script>
    document.querySelectorAll('.deleteBtn').forEach(button => {
        button.addEventListener('click', function () {
             var username = this.dataset.username;
             deleteUser(username);
         });
    });
</script>
</body>
</html>
