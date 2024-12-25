<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ include file="menu.jsp" %>
<html>
<head>
    <title>Проекты</title>
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
<h1>Проекты</h1>
<a href="${pageContext.request.contextPath}/projects/add">Добавить проект</a>

<c:choose>
    <c:when test="${empty projects}">
        <p>Нет доступных проектов.</p>
    </c:when>
    <c:otherwise>
        <table>
            <tr>
                <th>ID</th>
                <th>Имя</th>
                <th>Описание</th>
                <th>Дата начала</th>
                <th>Дата окончания</th>
                <th>Приоритет</th>
                <th>Статус</th>
                <th>Ответственное лицо</th>
                <th>Участники</th>
                <th>Действия</th>
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
                        <c:forEach var="responsiblePersonId" items="${project.responsiblePersons}">
                            <c:set var="responsiblePersonName" value="${projectService.getUserName(responsiblePersonId)}"/>
                            ${responsiblePersonName}<br>
                        </c:forEach>
                    </td>
                    <td>
                        <c:forEach var="participant" items="${project.participants}">
                            ${participant.name}<br>
                        </c:forEach>
                    </td>
                    <td>
                        <a href="${pageContext.request.contextPath}/projects/edit/${project.id}">Редактировать</a> |
                        <a href="${pageContext.request.contextPath}/projects/delete/${project.id}">Удалить</a>
                    </td>
                </tr>
            </c:forEach>
        </table>
    </c:otherwise>
</c:choose>

</body>
</html>