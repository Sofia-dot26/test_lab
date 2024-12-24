<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<html>
<head>
    <title>Manage Responsible Persons</title>
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
<h1>Manage Responsible Persons for Project: ${project.name}</h1>
<a href="${pageContext.request.contextPath}/projects/responsible/add/${project.id}">Add Responsible Person</a>

<c:choose>
    <c:when test="${empty responsiblePersons}">
        <p>No responsible persons available.</p>
    </c:when>
    <c:otherwise>
        <table>
            <tr>
                <th>ID</th>
                <th>Name</th>
                <th>Role</th>
                <th>Actions</th>
            </tr>
            <c:forEach var="person" items="${responsiblePersons}">
                <tr>
                    <td>${person.id}</td>
                    <td>${person.name}</td>
                    <td>${person.role}</td>
                    <td>
                        <a href="${pageContext.request.contextPath}/projects/responsible/edit/${person.id}">Edit</a> |
                        <a href="${pageContext.request.contextPath}/projects/responsible/delete/${person.id}">Delete</a>
                    </td>
                </tr>
            </c:forEach>
        </table>
    </c:otherwise>
</c:choose>

</body>
</html>
