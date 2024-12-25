<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ include file="menu.jsp" %>
<html>
<head>
    <title>Добавить проект</title>
    <style>
        body {
            font-family: Arial, sans-serif;
            margin: 20px;
        }
        h1 {
            color: #333;
        }
        form {
            margin-top: 20px;
        }
        label {
            display: block;
            margin-top: 10px;
        }
        input[type="text"], input[type="date"], select {
            width: 100%;
            padding: 8px;
            margin-top: 5px;
            margin-bottom: 10px;
            border: 1px solid #ddd;
            border-radius: 4px;
        }
        input[type="submit"] {
            background-color: #007BFF;
            color: white;
            padding: 10px 20px;
            border: none;
            border-radius: 4px;
            cursor: pointer;
        }
        input[type="submit"]:hover {
            background-color: #0056b3;
        }
    </style>
</head>
<body>
<h1>Добавить проект</h1>
<form:form action="${pageContext.request.contextPath}/projects/add" method="post" modelAttribute="project">
    <label for="name">Имя проекта:</label>
    <form:input path="name" id="name" required="true"/>

    <label for="description">Описание:</label>
    <form:input path="description" id="description" required="true"/>

    <label for="startDate">Дата начала:</label>
    <form:input path="startDate" id="startDate" type="date" required="true"/>

    <label for="endDate">Дата окончания:</label>
    <form:input path="endDate" id="endDate" type="date" required="true"/>

    <label for="priority">Приоритет:</label>
    <form:select path="priority" id="priority" required="true">
        <form:option value="Высокий">Высокий</form:option>
        <form:option value="Средний">Средний</form:option>
        <form:option value="Низкий">Низкий</form:option>
    </form:select>

    <label for="status">Статус:</label>
    <form:select path="status" id="status" required="true">
        <form:option value="Планирование">Планирование</form:option>
        <form:option value="В процессе">В процессе</form:option>
        <form:option value="Завершен">Завершен</form:option>
    </form:select>

    <label for="responsiblePerson">Ответственное лицо:</label>
    <select name="responsiblePerson" id="responsiblePerson" required="true">
        <c:forEach var="user" items="${users}">
            <option value="${user.id}">${user.name}</option>
        </c:forEach>
    </select>

    <label for="participants">Участники:</label>
    <select name="participants" id="participants" multiple required="true">
        <c:forEach var="user" items="${users}">
            <option value="${user.id}">${user.name}</option>
        </c:forEach>
    </select>

    <input type="submit" value="Добавить проект">
</form:form>
</body>
</html>
