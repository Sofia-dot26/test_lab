<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
<html>
<head>
    <title>Add/Edit Task</title>
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
        .error {
            color: red;
        }
    </style>
</head>
<body>
<h1>Add/Edit Task</h1>
<form:form action="${pageContext.request.contextPath}/tasks/add" method="post" modelAttribute="task">
    <label for="name">Task Name:</label>
    <form:input path="name" id="name" required="true"/>
    <form:errors path="name" cssClass="error"/>

    <label for="description">Description:</label>
    <form:input path="description" id="description" required="true"/>
    <form:errors path="description" cssClass="error"/>

    <label for="startDate">Start Date:</label>
    <form:input path="startDate" id="startDate" type="date" required="true"/>
    <form:errors path="startDate" cssClass="error"/>

    <label for="endDate">End Date:</label>
    <form:input path="endDate" id="endDate" type="date" required="true"/>
    <form:errors path="endDate" cssClass="error"/>

    <label for="priority">Priority:</label>
    <form:select path="priority" id="priority" required="true">
        <form:option value="High">High</form:option>
        <form:option value="Medium">Medium</form:option>
        <form:option value="Low">Low</form:option>
    </form:select>
    <form:errors path="priority" cssClass="error"/>

    <label for="status">Status:</label>
    <form:select path="status" id="status" required="true">
        <form:option value="In Queue">In Queue</form:option>
        <form:option value="In Progress">In Progress</form:option>
        <form:option value="Completed">Completed</form:option>
    </form:select>
    <form:errors path="status" cssClass="error"/>

    <label for="project">Project:</label>
    <form:select path="project.id" id="project" required="true">
        <c:forEach var="project" items="${projects}">
            <form:option value="${project.id}">${project.name}</form:option>
        </c:forEach>
    </form:select>
    <form:errors path="project" cssClass="error"/>

    <label for="assignees">Assignees:</label>
    <form:select path="assignees" id="assignees" multiple="true" required="true">
        <c:forEach var="user" items="${users}">
            <form:option value="${user.id}">${user.name}</form:option>
        </c:forEach>
    </form:select>
    <form:errors path="assignees" cssClass="error"/>

    <input type="submit" value="Save Task">
</form:form>
</body>
</html>
