<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ include file="menu.jsp" %>
<html>
<head>
    <title>Task Details</title>
    <style>
        body {
            font-family: Arial, sans-serif;
            margin: 20px;
        }
        h1 {
            color: #333;
        }
        .task-details {
            margin-top: 20px;
        }
        .task-details p {
            margin: 5px 0;
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
<h1>Task Details</h1>
<div class="task-details">
    <p><strong>Task Name:</strong> ${task.name}</p>
    <p><strong>Description:</strong> ${task.description}</p>
    <p><strong>Start Date:</strong> ${task.startDate}</p>
    <p><strong>End Date:</strong> ${task.endDate}</p>
    <p><strong>Priority:</strong> ${task.priority}</p>
    <p><strong>Status:</strong> ${task.status}</p>
    <p><strong>Project:</strong> ${task.project.name}</p>
    <p><strong>Assignees:</strong>
        <c:forEach var="assignee" items="${task.assignees}">
            ${assignee}<br>
        </c:forEach>
    </p>
    <a href="${pageContext.request.contextPath}/tasks/edit/${task.id}">Edit Task</a> |
    <a href="${pageContext.request.contextPath}/tasks/delete/${task.id}">Delete Task</a>
</div>
</body>
</html>
