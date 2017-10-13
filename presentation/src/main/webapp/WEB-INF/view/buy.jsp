<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<html>
<head>
    <title>Info</title>
    <script type="text/javascript" src="https://code.jquery.com/jquery-1.11.3.min.js"></script>
    <link rel="stylesheet" href="https://formden.com/static/cdn/bootstrap-iso.css"/>
    <script type="text/javascript"
            src="https://cdnjs.cloudflare.com/ajax/libs/bootstrap-datepicker/1.4.1/js/bootstrap-datepicker.min.js"></script>
    <link rel="stylesheet"
          href="https://cdnjs.cloudflare.com/ajax/libs/bootstrap-datepicker/1.4.1/css/bootstrap-datepicker3.css"/>
    <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css"/>
    <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap-theme.min.css"/>
    <link rel="stylesheet" href="css/index.css"/>
    <link rel="stylesheet" href="css/buy.css"/>
</head>
<body>

<div class="row">
    <div class="col-md-3">
        <h2>Info</h2>
        <h4>Train ${currentRoute.get(0).train.number}</h4>
        <h4>Price: ${ticketPrice} $</h4>
        <h4>Seats: ${freeSeats}</h4>
        <h4> Departure  ${departDay}     ${departTime}</h4>
        <h4>Arrival  ${arriveDay}     ${arriveTime}</h4>
    </div>

    <div class="col-md-3">
        <h2>Route</h2>
        <table class="table table-striped">
            <c:forEach items="${currentRoute}" var="item">
                <tr>
                    <td>${item.station.title}</td>
                </tr>
                <tr>
                    <td><h3>|</h3></td>
                </tr>
            </c:forEach>
        </table>
    </div>

    <div class="col-md-6">
        <h2>Payment</h2>

        <div class="col-xs-6">
            <form method="post" action="/client/payment">
                <div class="form-group">
                    <input class="form-control" type="text" id="firstName" name="firstName" placeholder="First Name" required/>
                </div>

                <div class="form-group">
                    <input class="form-control" type="text" id="lastName" name="lastName" placeholder="Last Name" required/>
                </div>

                <div class="form-group" >
                    <input class="form-control" id="date" name="date" placeholder="MM/DD/YYY" type="text" required/>
                </div>

                <div>
                    <input value="${currentRoute.get(0).train.id}" id="trainId" name="trainId" type="hidden"/>
                </div>

                <div>
                    <input value="${departDay}" id="departDay" name="departDay" type="hidden"/>
                </div>

                <div>
                    <input value="${arriveDay}" id="arriveDay" name="arriveDay" type="hidden"/>
                </div>

                <div>
                    <input value="${currentRoute.get(0).station.title}" id="departStation" name="departStation" type="hidden"/>
                </div>

                <div>
                    <input value="${currentRoute.get(currentRoute.size()-1).station.title}" id="arriveStation" name="arriveStation" type="hidden"/>
                </div>

                <div>
                    <input value="${ticketPrice}" id="ticketPrice" name="ticketPrice" type="hidden"/>
                </div>

                <div class="form-group">
                    <button type="submit" class="btn btn-primary btn-md">Buy</button>
                </div>
            </form>
        </div>
    </div>
</div>

<script>
    $(document).ready(function () {
        var date_input = $('input[name="date"]');
        var container = $('.bootstrap-iso form').length > 0 ? $('.bootstrap-iso form').parent() : "body";
        var options = {
            format: 'mm/dd/yyyy',
            container: container,
            todayHighlight: true,
            autoclose: true
        };
        date_input.datepicker(options);
    })
</script>
</body>
</html>