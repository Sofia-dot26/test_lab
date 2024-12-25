<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<div class="menu">
    <a href="${pageContext.request.contextPath}/users">Users</a>
    <a href="${pageContext.request.contextPath}/dashboard">Dashboard</a>
    <a href="${pageContext.request.contextPath}/projects">Projects</a>
    <a href="${pageContext.request.contextPath}/tasks">Tasks</a>
</div>
<style>
    .menu {
        display: flex;
        justify-content: space-around;
        background-color: #333;
        padding: 10px 0;
    }
    .menu a {
        color: white;
        text-decoration: none;
        padding: 14px 20px;
        transition: background-color 0.3s;
    }
    .menu a:hover {
        background-color: #575757;
    }
</style>
