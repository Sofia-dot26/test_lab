<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ include file="menu.jsp" %>
<html>
<head>
    <title>Tasks</title>
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
<h1>Tasks</h1>
<a href="${pageContext.request.contextPath}/tasks/add">Add Task</a>

<c:if test="${empty tasks}">
    <p>No tasks available.</p>
</c:if>

<c:if test="${not empty tasks}">
    <table>
        <tr>
            <th>ID</th>
            <th>Name</th>
            <th>Description</th>
            <th>Start Date</th>
            <th>End Date</th>
            <th>Priority</th>
            <th>Status</th>
            <th>Project</th>
            <th>Assignees</th>
            <th>Actions</th>
        </tr>
        <c:forEach var="task" items="${tasks}">
            <tr>
                <td>${task.id}</td>
                <td>${task.name}</td>
                <td>${task.description}</td>
                <td>${task.startDate}</td>
                <td>${task.endDate}</td>
                <td>${task.priority}</td>
                <td>${task.status}</td>
                <td>${task.project.name}</td>
                <td>
                    <c:forEach var="assignee" items="${task.assignees}">
                        ${assignee}<br>
                    </c:forEach>
                </td>
                <td>
                    <a href="${pageContext.request.contextPath}/tasks/edit/${task.id}">Edit</a> |
                    <a href="${pageContext.request.contextPath}/tasks/delete/${task.id}">Delete</a>
                </td>
            </tr>
        </c:forEach>
    </table>
</c:if>

</body>
</html>
