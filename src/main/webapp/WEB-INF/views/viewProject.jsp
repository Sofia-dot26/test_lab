<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<html>
<head>
    <title>Project Details</title>
    <style>
        body {
            font-family: Arial, sans-serif;
            margin: 20px;
        }
        h1 {
            color: #333;
        }
        .project-details {
            margin-top: 20px;
        }
        .project-details p {
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
<h1>Project Details</h1>
<div class="project-details">
    <p><strong>Project Name:</strong> ${project.name}</p>
    <p><strong>Description:</strong> ${project.description}</p>
    <p><strong>Start Date:</strong> ${project.startDate}</p>
    <p><strong>End Date:</strong> ${project.endDate}</p>
    <p><strong>Priority:</strong> ${project.priority}</p>
    <p><strong>Status:</strong> ${project.status}</p>
    <p><strong>Responsible Persons:</strong>
        <c:forEach var="person" items="${project.responsiblePersons}">
            ${person}<br>
        </c:forEach>
    </p>
    <p><strong>Participants:</strong>
        <c:forEach var="participant" items="${project.participants}">
            ${participant}<br>
        </c:forEach>
    </p>
    <a href="${pageContext.request.contextPath}/projects/edit/${project.id}">Edit Project</a> |
    <a href="${pageContext.request.contextPath}/projects/delete/${project.id}">Delete Project</a>
</div>
</body>
</html>
