<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<html>
<head>
    <title>Manage Statuses</title>
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
<h1>Manage Statuses</h1>
<a href="${pageContext.request.contextPath}/statuses/add">Add Status</a>

<c:if test="${empty statuses}">
    <p>No statuses available.</p>
</c:if>

<c:if test="${not empty statuses}">
    <table>
        <tr>
            <th>ID</th>
            <th>Status Name</th>
            <th>Actions</th>
        </tr>
        <c:forEach var="status" items="${statuses}">
            <tr>
                <td>${status.id}</td>
                <td>${status.name}</td>
                <td>
                    <a href="${pageContext.request.contextPath}/statuses/edit/${status.id}">Edit</a> |
                    <a href="${pageContext.request.contextPath}/statuses/delete/${status.id}">Delete</a>
                </td>
            </tr>
        </c:forEach>
    </table>
</c:if>

</body>
</html>
