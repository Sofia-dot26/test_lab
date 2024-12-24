<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<html>
<head>
    <title>Manage Assignees</title>
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
<h1>Manage Assignees for Task: ${task.name}</h1>
<a href="${pageContext.request.contextPath}/tasks/assignees/add/${task.id}">Add Assignee</a>

<c:if test="${empty assignees}">
    <p>No assignees available.</p>
</c:if>

<c:if test="${not empty assignees}">
    <table>
        <tr>
            <th>ID</th>
            <th>Name</th>
            <th>Role</th>
            <th>Actions</th>
        </tr>
        <c:forEach var="assignee" items="${assignees}">
            <tr>
                <td>${assignee.id}</td>
                <td>${assignee.name}</td>
                <td>${assignee.role}</td>
                <td>
                    <a href="${pageContext.request.contextPath}/tasks/assignees/edit/${assignee.id}">Edit</a> |
                    <a href="${pageContext.request.contextPath}/tasks/assignees/delete/${assignee.id}">Delete</a>
                </td>
            </tr>
        </c:forEach>
    </table>
</c:if>

</body>
</html>
