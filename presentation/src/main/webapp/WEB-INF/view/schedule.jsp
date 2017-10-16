<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<html>
<head>
    <title>Schedule</title>
    <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css"/>
    <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap-theme.min.css"/>
</head>
<body>

<div class="row">
    <div class="col-md-4"></div>

    <div class="col-md-4">
        <h3>Schedule of station ${station}</h3>
        <h3>Date: ${date.split('/')[1]}.${date.split('/')[0]}.${date.split('/')[2]}</h3><br>
    </div>

    <div class="col-md-3">

    </div>

    <div class="col-md-1">
        <h3><p><a href="http://localhost:8080/client/"><span class="glyphicon glyphicon-home"></span></a></p></h3>
    </div>
</div>

<div class="row">
    <div class="col-md-6">
        <h3>Arrival</h3>
        <table class="table table-striped">
            <thead>
            <tr>
                <th>Train</th>
                <th>Arrival time</th>
            </tr>
            </thead>

            <tbody>
            <c:forEach items="${arriveSchedule}" var="item">
                <tr>
                    <td>${item.train.number}</td>
                    <td>${item.arrivalTime}</td>
                </tr>
            </c:forEach>
            </tbody>
        </table>
    </div>

    <div class="col-md-6">
        <h3>Departure</h3>
        <table class="table table-striped">
            <thead>
            <tr>
                <th>Train</th>
                <th>Departure time</th>
            </tr>
            </thead>

            <tbody>
            <c:forEach items="${departSchedule}" var="item">
                <tr>
                    <td>${item.train.number}</td>
                    <td>${item.departureTime}</td>
                </tr>
            </c:forEach>
            </tbody>
        </table>
    </div>
</div>

</body>
</html>