<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
<html>
<head>
    <title>Add Project</title>
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
<h1>Add Project</h1>
<form:form action="${pageContext.request.contextPath}/projects/add" method="post" modelAttribute="project">
    <label for="name">Project Name:</label>
    <form:input path="name" id="name" required="true"/>

    <label for="description">Description:</label>
    <form:input path="description" id="description" required="true"/>

    <label for="startDate">Start Date:</label>
    <form:input path="startDate" id="startDate" type="date" required="true"/>

    <label for="endDate">End Date:</label>
    <form:input path="endDate" id="endDate" type="date" required="true"/>

    <label for="priority">Priority:</label>
    <form:select path="priority" id="priority" required="true">
        <form:option value="High">High</form:option>
        <form:option value="Medium">Medium</form:option>
        <form:option value="Low">Low</form:option>
    </form:select>

    <label for="status">Status:</label>
    <form:select path="status" id="status" required="true">
        <form:option value="Planning">Planning</form:option>
        <form:option value="In Progress">In Progress</form:option>
        <form:option value="Completed">Completed</form:option>
    </form:select>

    <input type="submit" value="Add Project">
</form:form>
</body>
</html>