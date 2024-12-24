<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<html>
<head>
    <title>Projects</title>
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
<h1>Projects</h1>
<a href="${pageContext.request.contextPath}/projects/add">Add Project</a>

<c:choose>
    <c:when test="${empty projects}">
        <p>No projects available.</p>
    </c:when>
    <c:otherwise>
        <table>
            <tr>
                <th>ID</th>
                <th>Name</th>
                <th>Description</th>
                <th>Start Date</th>
                <th>End Date</th>
                <th>Priority</th>
                <th>Status</th>
                <th>Actions</th>
            </tr>
            <c:forEach var="project" items="${projects}">
                <tr>
                    <td>${project.id}</td>
                    <td>${project.name}</td>
                    <td>${project.description}</td>
                    <td>${project.startDate}</td>
                    <td>${project.endDate}</td>
                    <td>${project.priority}</td>
                    <td>${project.status}</td>
                    <td>
                        <a href="${pageContext.request.contextPath}/projects/edit/${project.id}">Edit</a> |
                        <a href="${pageContext.request.contextPath}/projects/delete/${project.id}">Delete</a>
                    </td>
                </tr>
            </c:forEach>
        </table>
    </c:otherwise>
</c:choose>

</body>
</html>
