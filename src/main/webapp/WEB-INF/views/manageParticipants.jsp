<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<html>
<head>
    <title>Manage Participants</title>
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
<h1>Manage Participants for Project: ${project.name}</h1>
<a href="${pageContext.request.contextPath}/projects/participants/add/${project.id}">Add Participant</a>

<c:choose>
    <c:when test="${empty participants}">
        <p>No participants available.</p>
    </c:when>
    <c:otherwise>
        <table>
            <tr>
                <th>ID</th>
                <th>Name</th>
                <th>Role</th>
                <th>Actions</th>
            </tr>
            <c:forEach var="participant" items="${participants}">
                <tr>
                    <td>${participant.id}</td>
                    <td>${participant.name}</td>
                    <td>${participant.role}</td>
                    <td>
                        <a href="${pageContext.request.contextPath}/projects/participants/edit/${participant.id}">Edit</a> |
                        <a href="${pageContext.request.contextPath}/projects/participants/delete/${participant.id}">Delete</a>
                    </td>
                </tr>
            </c:forEach>
        </table>
    </c:otherwise>
</c:choose>

</body>
</html>
