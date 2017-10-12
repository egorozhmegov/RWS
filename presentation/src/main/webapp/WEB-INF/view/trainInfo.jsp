<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<html>
<head>
    <title>Info</title>
    <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css"/>
    <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap-theme.min.css"/>
</head>
<body>
Train <h4>${currentRoute.get(0).train.number}</h4>
Price <h4>${ticketPrice} $</h4>
Seats <h4>${freeSeats}</h4>
Departure time
<h4>Day: ${departDay}</h4>
<h4>Time: ${departTime}</h4>
Arrival time
<h4>Day: ${arriveDay}</h4>
<h4>Time: ${arriveTime}</h4>

<table class="table table-striped">
    <thead>
    <tr>
        <th>Route</th>
        <th></th>
    </thead>

    <tbody>
    <c:forEach items="${currentRoute}" var="item">
        <tr>
            <td>${item.station.title}</td>
        </tr>
        <tr>
            <td><h3>|</h3></td>
        </tr>
    </c:forEach>
    </tbody>
</table>
</body>
</html>