<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<html>
<head>
    <title>Trains</title>
    <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css"/>
    <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap-theme.min.css"/>
    <link rel="stylesheet" href="css/index.css"/>
</head>
<body>

<table class="table table-striped">
    <c:forEach items="${trains}" var="item">
        <thead>
            <tr>
                <th>Train</th>
                <th>Departure time</th>
                <th>Departure days</th>
            </tr>
        </thead>

        <tbody>
            <tr>
                <td>${item.train.number}</td>
                <td>${item.departureTime}</td>
                <td>${item.departPeriod}</td>
            </tr>
        </tbody>
    </c:forEach>
</table>


</body>
</html>
