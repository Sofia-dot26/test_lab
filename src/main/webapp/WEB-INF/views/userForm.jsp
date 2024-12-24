<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
<html>
<head>
    <title>Add User</title>
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
        input[type="text"], input[type="email"], input[type="password"], select {
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
<h1>Add User</h1>
<form:form action="${pageContext.request.contextPath}/users/add" method="post" modelAttribute="user">
    <label>Name:</label>
    <form:input path="name" required="required"/>
    <form:errors path="name" cssClass="error"/>

    <label>Email:</label>
    <form:input path="email" type="email" required="required"/>
    <form:errors path="email" cssClass="error"/>

    <label>Password:</label>
    <form:input path="password" type="password" required="required"/>
    <form:errors path="password" cssClass="error"/>

    <label>Role:</label>
    <form:select path="role" required="required">
        <form:option value="Admin">Admin</form:option>
        <form:option value="Manager">Manager</form:option>
        <form:option value="Participant">Participant</form:option>
    </form:select>
    <form:errors path="role" cssClass="error"/>

    <label>Status:</label>
    <form:select path="status" required="required">
        <form:option value="Active">Active</form:option>
        <form:option value="Inactive">Inactive</form:option>
    </form:select>
    <form:errors path="status" cssClass="error"/>

    <input type="submit" value="Add User">
</form:form>
</body>
</html>
