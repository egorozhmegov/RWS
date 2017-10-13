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
    <thead>
    <tr>
        <th>Train</th>
        <th>Departure time</th>
        <th>Price, $</th>
        <th></th>
    </tr>
    </thead>

    <tbody>
    <c:forEach items="${trains}" var="item">
        <tr>
            <td>${item.key.train.number}</td>
            <td>${item.key.departureTime}</td>
            <td>${item.value}</td>

            <td><a href="/client/buy/${item.key.train.id}/${station1}/${station2}/${date}">
                    <input type="button" value="Buy" />
                </a>
            </td>
        </tr>
    </c:forEach>
    </tbody>
</table>


</body>
</html>