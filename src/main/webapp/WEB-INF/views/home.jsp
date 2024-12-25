<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ include file="menu.jsp" %>
<html>
<head>
    <title>Main Page</title>
</head>
<body>
<jsp:useBean id="currentDate" class="java.util.Date" />
<fmt:formatDate value="${currentDate}" pattern="yyyy-MM-dd HH:mm:ss" />
<h1>Welcome!</h1>

<div>
    <a href="${pageContext.request.contextPath}/projects" style="margin-right: 20px;">Projects</a>
    <a href="${pageContext.request.contextPath}/tasks" style="margin-right: 20px;">Tasks</a>
    <a href="${pageContext.request.contextPath}/users">Users</a>
</div>

<h2>Sample Table with Calculated Total</h2>
<table border="1">
    <tr>
        <th>Item</th>
        <th>Value</th>
    </tr>
    <c:set var="total" value="0" />
    <c:forEach var="i" begin="1" end="5">
        <c:set var="value" value="${i * 10}" />
        <tr>
            <td>Item ${i}</td>
            <td>${value}</td>
        </tr>
        <c:set var="total" value="${total + value}" />
    </c:forEach>
    <tr>
        <td><strong>Total</strong></td>
        <td><strong>${total}</strong></td>
    </tr>
</table>

</body>
</html>
