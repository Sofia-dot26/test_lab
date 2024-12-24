<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<html>
<head>
    <title>Manage Roles</title>
    <style>
        body {
            font-family: Arial, sans-serif;
            margin: 20px;
        }
        h1 {
            color: #333;
        }
        table {
            width: 100%;
            border-collapse: collapse;
            margin-top: 20px;
        }
        th, td {
            border: 1px solid #ddd;
            padding: 8px;
            text-align: left;
        }
        th {
            background-color: #f2f2f2;
        }
        a {
            text-decoration: none;
            color: #007BFF;
        }
        a:hover {
            text-decoration: underline;
        }
    </style>
</head>
<body>
<h1>Manage Roles</h1>
<a href="${pageContext.request.contextPath}/roles/add">Add Role</a>

<c:if test="${empty roles}">
    <p>No roles available.</p>
</c:if>

<c:if test="${not empty roles}">
    <table>
        <tr>
            <th>ID</th>
            <th>Role Name</th>
            <th>Actions</th>
        </tr>
        <c:forEach var="role" items="${roles}">
            <tr>
                <td>${role.id}</td>
                <td>${role.name}</td>
                <td>
                    <a href="${pageContext.request.contextPath}/roles/edit/${role.id}">Edit</a> |
                    <a href="${pageContext.request.contextPath}/roles/delete/${role.id}">Delete</a>
                </td>
            </tr>
        </c:forEach>
    </table>
</c:if>

</body>
</html>
