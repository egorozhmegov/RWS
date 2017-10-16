<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<html>
<head>
    <title>Trains</title>
    <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css"/>
    <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap-theme.min.css"/>
</head>
<body>

<div class="row">
    <div class="col-md-3"></div>

    <div class="col-md-5">
        <h1>RWS</h1>
    </div>

    <div class="col-md-1">
        <h3><p><a href="http://localhost:8080/client/"><span class="glyphicon glyphicon-home"></span></a></p></h3>
    </div>

    <div class="col-md-3"></div>

</div>

<div class="row">
    <div class="col-md-3"></div>

    <div class="col-md-6">
        <h4>Trains from ${station1} to ${station2}</h4>
        <h4>Date: ${date.split('/')[1]}.${date.split('/')[0]}.${date.split('/')[2]}</h4>
    </div>

    <div class="col-md-3"></div>
</div>

<div class="row">
    <div class="col-md-3"></div>

    <div class="col-md-6">
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
    </div>
</div>

<div class="col-md-3"></div>




</body>
</html>