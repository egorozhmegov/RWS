<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<html>
<head>
    <title>RWS</title>

    <link rel="icon"
          type="image/x-icon"
          href="img/favicon.ico">

    <script type="text/javascript" src="https://code.jquery.com/jquery-1.11.3.min.js"></script>
    <link rel="stylesheet" href="https://formden.com/static/cdn/bootstrap-iso.css"/>
    <script type="text/javascript"
            src="https://cdnjs.cloudflare.com/ajax/libs/bootstrap-datepicker/1.4.1/js/bootstrap-datepicker.min.js"></script>
    <link rel="stylesheet"
          href="https://cdnjs.cloudflare.com/ajax/libs/bootstrap-datepicker/1.4.1/css/bootstrap-datepicker3.css"/>
    <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css"/>
    <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap-theme.min.css"/>
</head>
<body>

<div class="row">
    <div class="col-md-3"></div>

    <div class="col-md-6">
        <h1>RWS</h1>
    </div>

    <div class="col-md-3"></div>
</div>

<div class="row">
    <div class="col-md-3"></div>

    <div class="col-md-6">
        <h2>Search train</h2>
    </div>

    <div class="col-md-3"></div>
</div>

<div class="row">
    <div class="col-md-3"></div>

    <div class="col-md-6">
        <div class="bootstrap-iso">
            <div class="row">
                <form method="post" action="/client/search">
                    <div class="col-md-3 col-sm-3 col-xs-6">
                        <input class="form-control" type="text" id="station1" name="station1" placeholder="From" required/>
                    </div>

                    <div class="col-md-3 col-sm-3 col-xs-6">
                        <input class="form-control" type="text" id="station2" name="station2" placeholder="To" required/>
                    </div>


                    <div class="col-md-3 col-sm-3 col-xs-6">
                        <input class="form-control" id="date" name="date" placeholder="Date" type="text" required/>
                    </div>

                    <div class="col-md-3 col-sm-3 col-xs-6">
                        <button type="submit" class="btn btn-primary btn-md">Search</button>
                    </div>
                </form>
            </div>
        </div>
    </div>
</div>

<div class="row">
    <div class="col-md-12"><h1><h/1></div>
</div>

<div class="row">
    <div class="col-md-3"></div>

    <div class="col-md-6">
        <h2>Show schedule</h2>
    </div>

    <div class="col-md-3"></div>
</div>

<div class="row">
    <div class="col-md-3"></div>

    <div class="col-md-6">
        <div class="bootstrap-iso">
            <div class="row">
                <form method="post" action="/client/schedule">
                    <div class="col-md-3 col-sm-3 col-xs-6">
                        <select class="form-control" id="station" name="station" required>
                                <c:forEach items="${stations}" var="item">
                                    <option>
                                        ${item.title}
                                    </option>
                                </c:forEach>
                        </select>
                    </div>

                    <div class="col-md-3 col-sm-3 col-xs-6">
                        <input class="form-control" id="date" name="date" placeholder="Date" type="text" required/>
                    </div>

                    <div class="col-md-3 col-sm-3 col-xs-6">
                        <button type="submit" class="btn btn-primary btn-md">Show</button>
                    </div>
                </form>
            </div>
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
